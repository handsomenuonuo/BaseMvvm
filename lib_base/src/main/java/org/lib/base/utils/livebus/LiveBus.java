package org.lib.base.utils.livebus;

import androidx.annotation.NonNull;

import org.lib.base.utils.livebus.BusObservable;
import org.lib.base.utils.livebus.LiveBusCore;

/**
 * @author: HuangFeng
 * @time: 2020/6/5 10:57 AM
 * @description: --
 * @since: 1.0.0
 */
public final class LiveBus {

    public static <T> BusObservable<T> get(@NonNull Object key, Class<T> type) {
        return LiveBusCore.getInstance().with(key, type);
    }

    public static <T> BusObservable<T> get(@NonNull Class<T> eventType) {
        return LiveBusCore.getInstance().with(eventType.getName(), eventType);
    }

    public static BusObservable<Object> get(@NonNull Object key) {
        return LiveBusCore.getInstance().with(key, Object.class);
    }

    public static void removeEvent(@NonNull Object key) {
        LiveBusCore.getInstance().removeEvent(key);
    }
}
