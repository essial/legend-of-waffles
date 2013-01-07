package com.lunaticedit.legendofwaffles.implementations.npc;

import com.lunaticedit.legendofwaffles.contracts.NPC;
import com.lunaticedit.legendofwaffles.enums.Demeanor;

public class Crab implements NPC {
    @Override
    public Demeanor getDemeanor() {
        return Demeanor.Aggressive;
    }
}
