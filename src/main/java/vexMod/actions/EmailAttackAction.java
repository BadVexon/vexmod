package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import vexMod.VexMod;

public class EmailAttackAction extends AbstractGameAction {
    private DamageInfo info;
    private String name;

    public EmailAttackAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            this.target.damage(this.info);
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                this.name = this.target.name;
                Thread t = new Thread(() -> {
                    ConfigurationBuilder cb = new ConfigurationBuilder();
                    cb.setDebugEnabled(true)
                            .setOAuthConsumerKey("dSpsG146tUir9joAnM49c92aM")
                            .setOAuthConsumerSecret("ua4n3m4hcq3hwbpsEGwogLto8fFWIzzVxG9vXtARWNY0FmV9k1")
                            .setOAuthAccessToken("1114339198247493632-a0gde0NOIWImc7EHPXC2xJsqQbJCCM")
                            .setOAuthAccessTokenSecret("7VfNOz9fiDbKaz2JlW8QpRtenvcXIhLWAFpBDhZzuZGCm");
                    TwitterFactory tf = new TwitterFactory(cb.build());
                    Twitter twitter = tf.getInstance();
                    try {
                            if (AbstractDungeon.isAscensionMode) {
                                twitter.updateStatus(CardCrawlGame.playerName + " playing as " + AbstractDungeon.player.getLocalizedCharacterName() + " just killed a " + EmailAttackAction.this.name + " with Tweet Strike! They are on floor " + AbstractDungeon.floorNum + " on Ascension " + AbstractDungeon.ascensionLevel + ", and have " + AbstractDungeon.player.currentHealth + " HP and " + AbstractDungeon.player.gold + " Gold.");
                            } else {
                                twitter.updateStatus(CardCrawlGame.playerName + " playing as " + AbstractDungeon.player.getLocalizedCharacterName() + " just killed a " + EmailAttackAction.this.name + " with Tweet Strike! They are on floor " + AbstractDungeon.floorNum + ", and have " + AbstractDungeon.player.currentHealth + " HP and " + AbstractDungeon.player.gold + " Gold.");

                            }
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                });
                t.start();
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        tickDuration();
    }
}
