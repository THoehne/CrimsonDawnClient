package net.cdc.gui.eventlistener;

import net.cdc.gui.eventlistener.targetedevent.TargetedMouseListener;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.EventListener;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * A listener group wraps multiple listeners into one group, for ease management.
 * It provides a way to very easily add and remove multiple listeners to one object.
 * In combination with {@code TargetedEventListeners}, it makes it possible to add one
 * group of listeners to several {@code JComponents}, ensuring that the action is performed
 * on the target dispatching the event. This means, that the same event listener can be applied to multiple
 * different Objects, while always ensuring its relation to the target Object.
 * @param <T> Defines the JComponent the Listener Group applies to (e.g. JButton or JLabel).
 */
public class ListenerGroup<T extends JComponent> {


     // Identification of the listener types.

    /**
     * @see ActionListener
     */
    public static final int ACTION_LISTENER = 0;
    /**
     * @see AdjustmentListener
     */
    public static final int ADJUST_LISTENER = 1;
    /**
     * @see ComponentListener
     */
    public static final int COMPONENT_LISTENER = 2;
    /**
     * @see ContainerListener
     */
    public static final int CONTAINER_LISTENER = 3;
    /**
     * @see FocusListener
     */
    public static final int FOCUS_LISTENER = 4;
    /**
     * @see HierarchyListener
     */
    public static final int HIERARCHY_LISTENER = 5;
    /**
     * @see InputMethodListener
     */
    public static final int INPUT_METHOD_LISTENER = 6;
    /**
     * @see ItemListener
     */
    public static final int ITEM_LISTENER = 7;
    /**
     * @see MouseListener
     * @see MouseAdapter
     */
    public static final int MOUSE_LISTENER = 8;
    /**
     * @see MouseMotionListener
     * @see MouseAdapter
     */
    public static final int MOUSE_MOTION_LISTENER = 9;
    /**
     * @see MouseWheelListener
     * @see MouseAdapter
     */
    public static final int MOUSE_WHEEL_LISTENER = 10;
    /**
     * @see TextListener
     */
    public static final int TEXT_LISTENER = 11;
    /**
     * @see WindowFocusListener
     * @see WindowAdapter
     */
    public static final int WINDOW_FOCUS_LISTENER = 12;
    /**
     * @see WindowListener
     * @see WindowAdapter
     */
    public static final int WINDOW_LISTENER = 13;
    /**
     * @see WindowStateListener
     * @see WindowAdapter
     */
    public static final int WINDOW_STATE_LISTENER = 14;

    /**
     * Identifier of the type of listener at the common index, in {@code ArrayList<Integer> listenerTypeId}.
     */
    private final ArrayList<Integer> listenerTypeId; // Type of listener (e.g. MouseListener)
    /**
     *  List of listeners. (Type of listener is identified by {@code ArrayList<Integer> listenerTypeId}.
     */
    private final ArrayList<EventListener> listeners;

    public ListenerGroup() {
        this.listeners = new ArrayList<>();
        this.listenerTypeId = new ArrayList<>();
    }

    /**
     * Adds listeners to the component
     * @param comp Component to add listeners to
     * @return True if process successful, false if not.(e.g. wrong type of component).
     */
    public boolean addComponent(T comp) {
        boolean state = true;
        for (int i = 0; i < this.listeners.size(); i++) {
            state = this.setListener(comp, i);
        }

        return state;
    }

    /**
     * Adds a new listener to the listener group.
     * @param listener Listener to add (can be an adapter)
     * @param type Type of listener added to the list of listeners.
     */
    public void addListener(EventListener listener, Integer type) {
        if (!this.listeners.contains(listener)) {
             this.listeners.add(listener);
             this.listenerTypeId.add(type);
        }
    }

    /**
     * Sets the listener for a component.
     * @param comp Component to add listener to.
     * @param id If of listener.
     */
    private boolean setListener(T comp, int id) {
        String methodName;
        Class[] c;

        // Get listener add function and class of listener
        switch (this.listenerTypeId.get(id)) {
            case ACTION_LISTENER:
                methodName = "addActionListener";
                c = new Class[]{ActionListener.class};
                break;
            case ADJUST_LISTENER:
                methodName = "addAdjustListener";
                c = new Class[]{AdjustmentListener.class};
                break;
            case COMPONENT_LISTENER:
                methodName = "addComponentListener";
                c = new Class[]{ComponentListener.class};
                break;
            case CONTAINER_LISTENER:
                methodName = "addContainerListener";
                c = new Class[]{ContainerListener.class};
                break;
            case FOCUS_LISTENER:
                methodName = "addFocusListener";
                c = new Class[]{FocusListener.class};
                break;
            case HIERARCHY_LISTENER:
                methodName = "addHierarchyListener";
                c = new Class[]{HierarchyListener.class};
                break;
            case ITEM_LISTENER:
                methodName = "addItemListener";
                c = new Class[]{ItemListener.class};
                break;
            case INPUT_METHOD_LISTENER:
                methodName = "addInputMethodListener";
                c = new Class[]{InputMethodListener.class};
                break;
            case MOUSE_LISTENER:
                methodName = "addMouseListener";
                c = new Class[]{MouseListener.class};
                break;
            case MOUSE_MOTION_LISTENER:
                methodName = "addMouseMotionListener";
                c = new Class[]{MouseMotionListener.class};
                break;
            case MOUSE_WHEEL_LISTENER:
                methodName = "addMouseWheelListener";
                c = new Class[]{MouseWheelListener.class};
                break;
            case TEXT_LISTENER:
                methodName = "addTextListener";
                c = new Class[]{TextListener.class};
                break;
            case WINDOW_LISTENER:
                methodName = "addWindowListener";
                c = new Class[]{WindowListener.class};
                break;
            case WINDOW_FOCUS_LISTENER:
                methodName = "addWindowFocusListener";
                c = new Class[]{WindowFocusListener.class};
                break;
            case WINDOW_STATE_LISTENER:
                methodName = "addWindowStateListener";
                c = new Class[]{WindowStateListener.class};
                break;
            default:
                return false;
        }

        // Invocation of method
        try {
            Method method = comp.getClass().getMethod( methodName, c );
            // TODO: Copy anonymous listener to make it target specific
            method.invoke(comp, this.listeners.get(id));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return false;
        }

        return true;
    }

    /**
     * Test
     * @param args CLI arguments
     */
    public static void main(String[] args) {
        // Create new ListenerGroup
        ListenerGroup<JLabel> group = new ListenerGroup<>();

        // Random Component
        JLabel label = new JLabel("Test");

        // Add anonymous MouseListener with target to listener group
        group.addListener(new TargetedMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(null, "Hello World!");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                this.getTarget().setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                this.getTarget().setForeground(null);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                this.getTarget().setForeground(Color.RED);
            }
        }, ListenerGroup.MOUSE_LISTENER);

        // Add listeners to label
        group.addComponent(label);

        // Create basic window
        JFrame frame = new JFrame();
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
