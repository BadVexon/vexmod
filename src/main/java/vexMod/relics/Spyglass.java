package vexMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.HashMap;
import java.util.Map;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Spyglass extends CustomRelic implements CustomSavable<Map<String, EventHelper.RoomResult>> {

    public static final String ID = VexMod.makeID("Spyglass");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Spyglass.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Spyglass.png"));

    public static Map<String, EventHelper.RoomResult> nodeList = new HashMap<>();
    public static MapRoomNode realCurrNode = null;

    @Override
    public Map<String, EventHelper.RoomResult> onSave() {
        return nodeList;
    }

    @Override
    public void onLoad(Map<String, EventHelper.RoomResult> maptime) {
        for (String s : maptime.keySet()) {
            System.out.println("Loading in located ? room node at " + s + " with result " + maptime.get(s));
            nodeList.put(s, maptime.get(s));
        }
    }

    public Spyglass() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
