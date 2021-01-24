package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import vexMod.VexMod;
import vexMod.cards.Virus;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class EndlessSickness extends CustomRelic {


    public static final String ID = VexMod.makeID("EndlessSickness");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("EndlessSickness.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("EndlessSickness.png"));

    public EndlessSickness() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2]));
    }


    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }


    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Virus(), 1, true, true));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}