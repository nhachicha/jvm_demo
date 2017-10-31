package io.realm.internal.looper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Nabil on 30/10/2017.
 */
public class Looper {
    public final BlockingQueue<Runnable> mQueue;
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();

    public Looper(BlockingQueue queue) {
        this.mQueue  = queue;
    }

    public static void prepare() {// should be called once
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(new LinkedBlockingQueue<>()));
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        BlockingQueue<Runnable> queue = me.mQueue;
        for (;;) {
            Runnable runnable = null;
            try {
                runnable = queue.take();
                if (runnable instanceof QuitMessage) {
                    // No message indicates that the message queue is quitting.
                    return;
                }
                runnable.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public interface QuitMessage extends Runnable {

    }
}
