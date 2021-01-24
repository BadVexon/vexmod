package vexMod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.relics.TinyChest;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import vexMod.relics.Spyglass;

import java.util.ArrayList;

public class SpyglassPatch {
    public static final int MONSTER_CHANCE = 50;
    public static final int SHOP_CHANCE = 25;
    public static final int TREASURE_CHANCE = 10;
    public static final int MAX_MONSTERS = 4;
    public static final int MAX_SHOPS = 4;
    public static final int MAX_TREASURES = 3;

    @SpirePatch(clz = MapRoomNode.class, method = "render")

    public static class MapRoomNodeRender {
        public static void Postfix(MapRoomNode self, SpriteBatch sb) {
            if (!(AbstractDungeon.floorNum == 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.EVENT) && self.hb.hovered && self.room instanceof EventRoom && AbstractDungeon.player.hasRelic(Spyglass.ID) && !Spyglass.nodeList.containsKey(getNodeIndex(self))) {
                ArrayList<PowerTip> tips = new ArrayList<>();
                EventHelper.RoomResult result = epicRoll();
                tips.add(new PowerTip("? Room Contents", "This ? room contains a(n) " + result.name().toLowerCase() + "."));
                Spyglass.nodeList.put(getNodeIndex(self), result);
                TipHelper.queuePowerTips(self.hb.x + self.hb.width + 20.0f * Settings.scale, self.hb.y + self.hb.height, tips);
                System.out.println("Room at " + self.x + ", " + self.y + " assigned content " + result.name());
            } else if (!(AbstractDungeon.floorNum == 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.EVENT) && self.hb.hovered && self.room instanceof EventRoom && AbstractDungeon.player.hasRelic(Spyglass.ID) && Spyglass.nodeList.containsKey(getNodeIndex(self))) {
                ArrayList<PowerTip> tips = new ArrayList<>();
                EventHelper.RoomResult result = Spyglass.nodeList.get(getNodeIndex(self));
                tips.add(new PowerTip("? Room Contents", "This ? room contains a(n) " + result.name().toLowerCase() + "."));
                TipHelper.queuePowerTips(self.hb.x + self.hb.width + 20.0f * Settings.scale, self.hb.y + self.hb.height, tips);
            }
        }
    }

    public static EventHelper.RoomResult epicRoll() {
        int mariotime = AbstractDungeon.eventRng.random(1, 100);
        if (AbstractDungeon.player.hasRelic(TinyChest.ID)) {
            if (mariotime <= TREASURE_CHANCE + 10) {
                return EventHelper.RoomResult.TREASURE;
            } else if (mariotime <= SHOP_CHANCE + 10) {
                return EventHelper.RoomResult.SHOP;
            } else if (mariotime <= MONSTER_CHANCE + 10) {
                return EventHelper.RoomResult.MONSTER;
            } else {
                return EventHelper.RoomResult.EVENT;
            }
        } else {
            if (mariotime <= TREASURE_CHANCE) {
                return EventHelper.RoomResult.TREASURE;
            } else if (mariotime <= SHOP_CHANCE) {
                return EventHelper.RoomResult.SHOP;
            } else if (mariotime <= MONSTER_CHANCE) {
                return EventHelper.RoomResult.MONSTER;
            } else {
                return EventHelper.RoomResult.EVENT;
            }
        }
    }

    private static String getNodeIndex(MapRoomNode node) {
        for (int a = 0; a < AbstractDungeon.map.size(); ++a) {
            for (int b = 0; b < AbstractDungeon.map.get(0).size(); ++b) {
                if (node.equals(AbstractDungeon.map.get(a).get(b))) {
                    return a + " " + b;
                }
            }
        }
        return "";
    }

    private static MapRoomNode locateNodeFromString(String nodeIndex) {
        String[] kirby = nodeIndex.split(" ");
        int mario = Integer.parseInt(kirby[0]);
        System.out.println("X at " + mario);
        int sonic = Integer.parseInt(kirby[1]);
        System.out.println("Y at " + sonic);
        return AbstractDungeon.map.get(mario).get(sonic);
    }
}
