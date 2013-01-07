package com.lunaticedit.legendofwaffles.contracts;

import com.lunaticedit.legendofwaffles.enums.Facing;
import com.lunaticedit.legendofwaffles.enums.WeaponType;

public interface Attackable {
    public int getHealth();
    public void setHealth(final int value);
    public boolean getStaggered();
    public void setStaggered(final boolean value);
    public int getAttackStrength();
    public int getAttackDefense();
    public void onHitBy(Attackable source);
    public void onKilledBy(Attackable source);
    public void onVictimKilled(Attackable source);
    public long getAttackTime();
    public void setAttackTime(long value);
    public long getAttackedTime();
    public void setAttackedTime(long value);
    public Facing getFacingDirection();
    public int getAttackRange();
    public int getX();
    public int getY();
    public WeaponType getWeaponType();
}
