package example1;

import io.realm.NotificationThread;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by Nabil on 30/10/2017.
 */
public class MyService extends NotificationThread {
    @Override
    public void run() {
        // Inside a managed Thread
        System.out.println("Service in thread " + Thread.currentThread().getName());

        Realm realm = new Realm();
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                System.out.println(">>>>>> listener 1 triggered");
            }
        });
        realm.commit();
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                System.out.println(">>>>>> listener 2 triggered");
                stop();
            }
        });

        realm.commit();
    }
}
