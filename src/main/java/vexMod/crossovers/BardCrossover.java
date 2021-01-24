package vexMod.crossovers;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import vexMod.relics.*;

public class BardCrossover {
    public static void Relics() {
        BaseMod.addRelicToCustomPool(new PreRecordedMusic(), Bard.Enums.COLOR);
    }
}