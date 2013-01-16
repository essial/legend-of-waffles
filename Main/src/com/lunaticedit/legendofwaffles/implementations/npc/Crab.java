package com.lunaticedit.legendofwaffles.implementations.npc;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lunaticedit.legendofwaffles.base.ActorBase;
import com.lunaticedit.legendofwaffles.contracts.*;
import com.lunaticedit.legendofwaffles.enums.Demeanor;
import com.lunaticedit.legendofwaffles.enums.Facing;
import com.lunaticedit.legendofwaffles.enums.WeaponType;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Point;
import com.lunaticedit.legendofwaffles.physics.HitHandler;
import com.lunaticedit.legendofwaffles.physics.HitWatcher;
import com.lunaticedit.legendofwaffles.physics.Physics;
import com.lunaticedit.legendofwaffles.services.AnimationService;
import com.lunaticedit.legendofwaffles.services.AttackableServices;
import com.lunaticedit.legendofwaffles.services.NPCServices;
import org.w3c.dom.Element;

public final class Crab
        extends ActorBase
        implements NPC, Renderable, Attackable, Processable, StageObject, Animation, HitHandler
{
    public int _startX;
    public int _startY;
    boolean _animate;
    Facing _facing;
    private HitWatcher _hitWatcher;

    public Demeanor getDemeanor()
    { return Demeanor.Aggressive; }
    public int getAttackStrength()
    { return 1; }
    public int getAttackDefense()
    { return 0; }
    public void onHitBy(final Attackable source) {
        // TODO: Hit logic
        final Vector2 vel = _body.getLinearVelocity();
        if (source.getX() < (getX() + 4)) { vel.x = 1.25f; vel.y = -1; }
        else { vel.x = -1.25f; vel.y = -1; }
        _body.setLinearVelocity(vel);
    }
    public void onKilledBy(final Attackable source) {
        _deadX = getX();
        _deadY = getY();
        _final = true;
        _body.setLinearVelocity(new Vector2(0,0));
        (new AnimationService(this)).playFinalAnimation();
        Physics.getInstance().removeHitWatcher(_hitWatcher);
        Physics.getInstance().removeBody(_body);
    }
    public void onVictimKilled(final Attackable source) {}
    public Facing getFacingDirection()
    {  return _facing; }
    public void setFacingDirection(final Facing value)
    { _facing = value; }
    public Vector2 getVelocity()
    { return _body.getLinearVelocity(); }
    public void setVelocity(final Vector2 velocity) {
        if (_staggered) { return; }
        _animate =  ((Math.abs(velocity.x) > 0) && (Math.abs(velocity.y) > 0));
        _body.setLinearVelocity(velocity);
    }
    public Body getBody()
    { return  _body; }
    public int getAttackRange()
    { return 5; }
    public WeaponType getWeaponType()
    { return WeaponType.None; }
    public Point getTileOrigin() {
        if (!_final) { return new Point(4 + _currentFrame, _facing == Facing.Right ? 5 : 7); }
        else { return new Point(4 + _currentFrame, 3); }
    }
    public void process() {
        if (!_final) {
            (new NPCServices(this)).process();
            (new AttackableServices(this)).update();
        }
        (new AnimationService(this)).update();
    }
    private void initializePhysics() {
        _body = Physics.getInstance().createHumanoidBody(_startX, _startY, 0.0f, 1.0f);
        _hitWatcher = new HitWatcher(this, _body);
        Physics.getInstance().registerHitWacher(_hitWatcher);
    }
    public void processXML(final Element element) {
        _animate = true;
        _startX = Integer.parseInt(element.getAttribute("x"));
        _startY = Integer.parseInt(element.getAttribute("y")) - 8;
        _health = 2;
        _facing = Facing.Left;
        (new RepositoryFactory()).generate().getObjects().add(this);
        initializePhysics();
        (new NPCServices(this)).initialize();
    }
    public int getAnimationSpeed()
    { return 200; }
    public boolean getShouldAnimate()
    { return true; }
    public void hitOccurred(final Body body1, final Body body2)
    { (new NPCServices(this)).processHit(body1, body2); }
}
