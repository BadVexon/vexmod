package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.GremlinTsundere;
import com.megacrit.cardcrawl.monsters.exordium.GremlinWarrior;
import com.megacrit.cardcrawl.powers.AngryPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.RefreshEnergyEffect;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import vexMod.VexMod;
import vexMod.actions.DeviousBlockAction;
import vexMod.actions.DeviousNerfAction;
import vexMod.actions.DeviousStealAction;
import vexMod.actions.RelicTalkAction;
import vexMod.powers.EndOfTurnDamagePower;
import vexMod.util.TextureLoader;
import vexMod.vfx.RelicSpeechBubble;

import java.util.Collections;


public class DeviousBotling
        extends CustomRelic
        implements OnPlayerDeathRelic {
    public static final String ID = VexMod.makeID("DeviousBotling");

    private static final Texture IMG = TextureLoader.getTexture(VexMod.makeRelicPath("DeviousBotling.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(VexMod.makeRelicOutlinePath("DeviousBotling.png"));


    public DeviousBotling() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
        this.counter = 0;
    }


    private static void moveRelic(AbstractRelic r1, AbstractRelic r2) {
        float oldX = r1.currentX;
        float oldHBX = r1.hb.x;
        r1.currentX = r2.currentX;
        r1.hb.x = r2.hb.x;
        r2.hb.x = oldHBX;
        r2.currentX = oldX;
    }


    public boolean onPlayerDeath(AbstractPlayer p, DamageInfo info) {
        AbstractDungeon.topLevelEffects.add(new RelicSpeechBubble(this.currentX, this.currentY, 5.0F, this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(77, 104)], true));
        return true;
    }


    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }


    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }


    public void onEnterRoom(AbstractRoom room) {
        if (AbstractDungeon.player.relics.size() == 2) {
            if (!(AbstractDungeon.player.relics.get(1) instanceof DeviousBotling) && AbstractDungeon.player.relics.get(0) instanceof DeviousBotling) {
                AbstractRelic r1 = AbstractDungeon.player.getRelic(ID);
                AbstractRelic r2 = AbstractDungeon.player.relics.get(1);
                moveRelic(r1, r2);
                Collections.swap(AbstractDungeon.player.relics, AbstractDungeon.player.relics.indexOf(this), 1);
            }
        } else if (AbstractDungeon.player.relics.size() >= 3 &&
                !(AbstractDungeon.player.relics.get(2) instanceof DeviousBotling) && (AbstractDungeon.player.relics.get(0) instanceof DeviousBotling || AbstractDungeon.player.relics.get(1) instanceof DeviousBotling)) {
            AbstractRelic r1 = AbstractDungeon.player.getRelic(ID);
            AbstractRelic r2 = AbstractDungeon.player.relics.get(2);
            moveRelic(r1, r2);
            Collections.swap(AbstractDungeon.player.relics, AbstractDungeon.player.relics.indexOf(this), 2);
        }
    }


    public void atTurnStartPostDraw() {
        this.counter++;
        if (this.counter == 3) {
            this.counter = 0;
            int downsideRoll = AbstractDungeon.cardRandomRng.random(0, 7);
            if (downsideRoll == 0) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(1, 4)] + this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)], 0.0F, 5.0F));
                flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 1, false), 1));
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else if (downsideRoll == 1) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(5, 8)] + this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)], 0.0F, 5.0F));
                flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, 1, false), 1));
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else if (downsideRoll == 2) {
                if (AbstractDungeon.player.gold > 0) {
                    int goldTaken = AbstractDungeon.cardRandomRng.random(5, 15);
                    AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(9, 12)] + this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)], 0.0F, 5.0F));
                    flash();
                    AbstractDungeon.player.loseGold(goldTaken);
                    for (int i = 0; i < goldTaken; i++) {
                        AbstractDungeon.effectList.add(new GainPennyEffect(AbstractDungeon.player, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY, false));
                    }
                    AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new DeviousBlockAction(AbstractDungeon.cardRandomRng.random(6, 10)));
                }
            } else if (downsideRoll == 3) {
                if (AbstractDungeon.player.hand.size() > 0) {
                    AbstractDungeon.actionManager.addToBottom(new DeviousStealAction());
                } else {
                    AbstractDungeon.actionManager.addToBottom(new DeviousBlockAction(AbstractDungeon.cardRandomRng.random(6, 10)));
                }
            } else if (downsideRoll == 4) {
                AbstractDungeon.actionManager.addToBottom(new DeviousNerfAction());
            } else if (downsideRoll == 5) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(21, 24)] + this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)], 0.0F, 5.0F));
                flash();
                AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(1));
                AbstractDungeon.effectsQueue.add(new RefreshEnergyEffect());
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else if (downsideRoll == 6) {
                AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(25, 28)] + this.DESCRIPTIONS[AbstractDungeon.cardRandomRng.random(34, 72)], 0.0F, 5.0F));
                flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EndOfTurnDamagePower(AbstractDungeon.player, AbstractDungeon.player, 6), 6));
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else if (downsideRoll == 7) {
                flash();
                AbstractDungeon.actionManager.addToBottom(new DeviousBlockAction(AbstractDungeon.cardRandomRng.random(6, 10)));
            }
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
