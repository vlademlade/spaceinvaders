package project.model.player;

import project.controller.SpaceInvaders;
import project.model.abstracts.Sprite;
import project.model.shots.Shot;

import javax.swing.*;
import java.awt.*;

public class Health extends Sprite {

    public Health(int x) {
        super(new ImageIcon(SpaceInvaders.class.getResource("/health.png")).getImage(),
                (x+1) * 5 * (SpaceInvaders.getBoardWidth() / 130),
                5);
    }
}
