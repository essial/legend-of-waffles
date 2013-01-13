package com.lunaticedit.legendofwaffles.implementations.npc;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lunaticedit.legendofwaffles.contracts.*;
import com.lunaticedit.legendofwaffles.enums.Demeanor;
import com.lunaticedit.legendofwaffles.enums.Facing;
import com.lunaticedit.legendofwaffles.enums.WeaponType;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.helpers.Dimension;
import com.lunaticedit.legendofwaffles.helpers.Point;
import com.lunaticedit.legendofwaffles.physics.HitHandler;
import com.lunaticedit.legendofwaffles.physics.HitWatcher;
import com.lunaticedit.legendofwaffles.physics.Physics;
import com.lunaticedit.legendofwaffles.services.AnimationService;
import com.lunaticedit.legendofwaffles.services.AttackableServices;
import com.lunaticedit.legendofwaffles.services.NPCServices;
import org.w3c.dom.Element;

public class Crab implements NPC, Renderable, Attackable, Processable, StageObject, Animation, HitHandler {
    private Body _body;
    public int _startX;
    public int _startY;
    private int _health;
    private boolean _staggered;
    private long _attackTime;
    private long _attackedTime;
    private Facing _facing;
    private boolean _animate;
    private int _animationFrame;
    private long _animationTime;
    private HitWatcher _hitWatcher;


    @Override
    public Demeanor getDemeanor() {
        return Demeanor.Aggressive;
    }

    @Override
    public int getHealth() {
        return _health;
    }

    @Override
    public void setHealth(final int value) {
        _health = value;
    }

    @Override
    public boolean getStaggered() {
        return _staggered;
    }

    @Override
    public void setStaggered(final boolean value) {
        _staggered = value;
    }

    @Override
    public int getAttackStrength() {
        return 1;
    }

    @Override
    public int getAttackDefense() {
        return 0;
    }

    @Override
    public void onHitBy(final Attackable source) {
        // TODO: Hit logic
        System.err.printf("Attacked!");
    }

    @Override
    public void onKilledBy(final Attackable source) {
        // TODO: Death logic
    }

    @Override
    public void onVictimKilled(final Attackable source) {

    }

    @Override
    public long getAttackTime() {
        return _attackTime;
    }

    @Override
    public void setAttackTime(final long value) {
        _attackTime = value;
    }

    @Override
    public long getAttackedTime() {
        return _attackedTime;
    }

    @Override
    public void setAttackedTime(final long value) {
        _attackedTime = value;
    }

    @Override
    public Facing getFacingDirection() {
        return _facing;
    }

    @Override
    public void setFacingDirection(final Facing value) {
        _facing = value;
    }

    @Override
    public Vector2 getVelocity() {
        return _body.getLinearVelocity();
    }

    @Override
    public void setVelocity(final Vector2 velocity) {
        _animate =  ((Math.abs(velocity.x) > 0) && (Math.abs(velocity.y) > 0));
        _body.setLinearVelocity(velocity);
    }

    @Override
    public Body getBody() {
        return _body;
    }

    @Override
    public int getAttackRange() {
        return 5;
    }

    @Override
    public int getX() {
        if (_body == null)
        { return 0; }

        final Vector2 position = _body.getPosition();
        return Physics.toPixels(position.x) + 5;
    }

    @Override
    public int getY() {
        if (_body == null)
        { return 0; }

        final Vector2 position = _body.getPosition();
        return Physics.toPixels(position.y) - 3;
    }

    @Override
    public WeaponType getWeaponType() {
        return WeaponType.None;
    }

    @Override
    public boolean getVisible() {
        return true;
    }

    @Override
    public Dimension getTileSize() {
        return new Dimension(1, 2);
    }

    @Override
    public Point getTileOrigin() {
        return new Point(4 + _animationFrame, _facing == Facing.Right ? 5 : 7);
    }

    @Override
    public void process() {

        (new NPCServices(this))
                .process();

        (new AnimationService(this))
                .update();

        (new AttackableServices(this)).
                update();
    }

    private void initializePhysics() {
        _body = Physics.getInstance().createHumanoidBody(_startX, _startY, 0.0f, 1.0f);
        _hitWatcher = new HitWatcher(this, _body);
        Physics.getInstance().registerHitWacher(_hitWatcher);
    }

    @Override
    public void processXML(final Element element) {
        _animate = true;
        _startX = Integer.parseInt(element.getAttribute("x"));
        _startY = Integer.parseInt(element.getAttribute("y")) - 8;
        _health = 2;
        _facing = Facing.Left;

        // Attach to the repository
        (new RepositoryFactory())
                .generate()
                .getObjects()
                .add(this);

        initializePhysics();

        (new NPCServices(this))
                .initialize();
    }

    @Override
    public int getAnimationSpeed() {
        return 200;
    }

    @Override
    public boolean getShouldAnimate() {
        return true;
    }

    @Override
    public int getCurrentFrame() {
        return _animationFrame;
    }

    @Override
    public void setCurrentFrame(final int value) {
        _animationFrame = value;
    }

    @Override
    public long getAnimationTime() {
        return _animationTime;
    }

    @Override
    public void setAnimationTime(final long value) {
        _animationTime = value;
    }

    @Override
    public void HitOccurred(final Body body1, final Body body2) {

        (new NPCServices(this))
                .processHit(body1, body2);

    }
}
