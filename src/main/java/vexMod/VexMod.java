package vexMod;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vexMod.cards.EmailVexCard;
import vexMod.cards.StairwayStrike;
import vexMod.cards.UltimateCard;
import vexMod.cards.Virus;
import vexMod.crossovers.AstrologerCrossover;
import vexMod.crossovers.BardCrossover;
import vexMod.events.SpireWellEvent;
import vexMod.relics.*;
import vexMod.util.TextureLoader;

import java.util.Properties;

import static com.megacrit.cardcrawl.core.Settings.language;

@SuppressWarnings("WeakerAccess")
@SpireInitializer
public class VexMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        PostInitializeSubscriber,
        PostUpdateSubscriber,
        StartGameSubscriber {
    public static final Logger logger = LogManager.getLogger(VexMod.class.getName());
    public static final String DISABLE_ENEMIES = "disableEnemies";
    public static final String BADGE_IMAGE = "vexModResources/images/Badge.png";
    private static final String MODNAME = "VexMod";
    private static final String AUTHOR = "DarkVexon";
    private static final String DESCRIPTION = "A major content mod.";
    public static Properties vexModDefaultSettings = new Properties();
    public static boolean disableEnemies = false;
    private static String modID;

    public VexMod() {
        logger.info("baseMod hooks BOYS");

        BaseMod.subscribe(this);
        setModID("vexMod");

        logger.info("aand done with hooks");

        logger.info("settings time LOL");
        vexModDefaultSettings.setProperty(DISABLE_ENEMIES, "FALSE");
        try {
            SpireConfig config = new SpireConfig("vexMod", "vexModConfig", vexModDefaultSettings);
            config.load();
            disableEnemies = config.getBool(DISABLE_ENEMIES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Mod settings concluded.");

    }

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String getModID() {
        return modID;
    }

    public static void setModID(String ID) {
        modID = ID;
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Here comes the vexMod. =========================");
        VexMod vexmod = new VexMod();
        logger.info("========================= vex has infiltrated your PC =========================");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receivePostInitialize() {
        logger.info("it's badge and options time");

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        ModPanel settingsPanel = new ModPanel();

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        BaseMod.addEvent(SpireWellEvent.ID, SpireWellEvent.class);

        logger.info("My image and mod options done.");

    }


    @Override
    public void receiveEditRelics() {
        logger.info("it's a me, relics");

        BaseMod.addRelic(new MemoryStick(), RelicType.SHARED);
        BaseMod.addRelic(new InvisibilityCloak(), RelicType.SHARED);
        BaseMod.addRelic(new RetaliationKit(), RelicType.SHARED);
        BaseMod.addRelic(new ConsolationPrize(), RelicType.SHARED);
        BaseMod.addRelic(new GhostlyGear(), RelicType.SHARED);
        BaseMod.addRelic(new CursedCompass(), RelicType.SHARED);
        BaseMod.addRelic(new Cooldron(), RelicType.SHARED);
        BaseMod.addRelic(new TanglingVine(), RelicType.SHARED);
        BaseMod.addRelic(new FocusSash(), RelicType.SHARED);
        BaseMod.addRelic(new BigBerry(), RelicType.SHARED);
        BaseMod.addRelic(new CardConverter(), RelicType.SHARED);
        BaseMod.addRelic(new ScavengeHelm(), RelicType.SHARED);
        BaseMod.addRelic(new TimeEaterHat(), RelicType.SHARED);
        BaseMod.addRelic(new Dragonfruit(), RelicType.SHARED);
        BaseMod.addRelic(new EndlessSickness(), RelicType.SHARED);
        BaseMod.addRelic(new BerryBomb(), RelicType.SHARED);
        BaseMod.addRelic(new PlasmaPancake(), RelicType.SHARED);
        BaseMod.addRelic(new RedPlottingStone(), RelicType.SHARED);
        BaseMod.addRelic(new DeviousBotling(), RelicType.SHARED);
        BaseMod.addRelic(new ImprovementManual(), RelicType.SHARED);
        BaseMod.addRelic(new PaidLearning(), RelicType.SHARED);
        BaseMod.addRelic(new HeadHunter(), RelicType.SHARED);
        BaseMod.addRelic(new GildedClover(), RelicType.SHARED);
        BaseMod.addRelic(new FluxCapacitor(), RelicType.SHARED);
        BaseMod.addRelic(new MegatonBomb(), RelicType.SHARED);
        BaseMod.addRelic(new BarristanHead(), RelicType.SHARED);
        BaseMod.addRelic(new NewClearReactor(), RelicType.SHARED);
        BaseMod.addRelic(new RockOfEvil(), RelicType.SHARED);
        BaseMod.addRelic(new Spyglass(), RelicType.SHARED);
        BaseMod.addRelic(new MysteryBossChallenge(), RelicType.SHARED);

        if (Loader.isModLoaded("Astrologer")) {
            AstrologerCrossover.Relics();
        }

        if (Loader.isModLoaded("bard")) {
            BardCrossover.Relics();
        }

        logger.info("woo hoo relics be cool");
    }

    @Override
    public void receiveEditCards() {
        logger.info("Variable time.");
        logger.info("Variable added.");
        logger.info("AND NOW, CARDS");
        BaseMod.addCard(new Virus());
        BaseMod.addCard(new StairwayStrike());
        BaseMod.addCard(new EmailVexCard());
        BaseMod.addCard(new UltimateCard());
    }

    private String languageSupport() {
        if (language == Settings.GameLanguage.ZHS) {
            return "zhs";
        }
        return "eng";
    }

    @Override
    public void receiveEditStrings() {
        logger.info("And now, the strings!!!!!");

        String path = "Resources/localization/" + languageSupport() + "/";

        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + path + "vexMod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + path + "vexMod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + path + "vexMod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + path + "vexMod-Event-Strings.json");

        logger.info("Crazy, isn't it?");
    }

    @Override
    public void receivePostUpdate() {
        if (AbstractDungeon.player == null) return;
        if (AbstractDungeon.player.hasRelic(BerryBomb.ID)) BerryBomb.relicBullshit();
        if (AbstractDungeon.player.hasRelic(RedPlottingStone.ID)) RedPlottingStone.FuckShitPoo();
        if (AbstractDungeon.player.hasRelic(MysteryBossChallenge.ID)) MysteryBossChallenge.FuckShitPoo();
        if (AbstractDungeon.player.hasRelic(FluxCapacitor.ID)) {
            if (AbstractDungeon.player.getRelic(FluxCapacitor.ID).counter == -2) {
                FluxCapacitor.relicBullshit();
            }
        }
    }

    @Override
    public void receiveStartGame() {
        Spyglass.nodeList.clear();
    }

}
