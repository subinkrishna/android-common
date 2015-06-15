package com.subinkrishna.android.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static com.subinkrishna.android.common.util.Logger.d;

/**
 * @author Subinkrishna Gopi
 */
public class ObjectUtil {

    /** Log tag */
    public static final String TAG = ObjectUtil.class.getSimpleName();

    private ObjectUtil() {

    }

    /**
     * Tries to map anObject to the specified type. Creates a proxy instance
     * if anObject is NOT an instance of type and forceCreate is set to true.
     *
     * @param anObject
     * @param type
     * @param forceCreate
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T mapTo(final Object anObject,
                              final Class<T> type,
                              final boolean forceCreate) {
        if ((null == anObject) || (null == type)) {
            throw new IllegalArgumentException("Invalid parameters!");
        }

        T proxy = null;

        if (type.isInstance(anObject)) {
            proxy = (T) anObject;
        } else if (forceCreate) {
            try {
                // Create a proxy instance
                proxy = (T) Proxy.newProxyInstance(type.getClassLoader(),
                        new Class[]{type},
                        // Invocation Handler
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy,
                                                 Method method,
                                                 Object[] args) throws Throwable {
                                d(TAG, "ProxyObject." + method.getName() + "()");
                                return null;
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
                proxy = null;
            }
        }

        return proxy;
    }
}
