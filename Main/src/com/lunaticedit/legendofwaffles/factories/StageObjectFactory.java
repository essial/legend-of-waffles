package com.lunaticedit.legendofwaffles.factories;

import com.lunaticedit.legendofwaffles.contracts.StageObject;
import com.lunaticedit.legendofwaffles.implementations.npc.Crab;
import com.lunaticedit.legendofwaffles.implementations.stageobject.*;

public class StageObjectFactory {
    public StageObject generate(String typeName) {
        if (typeName.equals("Collision Region"))    { return new CollisionRegion(); }
        if (typeName.equals("Coin Box"))            { return new ItemBox();         }
        if (typeName.equals("Enemy Crab"))          { return new Crab();            }
        if (typeName.equals("Platform"))            { return new Platform();        }
        if (typeName.equals("Fire Outlet"))         { return new FireOutlet();      }
        if (typeName.equals("Door"))                { return new Door();            }
        if (typeName.equals("Life Up"))             { return new LifeUp();          }
        //if (typeName.equals("Warp Zone"))           { return new WarpZone();        }
        //if (typeName.equals("Vertical Cannon"))   { processVerticalCannonXML(childElement);  }
        throw new UnsupportedOperationException("Attempted to generate an object of type '" + typeName + "'!");
    }
}
