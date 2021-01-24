package vexMod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;
import vexMod.actions.EmailAttackAction;


public class EmailVexCard
        extends AbstractDefaultCard {
    public static final String ID = VexMod.makeID("EmailVexCard");
    public static final String IMG = VexMod.makeCardPath("EmailVexCard.png");
    public static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.COLORLESS;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 13;
    private static final int UPGRADE_PLUS_DMG = 6;

    public EmailVexCard() {
        super(ID, NAME, IMG, 1, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 13;
        this.exhaust = true;
        this.tags.add(AbstractCard.CardTags.STRIKE);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new EmailAttackAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(6);
            initializeDescription();
        }
    }
}
