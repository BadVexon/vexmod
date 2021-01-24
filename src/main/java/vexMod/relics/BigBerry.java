package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class BigBerry extends CustomRelic {

    public static final String ID = VexMod.makeID("BigBerry");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BigBerry.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BigBerry.png"));

    public BigBerry() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(30, true);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
