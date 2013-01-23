package com.lunaticedit.legendofwaffles.implementations.repository;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lunaticedit.legendofwaffles.base.ActorBase;
import com.lunaticedit.legendofwaffles.contracts.Animation;
import com.lunaticedit.legendofwaffles.contracts.Attackable;
import com.lunaticedit.legendofwaffles.contracts.Processable;
import com.lunaticedit.legendofwaffles.contracts.Renderable;
import com.lunaticedit.legendofwaffles.enums.Facing;
import com.lunaticedit.legendofwaffles.enums.SceneType;
import com.lunaticedit.legendofwaffles.enums.SoundEffect;
import com.lunaticedit.legendofwaffles.enums.WeaponType;
import com.lunaticedit.legendofwaffles.factories.RepositoryFactory;
import com.lunaticedit.legendofwaffles.factories.SceneFactory;
import com.lunaticedit.legendofwaffles.helpers.Constants;
import com.lunaticedit.legendofwaffles.helpers.Point;
import com.lunaticedit.legendofwaffles.implementations.Input;
import com.lunaticedit.legendofwaffles.implementations.MusicPlayer;
import com.lunaticedit.legendofwaffles.physics.Physics;
import com.lunaticedit.legendofwaffles.services.AnimationService;
import com.lunaticedit.legendofwaffles.services.AttackableServices;

public final class Player
        extends ActorBase
        implements Renderable, Processable, Animation, Attackable {

    private boolean _running;
    private boolean _movingLeft;
    private boolean _movingRight;
    private long _groundTimeStart;
    private boolean _animate;
    private boolean _faceLeft;
    private int _coins;
    private int _maxHealth;
    private int _attackStrength;
    private int _defense;
    private int _attackRange;
    public WeaponType _weaponType;

    public Player() {
        _maxHealth = 4;
        _health = 4;
        _final = false;
        _coins = 0;
        _staggered = false;
        _attackStrength = 1;
        _defense = 0;
        _attackRange = 5;
        _weaponType = WeaponType.ShortSword;
    }
    public int getMaxHealth()
    { return _maxHealth; }
    public WeaponType getWeaponType()
    { return _weaponType; }
    public boolean involved(Body body1, Body body2)
    { return ((body1 == _body) || (body2 == _body)); }
    public void initializePhysics()
    { _body = Physics.getInstance().createHumanoidBody(getX(), getY(), 0.0f, 1.0f); }
    public void setPosition(final int x, final int y) {
        final float meterSize = Physics.toMeters(Constants.TileSize);
        final float halfTile = meterSize / 2.0f;
        _body.setTransform(new Vector2(Physics.toMeters(x) - halfTile, Physics.toMeters(y + 10) - meterSize), 0.0f);
        _body.setLinearVelocity(new Vector2(0.0f, 0.0f));
    }
    public void attach() {
        (new RepositoryFactory()).generate().getObjects().add(this);
        (new AttackableServices(this)).initialize();
    }
    public Point getTileOrigin()
    { return new Point(_currentFrame, _faceLeft ? 3 : 1); }
    public void process() {
        handleInput();
        final Vector2 vel = _body.getLinearVelocity();
        if (_staggered) {
            _animate = false;
            (new AnimationService(this)).update();
            (new AttackableServices(this)).update();
            return;
        }
        if (!(_movingLeft || _movingRight)) { vel.x = 0.0f; _body.setLinearVelocity(vel); }
        final float runMode = _running ? 1.2f : 0.7f;
        if (_movingLeft)  { vel.x = -1.2f * runMode; _body.setLinearVelocity(vel); }
        if (_movingRight) { vel.x =  1.2f * runMode; _body.setLinearVelocity(vel); }
        if ((_movingLeft || _movingRight) && (vel.y != 0.0f)) { _animate = true; }
        else if ((!_movingLeft && !_movingRight) && (vel.y == 0.0f)) {
            _animate = false;
            vel.x *= 0.5f;
            _body.setLinearVelocity(vel);
        }
        double jumpVel = Math.abs(vel.y);
        if (jumpVel > 0.03f) { _groundTimeStart = System.currentTimeMillis(); }
        else if (!_movingRight && !_movingLeft && vel.x != 0.0) { vel.x *= 0.5; _body.setLinearVelocity(vel); }
        (new AnimationService(this)).update();
        (new AttackableServices(this)).update();
    }
    private void handleInput() {
        if (_staggered) {
            _running = false;
            _movingLeft = false;
            _movingRight = false;
        }
        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.Q)) { Gdx.app.exit(); }
        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.Z)) { _running = true; }
        else if (_running) { _running = false; }
        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.DPAD_LEFT)) {
            if (!_movingLeft) {
                _movingLeft = true;
                _movingRight = false;
                _animate = true;
                _faceLeft = true;
            }
        } else if (_movingLeft) {
            _movingLeft = false;
            _animate = false;
        }
        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.DPAD_RIGHT)) {
            if (!_movingRight) {
                _movingRight = true;
                _movingLeft = false;
                _animate = true;
                _faceLeft = false;
            }
        } else if (_movingRight) {
            _movingRight = false;
            _animate = false;
        }
        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.X)) {
            Input.getInstance().setKeyState(com.badlogic.gdx.Input.Keys.X, false);
            if (!canJump()) { return; }
            Vector2 vel = _body.getLinearVelocity();
            vel.y = -4.3f;
            vel.x = 0.0f;
            MusicPlayer.playSound(SoundEffect.Jump);
            _body.applyForce(vel, _body.getWorldCenter());
        }
        if (Input.getInstance().getKeyState(com.badlogic.gdx.Input.Keys.C)) {
            Input.getInstance().setKeyState(com.badlogic.gdx.Input.Keys.C, false);
            doAttack();
        }
    }
    private void doAttack() {
        if (!(new AttackableServices(this)).canAttack()) { return; }
        (new AttackableServices(this)).startAttackAttempt();
        MusicPlayer.playSound(SoundEffect.SwordSwing);
    }
    private boolean canJump() {
        return true;
        //final Vector2 vel = _body.getLinearVelocity();
        //return vel.y == 0.0f;
        //return ((System.currentTimeMillis() - _groundTimeStart) > 20);
    }
    public int getCoins()
    { return _coins; }
    public void adjustCoins(int val)
    { _coins += val; }
    public int getAnimationSpeed()
    { return (_running) ? 100 : 160; }
    public boolean getShouldAnimate()
    { return _animate; }
    public int getAttackStrength()
    { return _attackStrength; }
    public int getAttackDefense()
    { return _defense; }
    public void onHitBy(final Attackable source) {
        final Vector2 vel = _body.getLinearVelocity();
        if (source.getX() < (getX() + 4)) { vel.x =  1.25f; vel.y = -1; }
        else                              { vel.x = -1.25f; vel.y = -1; }
        _body.setLinearVelocity(vel);
    }
    public void onKilledBy(final Attackable source) {
        (new RepositoryFactory())
                .generate()
                .setScene(new SceneFactory()
                        .generateScene(SceneType.Death));
    }
    public void onVictimKilled(final Attackable source) {}
    public Facing getFacingDirection()
    { return _faceLeft ? Facing.Left : Facing.Right; }
    public int getAttackRange()
    { return _attackRange; }
    public void increaseMaxHealth() {
        _maxHealth++;
        _health++;
    }
}
