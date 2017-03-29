package com.genealogytree.configuration;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by vanilka on 24/11/2016.
 */
public interface Listenable {

    PropertyChangeSupport getPropertyChangeSupport();

    default void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    default void removerPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }

}
