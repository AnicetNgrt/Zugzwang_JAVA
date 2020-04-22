package ZwangCore.Interfaces;

import ZwangCore.Classes.ModifierConclusion;
import ZwangCore.Classes.ModifierIntroduction;
import ZwangCore.Enums.Cardinal;

public interface ModifExpr {
    ModifierConclusion execute(ModifierIntroduction mi, Cardinal orientation);
}