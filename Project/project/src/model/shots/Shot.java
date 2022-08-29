package project.model.shots;

import project.util.Direction;
import project.model.abstracts.Sprite;

import java.awt.*;

public abstract class Shot extends Sprite {

    protected Direction direction;
    private int damage;

    protected Shot(Image image, int x, int y, Direction direction, int damage) {
        super(image, x, y);
        this.direction = direction;
        this.damage = damage;
    }

    public abstract void move();

    public Direction getDirection() {
        return direction;
    }

    public int getDamage() {
        return damage;
    }
}
