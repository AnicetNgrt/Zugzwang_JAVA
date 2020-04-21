package ZugCore.Interfaces;

import ZugCore.Classes.ModifierConclusion;
import ZugCore.Classes.ModifierIntroduction;
import ZugCore.Enums.Cardinal;

public interface ModifExpr {
    ModifierConclusion execute(ModifierIntroduction mi, Cardinal orientation);
}