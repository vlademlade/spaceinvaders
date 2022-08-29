package project.view;

import oop.lib.Display;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyAnimation implements Runnable, ActionListener {

    private JFrame frame;
    private Timer timer;
    private boolean autoplay;
    private Display display;

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
        display.repaint();
    }

    @Override
    public void run() {
        frame = new JFrame();
        frame.setTitle("My Animation");
        frame.setDefaultCloseOperation(3);
        timer = new Timer(20, this);
        init();
        frame.add(display);
        frame.pack();
        frame.setLocationRelativeTo(null);
        display.requestFocus();
        frame.setVisible(true);
        if (autoplay){
            timer.start();
            timer.setInitialDelay(20);
        }
    }

    protected void init(){
    }

    protected void step(){
    }

    protected synchronized void launch(boolean automatic){
        autoplay = automatic;
        SwingUtilities.invokeLater(this);
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public Timer getTimer() {
        return timer;
    }
}
