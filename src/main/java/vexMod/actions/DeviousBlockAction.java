package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import vexMod.relics.DeviousBotling;

import java.util.ArrayList;

public class DeviousBlockAction extends AbstractGameAction {
    public DeviousBlockAction(int amount) {
        this.duration = 0.5F;
        this.amount = amount;
        this.actionType = ActionType.BLOCK;
    }

    public void update() {
        if (this.duration == 0.5F) {
            ArrayList<AbstractMonster> validMonsters = new ArrayList<>();

            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m != this.source && m.intent != Intent.ESCAPE && !m.isDying) {
                    validMonsters.add(m);
                }
            }

            if (!validMonsters.isEmpty()) {
                this.target = validMonsters.get(AbstractDungeon.aiRng.random(validMonsters.size() - 1));
            } else {
                this.target = this.source;
            }

            if (this.target != null) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SHIELD));
                this.target.addBlock(this.amount);
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(this.target, AbstractDungeon.player.getRelic(DeviousBotling.ID)));
            }
            AbstractDungeon.actionManager.addToBottom(new RelicTalkAction((AbstractDungeon.player.getRelic(DeviousBotling.ID)), (AbstractDungeon.player.getRelic(DeviousBotling.ID).DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(29, 33)] + this.target.name + "?"), 0.0F, 5.0F));
        }

        this.tickDuration();
    }
}
