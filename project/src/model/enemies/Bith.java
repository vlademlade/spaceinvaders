package project.model.enemies;

import project.controller.SpaceInvaders;
import project.model.shots.Bomb;
import project.model.shots.Laser;
import project.model.shots.Shot;
import project.util.Direction;

import javax.swing.*;
import java.util.Random;

public class Bith extends Enemy {

    private static final int HEALTH_POINTS = 1;

    public Bith(int x, int y) {
        super(new ImageIcon(SpaceInvaders.class.getResource("/bith1.png")).getImage(), x, y, HEALTH_POINTS);
        setAnimation(4);
    }

    @Override
    public void move(Direction direction) {
        super.move(direction);
        setImage(new ImageIcon(SpaceInvaders.class.getResource("/bith" + nextFrame() + ".png")).getImage());
        if (delayIsReached()){
            resetFrame();
        }
    }

    @Override
    public Shot attack(){
        random = new Random();
        Laser laser = null;
        if (random.nextDouble() <= firingChance){
            laser = new Laser(getX() + 10, getY() + 20);
        }
        return laser;
    }
}
