package project.model.shots;

import project.controller.SpaceInvaders;
import project.util.Direction;

import javax.swing.*;

public class Bullet extends Shot {

    private static final int DAMAGE = 1;
    private static final int SPEED = 15;

    public Bullet(int x, int y) {
        super(new ImageIcon(SpaceInvaders.class.getResource("/bullet.png")).getImage(), x, y, Direction.UP, DAMAGE);
    }

    @Override
    public void move() {
        setY(getY() - SPEED);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Bullet)) return false;
        Bullet bullet = (Bullet) obj;
        return hashCode() == bullet.hashCode() && getX() == bullet.getX() && getY() == bullet.getY();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + getX();
        hash = 97 * hash + getY();
        return hash;
    }
}
