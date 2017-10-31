package example2;

import io.realm.NotificationThread;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.Task;

/**
 * Created by Nabil on 30/10/2017.
 */
public class Main {
    public static void main(String args[]) throws InterruptedException {
        NotificationThread.run(new Task() {
            @Override
            public void run(NotificationThread notificationThread) {
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
                        notificationThread.stop();
                    }
                });

                realm.commit();
            }
        });
    }
}
