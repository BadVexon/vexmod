package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RetaliationKit extends CustomRelic implements OnLoseBlockRelic {


    public static final String ID = VexMod.makeID("RetaliationKit");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RetaliationKit.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RetaliationKit.png"));
    private static boolean activated = false;

    public RetaliationKit() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    public void atBattleStart() {
        activated = false;
    }

    @Override
    public int onLoseBlock(DamageInfo info, int damageAmount) {
        if (damageAmount >= AbstractDungeon.player.currentBlock && !activated && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
                if (!m.isDead && !m.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, 2, false), 2));
                }
            activated = true;
        }
        return damageAmount;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
