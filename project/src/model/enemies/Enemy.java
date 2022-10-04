package project.model.enemies;

import project.controller.SpaceInvaders;
import project.model.abstracts.*;
import project.util.Direction;
import project.model.shots.Shot;

import java.awt.*;
import java.util.Random;

public abstract class Enemy extends Sprite implements Attacker, Hittable, Movable, Damageable {

    private int downStep;
    private int sideStep;
    private int healthPoints;
    private int angerLevel;
    protected double firingChance;
    protected Random random;
    private int level3Duration;

    public Enemy(Image image, int x, int y, int healthPoints) {
        super(image, (x+1) * (SpaceInvaders.getBoardWidth() / 12),
                60 + (y * (SpaceInvaders.getBoardHeight() / 10)));
        this.healthPoints = healthPoints;
        downStep = 5;
        sideStep = 1;
        angerLevel = 0;
        firingChance = 0.005;
        level3Duration = 100;
    }

    @Override
    public boolean gotHit(Shot shot) {
        if (checkCollision(shot)) {
            healthPoints = healthPoints - shot.getDamage();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isDead() {
        return healthPoints == 0;
    }

    @Override
    public void move(Direction direction) {
        if (direction == Direction.RIGHT){
            setX(getX() + sideStep);
        }
        if (direction == Direction.LEFT){
            setX(getX() - sideStep);
        }
        if (direction == Direction.DOWN){
            setY(getY() + downStep);
        }
    }

    @Override
    public boolean isDamaged(){
        return healthPoints == 1;
    }

    @Override
    public void transformIntoDamaged(){
    }

    public boolean isFinishedExploding(){
        nextFrame();
        if (delayIsReached()){
            return true;
        } else {
            return false;
        }
    }

    public void setAngerLevel(int angerLevel) {
        this.angerLevel = angerLevel;
    }

    public void levelUpAngryMode(){
        switch (angerLevel){
            case 1:
                this.sideStep = 3;
                this.downStep = 8;
                this.firingChance = 0.008;
                break;

            case 2:
                this.sideStep = 5;
                this.firingChance = 0.01;
                break;

            case 3:
                if (level3Duration > 0) {
                    this.sideStep = 0;
                    this.downStep = 0;
                    this.firingChance = 0.04;
                    level3Duration--;
                    break;
                }
                if (level3Duration <= 0 && level3Duration > -70){
                    this.sideStep = 10;
                    this.downStep = 10;
                    this.firingChance = 0.03;
                    level3Duration--;
                    break;
                }
                if (level3Duration <= -70){
                    resetLevel3Duration();
                    break;
                }

            case 4:
                this.sideStep = 15;
                this.downStep = 15;
                this.firingChance = 0.1;
                break;

            case 5:
        }
    }

    private void resetLevel3Duration(){
        level3Duration = 90;
    }
}
