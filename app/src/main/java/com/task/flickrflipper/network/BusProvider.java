package com.task.flickrflipper.network;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by rafi on 03/06/15.
 */

/**
 * Maintains a singleton instance for obtaining the bus. Ideally this would be
 * replaced with a more efficient means such as through injection directly into
 * interested classes.
 */
public final class BusProvider {
    private static final Bus BUS = new MainThreadBus(new Bus());

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }

    static class MainThreadBus extends Bus {
        private final Bus mBus;
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        public MainThreadBus(final Bus bus) {
            if (bus == null) {
                throw new NullPointerException("bus must not be null");
            }
            mBus = bus;
        }

        @Override
        public void register(Object obj) {
            mBus.register(obj);
        }

        @Override
        public void unregister(Object obj) {
            mBus.unregister(obj);
        }

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                mBus.post(event);
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBus.post(event);
                    }
                });
            }
        }
    }
}
