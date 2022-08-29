package project.view;

import oop.lib.Display;
import project.controller.SpaceInvaders;
import project.model.player.Health;
import project.model.shots.Shot;
import project.model.enemies.Enemy;
import project.model.enemies.Group;
import project.model.player.Player;
import project.model.text_and_stats.Letter;
import project.util.MyLinkedList;

import javax.swing.*;
import java.awt.*;

public class Board extends Display {

    private Group<Enemy> enemies;
    private Group<Enemy> explodingEnemies;
    private MyLinkedList<Shot> shotList;
    private Player player;
    private MyLinkedList<Health> healthBar;

    public Board(int width, int height, Color color, Group<Enemy> enemies, Group<Enemy> explodingEnemies, MyLinkedList shotList, Player player, MyLinkedList<Health> healthBar) {
        super(width, height);
        this.setBackground(color);
        this.enemies = enemies;
        this.explodingEnemies = explodingEnemies;
        this.shotList = shotList;
        this.player = player;
        this.healthBar = healthBar;
    }

    @Override
    public void paint(Display display) {
        for (Health health : healthBar){
            health.paint(display);
        }
        enemies.paint(display);
        explodingEnemies.paint(display);
        for(Shot shot : shotList){
                shot.paint(display);
        }
        player.paint(display);

        if (!player.isDead() && enemies.isEmpty()){
            paintText("victory", display, 0);
        }

        if (player.isDead()){
            paintText("game", display, 0);
            paintText("over", display, 1);
        }
    }

    public Letter[] newText(String string, int lineNbr){
        Letter[] text = new Letter[string.length()];
        for (int i = 0; i < string.length(); i++){
            text[i] = new Letter(
                    new ImageIcon(SpaceInvaders.class.getResource("/" + string.charAt(i) + ".png")).getImage(),
                    SpaceInvaders.getBoardWidth()/4 + (i+1) * (SpaceInvaders.getBoardWidth() / 16),
                    SpaceInvaders.getBoardHeight()/3 + 60 * lineNbr
            );
        }
        return text;
    }

    public void paintText(String text, Display display, int lineNbr){
        for (int i = 0; i < text.length(); i++){
            newText(text, lineNbr)[i].paint(display);
        }
    }
}
