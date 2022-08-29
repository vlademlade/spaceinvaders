package project.model.abstracts;

import project.model.shots.Shot;
import project.util.MyLinkedList;

public interface GroupAttacker {

    MyLinkedList<Shot> attack();
}
