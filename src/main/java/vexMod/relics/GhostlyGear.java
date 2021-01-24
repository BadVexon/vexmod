package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static basemod.helpers.BaseModCardTags.BASIC_DEFEND;
import static basemod.helpers.BaseModCardTags.BASIC_STRIKE;
import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class GhostlyGear extends CustomRelic {


    public static final String ID = VexMod.makeID("GhostlyGear");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GhostlyGear.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GhostlyGear.png"));


    public GhostlyGear() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || card.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
            action.exhaustCard = true;
        }
    }

    @Override
    public boolean canSpawn() {
        int strikes = 0;
        int defends = 0;
        for (AbstractCard q : AbstractDungeon.player.masterDeck.group) {
            if (q.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                strikes++;
            }
            if (q.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                defends++;
            }
        }
        return ((strikes + defends) > 4);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
