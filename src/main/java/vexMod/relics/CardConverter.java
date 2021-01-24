package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class CardConverter extends CustomRelic {


    public static final String ID = VexMod.makeID("CardConverter");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BoxOfBandaids.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BoxOfBandaids.png"));

    public CardConverter() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        AbstractDungeon.player.increaseMaxHp(1, true);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
