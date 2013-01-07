package com.lunaticedit.legendofwaffles.services;

import com.lunaticedit.legendofwaffles.contracts.Attackable;
import com.lunaticedit.legendofwaffles.enums.Facing;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;

import java.awt.geom.Point2D;

public class AttackableServices {
    private Attackable _attackable;

    public AttackableServices(Attackable attackable) {
        _attackable = attackable;
    }

    public void initialize() {
        _attackable.setAttackTime(0);
        _attackable.setAttackedTime(0);
        _attackable.setStaggered(false);
        (new RepositoryFactory())
                .generate()
                .getObjects();
    }

    public void update() {
        if (_attackable.getStaggered()) {
            if ((System.currentTimeMillis() - _attackable.getAttackedTime()) > Constants.StaggerDuration) {
                _attackable.setStaggered(false);
            }
        }

        if (!isAttacking())
        { return; }

        final int centerX =
                (_attackable.getX() + 4) +
                        ((_attackable.getFacingDirection() == Facing.Left)
                                ? -8
                                : 8
                        );
        final int centerY = (_attackable.getY() + 8);


        for(Object v : (new RepositoryFactory()).generate().getObjects()) {
            if (!(v instanceof Attackable))
            { continue; }
            Attackable victim = (Attackable) v;

            // Don't process the actor as the victim
            if (victim == _attackable) {
                continue;
            }

            // Determine how close we are to the victim
            final int enemyX = (victim.getX() + 4);
            final int enemyY = (victim.getY() + 8);

            if (Point2D.distance(centerX, centerY, enemyX, enemyY) < _attackable.getAttackRange())
            { attack(victim); }
        }
    }

    public boolean canAttack() {
        return (System.currentTimeMillis() - _attackable.getAttackTime()) > Constants.AttackSpacing;
    }

    public boolean isAttacking() {
        return (System.currentTimeMillis() - _attackable.getAttackTime()) < Constants.AttackDuration;
    }

    public void attack(final Attackable victim) {

        // Do not process attacks on a victim that is currently being staggered
        if (victim.getStaggered()) {
            return;
        }

        // Do not process attack on a victim that has just been attacked
        if ((System.currentTimeMillis() - victim.getAttackedTime()) < Constants.StaggerDuration) {
            return;
        }
        victim.setAttackedTime(System.currentTimeMillis());

        // Determine the damage that will be dealt
        final int damageToDeal =
                Math.min(
                        // Victim
                        victim.getHealth(),
                        // At least 1 damage must take place
                        Math.max(1, _attackable.getAttackStrength() - victim.getAttackDefense())
                );

        // Apply damage to the victim
        victim.setHealth(victim.getHealth() - damageToDeal);

        victim.onHitBy(_attackable);
        victim.setStaggered(true);

        if (victim.getHealth() > 0) {
            return;
        }

        victim.onKilledBy(_attackable);
        _attackable.onVictimKilled(victim);
    }

    public void startAttackAttempt() {
        _attackable.setAttackTime(System.currentTimeMillis());
    }
}
