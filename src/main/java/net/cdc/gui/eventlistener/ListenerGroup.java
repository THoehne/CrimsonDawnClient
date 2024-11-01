package net.cdc.gui.eventlistener;

import net.cdc.gui.Window;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.EventListener;

import javax.swing.*;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ListenerGroup<T extends JComponent> {

    public static final int ACTION_LISTENER = 0;
    public static final int ADJUST_LISTENER = 1;
    public static final int COMPONENT_LISTENER = 2;
    public static final int CONTAINER_LISTENER = 3;
    public static final int FOCUS_LISTENER = 4;
    public static final int HIERARCHY_LISTENER = 5;
    public static final int INPUT_METHOD_LISTENER = 6;
    public static final int ITEM_LISTENER = 7;
    public static final int MOUSE_LISTENER = 8;
    public static final int MOUSE_MOTION_LISTENER = 9;
    public static final int MOUSE_WHEEL_LISTENER = 10;
    public static final int TEXT_LISTENER = 11;
    public static final int WINDOW_FOCUS_LISTENER = 12;
    public static final int WINDOW_LISTENER = 13;
    public static final int WINDOW_STATE_LISTENER = 14;


    private final ArrayList<Integer> listenerTypeId; // Type of listener (e.g. MouseListener)
    private final ArrayList<EventListener> listeners;

    public ListenerGroup() {
        this.listeners = new ArrayList<>();
        this.listenerTypeId = new ArrayList<>();

        JLabel label = new JLabel();
    }

    /**
     * Adds set listeners to the component
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
        String methodName = "";
        Class[] c = null;
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
            // TODO: Copy event anonymous listener. Add target to copied listener.
            method.invoke(comp, this.listeners.get(id));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        ListenerGroup<JLabel> group = new ListenerGroup<>();

        JLabel label = new JLabel("Test");

        group.addListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                label.setForeground(Color.BLUE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                label.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                label.setForeground(null);
            }
        }, ListenerGroup.MOUSE_LISTENER);

        group.addComponent(label);

        JFrame frame = new JFrame();
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
