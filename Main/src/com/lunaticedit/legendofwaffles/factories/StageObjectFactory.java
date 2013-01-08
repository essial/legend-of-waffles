package com.lunaticedit.legendofwaffles.factories;

import com.lunaticedit.legendofwaffles.contracts.StageObject;
import com.lunaticedit.legendofwaffles.implementations.npc.Crab;
import com.lunaticedit.legendofwaffles.implementations.stageobject.CollisionRegion;
import com.lunaticedit.legendofwaffles.implementations.stageobject.ItemBox;

public class StageObjectFactory {
    public StageObject generate(String typeName) {
        if (typeName.equals("Collision Region"))  { return new CollisionRegion(); }
        if (typeName.equals("Coin Box"))          { return new ItemBox();         }
        //if (typeName.equals("Vertical Cannon"))   { processVerticalCannonXML(childElement);  }
        //if (typeName.equals("Warp Zone"))         { processWarpZoneXML(childElement);        }
        //if (typeName.equals("Enemy Path"))        { processEnemyPath(childElement);          }
        //if (typeName.equals("Enemy Girl"))        { processEnemyGirl(childElement);          }
        if (typeName.equals("Enemy Crab"))          { return new Crab();          }
        //if (typeName.equals("Vertical Platform")) { processVerticalPlatform(childElement);   }
        throw new UnsupportedOperationException("Attempted to generate an object of type " + typeName);
    }
}
