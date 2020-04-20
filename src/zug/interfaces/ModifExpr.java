package zug.interfaces;

import zug.classes.ModifierConclusion;
import zug.classes.ModifierIntroduction;
import zug.enums.Cardinal;

public interface ModifExpr {
    ModifierConclusion execute(ModifierIntroduction mi, Cardinal orientation);
}