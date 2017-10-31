package io.realm;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Nabil on 30/10/2017.
 */
public class Realm {
    CopyOnWriteArrayList<RealmChangeListener<Realm>> listeners = new CopyOnWriteArrayList<>();

    public void sayHi () {
        System.out.println("Hi from Realm");
    }

    public void commit() {
        System.out.println("Commit and notify changes");
        for (RealmChangeListener<Realm> listener : listeners) {
            listener.onChange(this);
        }
    }

    public void addChangeListener (RealmChangeListener<Realm> listener) {
        listeners.add(listener);
    }
}
