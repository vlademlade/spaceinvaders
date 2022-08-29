package project.model.enemies;

import project.controller.SpaceInvaders;
import project.model.shots.Bomb;
import project.model.shots.Shot;
import javax.swing.ImageIcon;
import java.util.Random;

public class AlienShip extends Enemy {

    public AlienShip(int x, int y) {
        super(new ImageIcon(SpaceInvaders.class.getResource("/alien_ship.png")).getImage(), x , y, 2);
    }

    @Override
    public void transformIntoDamaged() {
        setImage(new ImageIcon(SpaceInvaders.class.getResource("/damaged_ship.png")).getImage());
    }

    @Override
    public Shot attack(){
        random = new Random();
        Bomb bomb = null;
        if (random.nextDouble() <= firingChance){
            bomb = new Bomb(getX() + 15, getY() + 20);
        }
        return bomb;
    }
}
