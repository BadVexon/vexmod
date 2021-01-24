package vexMod.crossovers;

import Astrologer.Enums.CardColorEnum;
import basemod.BaseMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.relics.*;

public class AstrologerCrossover {
    public static void Relics() {
        BaseMod.addRelicToCustomPool(new StarEater(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new Trinoculars(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new FakeStar(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new ThrowingStar(), CardColorEnum.ASTROLOGER);
        BaseMod.addRelicToCustomPool(new FutureSight(), CardColorEnum.ASTROLOGER);
    }
}