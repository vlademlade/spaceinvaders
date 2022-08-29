package project.model.enemies;

import oop.lib.Paintable;
import oop.lib.Painting;
import project.controller.SpaceInvaders;
import project.model.abstracts.*;
import project.model.shots.Shot;
import project.util.Direction;
import project.util.MyLinkedList;

import java.awt.*;
import java.util.Collections;

public class Group<E extends Enemy> implements GroupMovable, GroupAttacker, Hittable, Paintable {

    private MyLinkedList<E> enemies;
    private static Direction direction = Direction.RIGHT;

    public Group() {
        enemies = new MyLinkedList<>();
    }

    @Override
    public void paint(Painting painting) {
        for(Enemy enemy : enemies){
            enemy.paint(painting);
        }
    }

    @Override
    public MyLinkedList<Shot> attack() {
        MyLinkedList<Shot> shots = new MyLinkedList<>();
        MyLinkedList<E> shootingEnemies = new MyLinkedList<>();
        for (E enemy : enemies){
            Group<Enemy> xCoordinateAllies = new Group<>();
            for (E ally : enemies){
                if (enemy.getX() == ally.getX()){
                    xCoordinateAllies.add(ally);
                }
            }
            if (enemy.getY() >= xCoordinateAllies.getMaxY()){
                shootingEnemies.add(enemy);
            }
        }
        for (E shootingEnemy : shootingEnemies){
            Shot shot = shootingEnemy.attack();
            if (shot != null) {
                shots.add(shot);
            }
        }
        return shots;
    }

    @Override
    public boolean gotHit(Shot shot) {
        for (E enemy : enemies){
            if (enemy.gotHit(shot)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDead() {
        if (enemies.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void moveGroup(){
        for (E enemy : enemies){
            enemy.move(direction);
        }
        if (getMaxX() >= SpaceInvaders.getBoardWidth() - this.getWidth()){
            for (E enemy : enemies){
                enemy.move(Direction.DOWN);
            }
            direction = Direction.LEFT;
        }
        if (this.getMinX() <= 0){
            for (E enemy : enemies){
                enemy.move(Direction.DOWN);
            }
            direction = Direction.RIGHT;
        }
    }

    public int getMaxX(){
        MyLinkedList<Integer> xCoordinates = new MyLinkedList<>();
        for (E enemy : enemies) {
            xCoordinates.add(enemy.getX());
        }
        return Collections.max(xCoordinates);
    }

    public int getMinX(){
        MyLinkedList<Integer> xCoordinates = new MyLinkedList<>();
        for (E enemy : enemies) {
            xCoordinates.add(enemy.getX());
        }
        return Collections.min(xCoordinates);
    }

    public int getMaxY(){
        MyLinkedList<Integer> yCoordinates = new MyLinkedList<>();
        for (E enemy : enemies) {
            yCoordinates.add(enemy.getY());
        }
        if (yCoordinates.size() > 0) {
            return Collections.max(yCoordinates);
        } else {
            return -1;
        }
    }

    public int getWidth(){
        if (!enemies.isEmpty()) {
            return enemies.get(0).getWidth();
        } else{
            return 0;
        }
    }

    public int getHeight(){
        if (!enemies.isEmpty()) {
            return enemies.get(0).getHeight();
        } else{
            return 0;
        }
    }

    public void resolveExplosions(){
        MyLinkedList<E> enemiesToRemove = new MyLinkedList<>();
        for (E enemy : enemies){
            if (enemy.isFinishedExploding()){
                enemiesToRemove.add(enemy);
            }
        }
        for (E enemyToRemove : enemiesToRemove){
            enemies.remove(enemyToRemove);
        }
    }

    public MyLinkedList<Point> retrieveDeadEnemiesCoordinates(){
        MyLinkedList<Point> deadEnemiesCoordinates = new MyLinkedList<>();
        for (E enemy : enemies){
            if (enemy.isDead()){
                int x = enemy.getX();
                int y = enemy.getY();
                deadEnemiesCoordinates.add(new Point(x,y));
            }
        }
        return deadEnemiesCoordinates;
    }

    public void resolveHitEnemies(){
        MyLinkedList<E> enemiesToRemove = new MyLinkedList<>();
        for (E enemy : enemies){
            if (enemy.isDead()){
                enemiesToRemove.add(enemy);
            }
            if (enemy.isDamaged()){
                enemy.transformIntoDamaged();
            }
        }
        for (E enemyToRemove : enemiesToRemove){
            enemies.remove(enemyToRemove);
        }
    }

    public void resolveAngerState(){
        if (getSize() <= 30 && getSize() > 15){
            for (E enemy : enemies){
                enemy.setAngerLevel(1);
            }
        }
        if (getSize() <= 15 && getSize() > 10){
            for (E enemy : enemies){
                enemy.setAngerLevel(2);
            }
        }
        if (getSize() <= 10 && getSize() > 1){
            for (E enemy : enemies){
                enemy.setAngerLevel(3);
            }
        }
        if (getSize() == 1){
            for (E enemy : enemies){
                enemy.setAngerLevel(4);
            }
        }
        for (E enemy : enemies){
            enemy.levelUpAngryMode();
        }
    }

    public void add(E enemy){
        enemies.add(enemy);
    }

    public void removeAll(){
        for (E enemy : enemies){
            enemies.remove(enemy);
        }
    }

    public int getSize(){
        return enemies.size();
    }

    public boolean isEmpty(){
        return enemies.isEmpty();
    }
}
