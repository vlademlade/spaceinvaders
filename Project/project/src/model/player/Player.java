package project.model.player;

import project.controller.SpaceInvaders;
import project.model.abstracts.*;
import project.model.shots.Bullet;
import project.model.shots.Shot;

import javax.swing.*;

public class Player extends Sprite implements Attacker, Hittable, Controllable, Damageable {

    private int healthPoints;
    private static final int SIDE_STEP = 7;
    private static final long COOL_DOWN = 300L;
    private boolean isFiring;
    private long lastShot;
    private boolean leftIsPressed;
    private boolean rightIsPressed;
    private boolean isDamaged;
    private boolean isRecovering;

    public Player(int x, int y) {
        super(new ImageIcon(SpaceInvaders.class.getResource("/player.png")).getImage(), x, y);
        healthPoints = 4;
        setAnimation(2);
        isDamaged = false;
    }

    @Override
    public Shot attack() {
        Bullet bullet = null;
        if (isFiring) {
            if(lastShot == 0L || System.currentTimeMillis() - lastShot >= COOL_DOWN ) {
                bullet = new Bullet(getX() + 14, getY() - 20);
                lastShot = System.currentTimeMillis();
            }
        }
        return bullet;
    }

    @Override
    public boolean gotHit(Shot shot) {
        if (checkCollision(shot)){
            healthPoints = healthPoints - shot.getDamage();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isDead() {
        return healthPoints <= 0;
    }

    @Override
    public void move() {
        if(rightIsPressed){
            if (getX() < SpaceInvaders.getBoardWidth() - getWidth()) {
                setX(getX() + SIDE_STEP);
            }
        }

        if(leftIsPressed){
            if (getX() > 0) {
                setX(getX() - SIDE_STEP);
            }
        }
    }

    @Override
    public void transformIntoDamaged(){
        setImage(new ImageIcon(SpaceInvaders.class.getResource("/damaged_player" + nextFrame() + ".png")).getImage());
        if (delayIsReached()){
            resetFrame();
        }
    }

    public void recover(){
        setImage(new ImageIcon(SpaceInvaders.class.getResource("/recovering_player" + nextFrame() + ".png")).getImage());
        if (delayIsReached()){
            resetFrame();
        }
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setIsFiring(boolean isFiring) {
        this.isFiring = isFiring;
    }

    public void setLeftIsPressed(boolean leftIsPressed) {
        this.leftIsPressed = leftIsPressed;
    }

    public void setRightIsPressed(boolean rightIsPressed) {
        this.rightIsPressed = rightIsPressed;
    }

    public void setIsDamaged(boolean isDamaged){
        this.isDamaged = isDamaged;
    }

    @Override
    public boolean isDamaged(){
        return isDamaged;
    }

    public boolean isRecovering(){
        return isRecovering;
    }

    public void setIsRecovering(boolean isRecovering){
        this.isRecovering = isRecovering;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}
