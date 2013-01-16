package com.lunaticedit.legendofwaffles.services;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lunaticedit.legendofwaffles.contracts.Attackable;
import com.lunaticedit.legendofwaffles.contracts.NPC;
import com.lunaticedit.legendofwaffles.enums.Demeanor;
import com.lunaticedit.legendofwaffles.enums.Facing;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;

public class NPCServices {
    private final NPC _npc;

    public NPCServices(NPC npc) {
        _npc = npc;
    }
    public void initialize() {
    }
    public void process() {
        Vector2 vel = _npc.getVelocity();
        switch(_npc.getFacingDirection()) {
            case Right: { vel.x = 0.4f;  } break;
            case  Left: { vel.x = -0.4f; } break;
        }
        _npc.setVelocity(vel);
    }
    public void processHit(Body body1, Body body2) {
        if ((new RepositoryFactory()).generate().getPlayer().involved(body1, body2)) {
            if (_npc.getDemeanor() == Demeanor.Passive) {
                turnAround();
                return;
            }
            if (!(_npc instanceof Attackable)) { return; }
            (new AttackableServices((Attackable)_npc)).attack((new RepositoryFactory()).generate().getPlayer());
        } else {
            turnAround();
        }
    }
    private void turnAround() {
        switch (_npc.getFacingDirection()) {
            case  Left: _npc.setFacingDirection(Facing.Right); break;
            case Right: _npc.setFacingDirection(Facing.Left);  break;
        }
    }
}
