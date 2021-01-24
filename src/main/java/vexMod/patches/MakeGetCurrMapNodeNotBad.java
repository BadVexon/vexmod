package vexMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import vexMod.relics.Spyglass;

public class MakeGetCurrMapNodeNotBad {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "nextRoomTransition",
            paramtypez = {
                    SaveFile.class
            }
    )

    public static class CurrMapNodeSteal {
        public static void Prefix(AbstractDungeon __instance, SaveFile savefile) {
            Spyglass.realCurrNode = AbstractDungeon.nextRoom;
        }
    }
}
