package io.realm;

import io.realm.internal.looper.Handler;
import io.realm.internal.looper.HandlerThread;
import io.realm.internal.looper.Looper;

/**
 * Created by Nabil on 30/10/2017.
 */
public abstract class NotificationThread {
    private Handler handler;
    private HandlerThread handlerThread;
    private boolean automaticJoin = false;

    public static NotificationThread run(final Task runnable) {
        final NotificationThread[] appContext = new NotificationThread[1];
        appContext[0] = new NotificationThread() {
            @Override
            public void run() {
                runnable.run(this);
            }
        };
        return appContext[0];
    }

    public static NotificationThread run(final Runnable runnable) {
        NotificationThread appContext = new NotificationThread(false) {
            @Override
            public void run() {
                runnable.run();
            }
        };
        return appContext;
    }

    public NotificationThread() {
        automaticJoin = true;
        start();
    }

    public NotificationThread(boolean automaticJoin) {
        this.automaticJoin = automaticJoin;
        start();
    }

    public void start() {
        // automatically start the handler
        handlerThread = new HandlerThread("background");
        handlerThread.start();
        handler = handlerThread.getThreadHandler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                NotificationThread.this.run();
            }
        });
        if (automaticJoin) {
            try {
                handlerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                stop();
            }
        }
    }

    public void join() {
        try {
            handlerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            stop();
        }
    }
    public void stop() {
        // quit Looper
        handler.post(new Looper.QuitMessage() {
            @Override
            public void run() {
            }
        });
    }

    public abstract void run();
}
