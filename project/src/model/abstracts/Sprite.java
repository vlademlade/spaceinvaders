package project.model.abstracts;


import oop.lib.Painting;

import java.awt.Rectangle;

import java.awt.*;
import java.util.Objects;

public abstract class Sprite extends Representation implements Animated {

    /**
     * x-coordinate of the whole sprite
     */
    private int x;
    /**
     * y-coordinate of the whole sprite
     */
    private int y;

    /**
     * Image representing the sprite
     */
    private Image image;
    private int frame;
    private int delay;
    private int nextFrameDelay;

    public Sprite(Image image, int x, int y) {
        this.x = x;
        this.y = y;
        this.image = image;
        nextFrameDelay = 3;
    }

    @Override
    public void paint(Painting painting){
        painting.drawImage(image, x, y);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, getWidth(), getHeight());
    }

    protected boolean checkCollision(Sprite sprite){
        return getBoundingBox().intersects(sprite.getBoundingBox());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth(){
        return image.getWidth(null);
    }

    public int getHeight(){
        return image.getHeight(null);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void setAnimation(int delay){
        this.frame = 1;
        this.delay = delay;
    }

    @Override
    public int nextFrame(){
        return frame++;
    }

    @Override
    public void resetFrame(){
        this.frame = 1;
    }

    @Override
    public boolean delayIsReached(){
        return frame > delay;
    }
}
