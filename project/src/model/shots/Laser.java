package project.model.shots;

import project.controller.SpaceInvaders;
import project.util.Direction;

import javax.swing.*;

public class Laser extends Shot {

    private static final int DAMAGE = 1;
    private static final int SPEED = 4;

    public Laser(int x, int y) {
        super(new ImageIcon(SpaceInvaders.class.getResource("/laser1.png")).getImage(), x, y, Direction.DOWN, DAMAGE);
        setAnimation(4);
    }

    @Override
    public void move() {
        setY(getY() + SPEED);
        setImage(new ImageIcon(SpaceInvaders.class.getResource("/laser" + nextFrame() + ".png")).getImage());
        if (delayIsReached()){
            resetFrame();
        }
    }
}
