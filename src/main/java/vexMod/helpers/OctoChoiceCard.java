package vexMod.helpers;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vexMod.VexMod;

public class OctoChoiceCard extends CustomCard {
    private static final int COST = -2;

    public OctoChoiceCard(String id, String name, String IMG, String description, CardType type, int damageAmt, int blockAmt, int magicamt) {
        super(makeID(id), name, IMG, COST, description, type, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);

        baseDamage = damageAmt;
        baseBlock = blockAmt;
        baseMagicNumber = magicNumber = magicamt;

    }

    public OctoChoiceCard(String id, String name, String IMG, String description, CardType type, int damageAmt, int blockAmt) {
        this(id, name, IMG, description, type, damageAmt, blockAmt, -1);
    }

    public OctoChoiceCard(String id, String name, String IMG, String description, CardType type) {
        this(id, name, IMG, description, type, -1, -1, -1);
    }

    private static String makeID(String id) {
        return VexMod.makeID("Shifting" + id);
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {

    }
}