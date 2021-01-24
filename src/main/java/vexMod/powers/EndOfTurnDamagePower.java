package vexMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

public class EndOfTurnDamagePower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = VexMod.makeID("EndOfTurnDamagePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("vexModResources/images/powers/EndOfTurnDamage_84.png");
    private static final Texture tex32 = TextureLoader.getTexture("vexModResources/images/powers/EndOfTurnDamage_32.png");
    public AbstractCreature source;

    public EndOfTurnDamagePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;


        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flashWithoutSound();
        this.playApplyPowerSfx();
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.source, this.amount, DamageInfo.DamageType.THORNS)));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new EndOfTurnDamagePower(owner, source, amount);
    }

    @Override
    public void updateDescription() {
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
    }

}