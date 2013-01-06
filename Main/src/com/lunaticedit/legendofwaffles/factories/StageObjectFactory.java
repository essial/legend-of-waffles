package com.lunaticedit.legendofwaffles.factories;

import com.lunaticedit.legendofwaffles.contracts.StageObject;

public class StageObjectFactory {
    public StageObject generate(String typeName) {
        //if (typeName.equals("Collision Region"))  { processCollisionRegionXML(childElement); }
        //if (typeName.equals("Coin Box"))          { processCoinBoxXML(childElement);         }
        //if (typeName.equals("Vertical Cannon"))   { processVerticalCannonXML(childElement);  }
        //if (typeName.equals("Warp Zone"))         { processWarpZoneXML(childElement);        }
        //if (typeName.equals("Enemy Path"))        { processEnemyPath(childElement);          }
        //if (typeName.equals("Enemy Girl"))        { processEnemyGirl(childElement);          }
        //if (typeName.equals("Enemy Crab"))        { processEnemyCrab(childElement);          }
        //if (typeName.equals("Vertical Platform")) { processVerticalPlatform(childElement);   }
        throw new UnsupportedOperationException("Attempted to generate an object of type " + typeName);
    }
}
