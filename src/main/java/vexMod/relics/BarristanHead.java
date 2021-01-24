package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class BarristanHead extends CustomRelic {

    public static final String ID = VexMod.makeID("BarristanHead");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("WellWornAnklet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("WellWornAnklet.png"));

    public BarristanHead() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player.currentHealth <= AbstractDungeon.player.maxHealth / 4F) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 4));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
