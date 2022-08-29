package project.model.abstracts;

import java.awt.*;

public interface Animated {

    void setAnimation(int delay);

    int nextFrame();

    void resetFrame();

    boolean delayIsReached();
}
