package vexMod.relics;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import de.robojumper.ststwitch.TwitchConfig;
import vexMod.VexMod;
import vexMod.actions.YeetPlayerAction;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class FluxCapacitor extends CustomRelic implements CustomSavable<Float> {

    public static final String ID = VexMod.makeID("FluxCapacitor");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FluxCapacitor.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FluxCapacitor.png"));

    public FluxCapacitor() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    private float timeCounter = -1;

    public static void relicBullshit() {
        if (CardCrawlGame.playtime >= 0.0F && !AbstractDungeon.player.isDead) {
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
        }
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
        flash();
        timeCounter = 600;
        setCounter((int) timeCounter);
    }

    @Override
    public void setCounter(int counter) {
        if (counter != this.counter) {
            if (counter == 600) {
                beginLongPulse();
            }
            if (counter <= 60) {
                stopPulse();
                flash();
            }
        }
        super.setCounter(counter);
    }

    private boolean isStopped() {
        return CardCrawlGame.stopClock;
    }

    @Override
    public void update() {
        super.update();
        if (counter > 0) {
            if (!isStopped()) {
                timeCounter -= Gdx.graphics.getRawDeltaTime();
                setCounter((int) timeCounter);
            }
        } else if (counter == 0) {
            if (!AbstractDungeon.player.isDead && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                flash();
                TimeWarpTurnEndEffect effect = new TimeWarpTurnEndEffect();
                TextureAtlas.AtlasRegion region = new TextureAtlas.AtlasRegion(img, 0, 0, img.getWidth(), img.getHeight());
                ReflectionHacks.setPrivate(effect, TimeWarpTurnEndEffect.class, "img", region);
                AbstractDungeon.topLevelEffectsQueue.add(effect);
                AbstractDungeon.actionManager.addToTop(new YeetPlayerAction());
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            } else {
                counter = -2;
            }
        }
    }

    @Override
    public void onVictory() {
        this.flash();
        if (TwitchConfig.readConfig().get().isEnabled()) {
            timeCounter += (60.0F + TwitchConfig.readConfig().get().getTimer());
            setCounter((int) (counter + (60.0F + TwitchConfig.readConfig().get().getTimer())));
        } else {
            timeCounter += 60;
            setCounter(counter + 60);
        }
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public boolean canSpawn() {
        return (AbstractDungeon.actNum == 1);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public Float onSave() {
        return timeCounter;
    }

    @Override
    public void onLoad(Float aFloat) {
        timeCounter = aFloat;
    }
}