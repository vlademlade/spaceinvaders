package project.model.enemies;

import project.controller.SpaceInvaders;
import project.model.shots.Bomb;
import project.model.shots.Shot;

import javax.swing.*;
import java.util.Random;

public class ExplodingEnemy extends Enemy {

    public ExplodingEnemy(int x, int y) {
        super(new ImageIcon(SpaceInvaders.class.getResource("/exploding_enemy.png")).getImage(), x, y, 0);
        setX(x);
        setY(y);
        setAnimation(5);
    }

    @Override
    public Shot attack(){
        return null;
    }
}
