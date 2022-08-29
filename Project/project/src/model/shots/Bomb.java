package project.model.shots;

import project.controller.SpaceInvaders;
import project.util.Direction;

import javax.swing.*;

public class Bomb extends Shot {

    private static final int DAMAGE = 2;
    private static final int SPEED = 4;

    public Bomb(int x, int y) {
        super(new ImageIcon(SpaceInvaders.class.getResource("/bomb1.png")).getImage(), x, y, Direction.DOWN, DAMAGE);
        setAnimation(4);
    }

    @Override
    public void move() {
        setY(getY() + SPEED);
        setImage(new ImageIcon(SpaceInvaders.class.getResource("/bomb" + nextFrame() + ".png")).getImage());
        if (delayIsReached()){
            resetFrame();
        }
    }
}
