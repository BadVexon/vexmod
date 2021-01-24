package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.blights.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import java.util.ArrayList;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class RockOfEvil extends CustomRelic {

    public static final String ID = VexMod.makeID("RockOfEvil");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RockOfEvil.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RockOfEvil.png"));

    public RockOfEvil() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(666);
        AbstractDungeon.getCurrRoom().spawnBlightAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), returnRandomBlight());
    }

    public AbstractBlight returnRandomBlight() {
        ArrayList<AbstractBlight> blightList = new ArrayList<>();
        blightList.add(new AncientAugmentation());
        blightList.add(new Durian());
        blightList.add(new GrotesqueTrophy());
        blightList.add(new MimicInfestation());
        blightList.add(new Scatterbrain());
        blightList.add(new TwistingMind());
        blightList.add(new VoidEssence());
        return blightList.get(AbstractDungeon.cardRandomRng.random(blightList.size() - 1));
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}