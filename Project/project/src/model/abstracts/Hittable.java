package project.model.abstracts;

import project.model.shots.Shot;

public interface Hittable {

    boolean gotHit(Shot shot);
    boolean isDead();
}
