package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ConsolationPrize
        extends CustomRelic
        implements BetterOnLoseHpRelic {

    public static final String ID = VexMod.makeID("ConsolationPrize");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ConsolationPrize.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ConsolationPrize.png"));

    public ConsolationPrize() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public int betterOnLoseHp(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && AbstractDungeon.player.currentHealth > 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainGoldAction(damageAmount));
        }
        return damageAmount;
    }

    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 40) && !(AbstractDungeon.getCurrRoom() instanceof ShopRoom);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
