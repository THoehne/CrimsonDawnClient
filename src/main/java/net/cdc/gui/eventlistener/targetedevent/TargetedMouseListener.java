package net.cdc.gui.eventlistener.targetedevent;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Generic version of MouseListener, to pass a target.
 * @see MouseListener
 */

public abstract class TargetedMouseListener extends TargetedEventListener implements MouseListener {

    public void mouseClicked(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}
}
