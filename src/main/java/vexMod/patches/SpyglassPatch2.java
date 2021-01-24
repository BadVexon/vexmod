package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import vexMod.relics.Spyglass;

@SpirePatch(
        clz = EventHelper.class,
        method = "roll",
        paramtypez = {
                Random.class
        }
)

public class SpyglassPatch2 {
    public static SpireReturn<EventHelper.RoomResult> Prefix(Random eventRng) {
        System.out.println("Player located in " + getNodeIndex(Spyglass.realCurrNode));
        if (AbstractDungeon.player.hasRelic(Spyglass.ID) && Spyglass.nodeList.containsKey(getNodeIndex(Spyglass.realCurrNode))) {
            System.out.println("Found assigned result " + Spyglass.nodeList.get(getNodeIndex(Spyglass.realCurrNode)) + " at ? room at" + getNodeIndex(Spyglass.realCurrNode));
            return SpireReturn.Return(Spyglass.nodeList.get(getNodeIndex(Spyglass.realCurrNode)));
        }
        System.out.println("No assigned result found. Returning usual event determination.");
        return SpireReturn.Continue();
    }

    private static String getNodeIndex(MapRoomNode node) {
        System.out.println("Attempting to get index of node at" + node.x + node.y);
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