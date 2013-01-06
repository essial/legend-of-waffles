package com.lunaticedit.legendofwaffles.factories;

import com.lunaticedit.legendofwaffles.contracts.Stage;
import com.lunaticedit.legendofwaffles.implementations.stage.StandardStage;

public class StageFactory {
    public static Stage _stage;

    public Stage generate() {
        if (_stage == null)
        { _stage = new StandardStage(); }

        return _stage;
    }

}
