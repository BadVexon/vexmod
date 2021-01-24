package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vexMod.VexMod;
import vexMod.helpers.OctoChoiceCard;
import vexMod.patches.CenterGridCardSelectScreen;
import vexMod.util.TextureLoader;

import java.util.ArrayList;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class MysteryBossChallenge extends CustomRelic {


    public static final String ID = VexMod.makeID("MysteryBossChallenge");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("MindDevourer.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("MindDevourer.png"));

    private boolean cardSelected = true;
    private static boolean gainEnergy;
    private static boolean gainNonEnergy;

    public MysteryBossChallenge() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CenterGridCardSelectScreen.centerGridSelect = true;
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.addToTop(new OctoChoiceCard("Energy", "Energy", VexMod.makeCardPath("Energy.png"), "Obtain a random Energy Boss relic.", AbstractCard.CardType.SKILL));
        group.addToTop(new OctoChoiceCard("Non-Energy", "Non-Energy", VexMod.makeCardPath("NonEnergy.png"), "Obtain a random non-Energy Boss relic.", AbstractCard.CardType.SKILL));
        AbstractDungeon.gridSelectScreen.open(group, 1, this.DESCRIPTIONS[1], false, false, false, false);
    }

    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            this.cardSelected = true;
            CenterGridCardSelectScreen.centerGridSelect = false;

            if (AbstractDungeon.gridSelectScreen.selectedCards.get(0).name.equals("Non-Energy")) {
                gainNonEnergy = true;
            }
            if (AbstractDungeon.gridSelectScreen.selectedCards.get(0).name.equals("Energy")) {
                gainEnergy = true;
            }

            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }

    public static void FuckShitPoo() {
        if (gainEnergy) {
            gainEnergy = false;
            ArrayList<AbstractRelic> energyList = new ArrayList<>();
            {
                for (AbstractRelic r : RelicLibrary.bossList) {
                    if (r.description.contains("Gain [E]") || r.description.contains("Gain [R]") || r.description.contains("Gain [G]")) {
                        energyList.add(r);
                    }
                }
            }
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, energyList.get(AbstractDungeon.relicRng.random(energyList.size() - 1)));
        }
        if (gainNonEnergy) {
            gainNonEnergy = false;
            ArrayList<AbstractRelic> energyList = new ArrayList<>();
            {
                for (AbstractRelic r : RelicLibrary.bossList) {
                    if (!r.description.contains("Gain [E]")) {
                        energyList.add(r);
                    }
                }
            }
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, energyList.get(AbstractDungeon.relicRng.random(energyList.size() - 1)));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
