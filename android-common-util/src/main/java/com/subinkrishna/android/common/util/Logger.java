package com.subinkrishna.android.common.util;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author Subinkrishna Gopi
 */
public class Logger {

    /** Log Tag for all logs (max length 23 chars) */
    private static String TAG = "APP";

    private static boolean sInitialized = false;

    private Logger() {
    }

    /**
     * Init Logger.
     *
     * @param tag
     */
    public static void init(String tag) {
        sInitialized = true;
        TAG = tag;
    }

    /**
     * Prints an DEBUG log.
     *
     * @param tag
     * @param message
     */
    public static void d(final String tag,
                         final String message) {
        if (sInitialized &&
            !TextUtils.isEmpty(tag) &&
            !TextUtils.isEmpty(message)) {
            Log.d(TAG, String.format("[%s] %s", tag, message));
        }
    }

    /**
     * Prints a bundle along with the given message.
     *
     * @param tag
     * @param message
     * @param bundle
     */
    public static void d(final String tag,
                         final String message,
                         final Bundle bundle) {
        if (sInitialized) {
            Log.d(TAG, String.format("[%s] %s", tag, message));
            if (null != bundle) {
                for (String key : bundle.keySet()) {
                    Log.d(TAG, String.format("[%s] - %s : %s", tag, key, String.valueOf(bundle.get(key))));
                }
            }
        }
    }

    /**
     * Prints an ERROR log.
     *
     * @param tag
     * @param message
     */
    public static void e(final String tag,
                         final String message) {
        e(tag, message, null);
    }

    /**
     * Prints an ERROR log.
     *
     * @param tag
     * @param message
     */
    public static void e(final String tag,
                         final String message,
                         final Throwable throwable) {
        if (sInitialized &&
            !TextUtils.isEmpty(tag) &&
            !TextUtils.isEmpty(message)) {
            Log.e(TAG, String.format("[%s] ERROR: %s", tag, message));
            // Print exception stack trace if available
            if (null != throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
