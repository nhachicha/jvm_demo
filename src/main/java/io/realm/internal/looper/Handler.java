package io.realm.internal.looper;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Nabil on 30/10/2017.
 */
public class Handler {
    private final BlockingQueue<Runnable> queue;

    Handler(Looper looper) {
        if (looper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread that has not called Looper.prepare()");
        }
        this.queue = looper.mQueue;
    }

    public boolean post(Runnable runnable) {
        return this.queue.add(runnable);
    }
}
