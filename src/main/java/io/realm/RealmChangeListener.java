package io.realm;

/**
 * Created by Nabil on 30/10/2017.
 */
public abstract class RealmChangeListener<T> {
    public abstract void onChange(T t);
}
