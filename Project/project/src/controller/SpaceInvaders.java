package project.controller;

import project.model.abstracts.Animated;
import project.model.enemies.*;
import project.model.player.Health;
import project.model.player.Player;
import project.model.shots.Shot;
import project.util.Direction;
import project.util.MyLinkedList;
import project.view.Board;
import project.view.MyAnimation;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SpaceInvaders extends MyAnimation implements Animated {

    private static final int BOARD_WIDTH = 900;
    private static final int BOARD_HEIGHT = 700;
    private static final int ENEMIES_IN_GROUP = 10;
    private Group<Enemy> enemies;
    private Group<Enemy> explodingEnemies;
    private MyLinkedList<Shot> shotList;
    private Player player;
    private MyLinkedList<Health> healthBar;
    private Board board;
    private int playerDamageDuration;
    private int playerDamageFrame;

    @Override
    protected void init() {
        shotList = new MyLinkedList();
        enemies = new Group<>();
        explodingEnemies = new Group<>();
        for (int i = 0; i < ENEMIES_IN_GROUP; i++){
            enemies.add(new AlienShip(i, 1));
            enemies.add(new Yuzzum(i, 2));
            enemies.add(new Duros(i, 3));
            enemies.add(new Bith(i, 4));
        }
        player = new Player(400, 600);
        healthBar = new MyLinkedList<>();
        for (int i = 0; i < player.getHealthPoints(); i++){
            healthBar.add(new Health(i));
        }
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT, Color.BLACK, enemies, explodingEnemies, shotList, player, healthBar);
        board.addKeyListener(new LocalKeyListener());
        setDisplay(board);
        this.setAnimation(30);
    }

    @Override
    protected void step() {
        if (player.isDamaged()) {
            playPlayerDamagedMode();
        } else {
            if (player.isRecovering()){
                playRecoveryMode();
            } else {
                if (!explodingEnemies.isEmpty()){
                    playEnnemyExplosionMode();
                } else {
                    playDefaultShootingMode();
                }
            }
        }
    }

    public void playDefaultShootingMode(){
        resolveCollisionsAndExplosions();
        if (player.isDead()){
            for (Shot shot : shotList) {
                shotList.remove(shot);
            }
            enemies.removeAll();
        } else {
            if (enemies.getSize() != 0) {
                enemies.moveGroup();
                shotList.addAll(enemies.attack());
                for (Shot shot : shotList){
                    shot.move();
                }
                player.move();
                Shot shot = player.attack();
                if (shot != null) {
                    shotList.add(shot);
                }
            } else {
                for (Shot shot : shotList) {
                    shotList.remove(shot);
                }
                player.move();
            }
        }
    }

    public void playEnnemyExplosionMode(){
        resolveCollisionsAndExplosions();
        if (enemies.getSize() != 0) {

            for (Shot shot : shotList){
                shot.move();
            }
            player.move();
            Shot shot = player.attack();
            if (shot != null) {
                shotList.add(shot);
            }
        }
    }

    public void playPlayerDamagedMode(){
        if (delayIsReached()) {
            player.setIsDamaged(false);
            if (!player.isDead()) {
                player.setIsRecovering(true);
                player.setImage(new ImageIcon(SpaceInvaders.class.getResource("/player.png")).getImage());
                resetFrame();
            }
        } else {
            player.transformIntoDamaged();
            nextFrame();
        }
    }

    public void playRecoveryMode(){
        if (delayIsReached()) {
            player.setIsRecovering(false);
            player.setImage(new ImageIcon(SpaceInvaders.class.getResource("/player.png")).getImage());
            resetFrame();
        } else {
            player.recover();
            enemies.moveGroup();
            shotList.addAll(enemies.attack());
            for (Shot shot : shotList){
                shot.move();
            }
            player.move();
            nextFrame();
        }
    }

    private void resolveCollisionsAndExplosions(){
        MyLinkedList<Shot> shotsToRemove = new MyLinkedList<>();
        for (Shot shot : shotList){
            if (shot.getDirection() == Direction.UP){
                if (enemies.gotHit(shot)){
                    shotsToRemove.add(shot);
                }
            }
            if (shot.getDirection() == Direction.DOWN){
                if (player.gotHit(shot)){
                    shotsToRemove.add(shot);
                    player.setIsDamaged(true);
                    resolveHealthBar(shot);
                }
            }
            if (shot.getY() < 0 || shot.getY() > BOARD_HEIGHT){
                shotsToRemove.add(shot);
            }
        }
        for (Shot shotToRemove : shotsToRemove){
            shotList.remove(shotToRemove);
        }
        explodingEnemies.resolveExplosions();
        for (Point coordinates : enemies.retrieveDeadEnemiesCoordinates()){
            explodingEnemies.add(new ExplodingEnemy(coordinates.x, coordinates.y));
        }
        enemies.resolveHitEnemies();
        enemies.resolveAngerState();
        if (enemies.getMaxY() > player.getY()){
            player.setHealthPoints(0);
        }
    }

    public void resolveHealthBar(Shot shot){
        for (int i = 0; i < shot.getDamage(); i++){
            if (shot.getDamage() > healthBar.size()) {
                healthBar.remove(0);
                break;
            } else {
                healthBar.remove(healthBar.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        new SpaceInvaders().launch(true);
    }

    private class LocalKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                player.setLeftIsPressed(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                player.setRightIsPressed(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE){
                player.setIsFiring(true);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                player.setLeftIsPressed(false);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                player.setRightIsPressed(false);
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE){
                player.setIsFiring(false);
            }
        }
    }

    public static int getBoardWidth() {
        return BOARD_WIDTH;
    }

    public static int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    @Override
    public void setAnimation(int delay) {
        playerDamageDuration = delay;
        playerDamageFrame = 1;
    }

    @Override
    public int nextFrame() {
        return playerDamageFrame++;
    }

    @Override
    public void resetFrame() {
        playerDamageFrame = 1;
    }

    @Override
    public boolean delayIsReached() {
        return playerDamageFrame > playerDamageDuration;
    }
}
