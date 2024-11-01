package net.cdc.gui.eventlistener;

import javax.swing.*;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewportEventListener {
    protected static ViewportEventListener instance;

    protected final ArrayList<ListenerGroup<JComponent>> listenerGroups;

    protected ViewportEventListener() {
        this.listenerGroups = new ArrayList<>();
    }

    public static ViewportEventListener getInstance() {
        if (instance == null) {
            instance = new ViewportEventListener();
        }
        return instance;
    }

    /**
     * Tries to add all listeners registered under the listener group id to the component.
     * @param component Component to add listeners to
     * @param listenerGroupId ID of listener group
     * @return True, if adding was successful, false if not (e.g. unsupported component)
     */
    public boolean registerComponent(JComponent component, int listenerGroupId) {
        return this.listenerGroups.get(listenerGroupId).addComponent(component);
    }

    /**
     * Adds listener group to registered listener groups.
     * @param group Group to add
     * @return index (ID) of listener group
     */
    protected int addListenerGroup(ListenerGroup group) {
        this.listenerGroups.add(group);
        return this.listenerGroups.size() - 1;
    }

}
