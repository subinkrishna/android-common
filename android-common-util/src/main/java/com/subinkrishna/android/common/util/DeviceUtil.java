package com.subinkrishna.android.common.util;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Holds device/Config related methods.
 *
 * @author Subinkrishna Gopi
 */
public class DeviceUtil {

    /**
     * Checks if the device is in landscape mode.
     *
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    /**
     * Checks if the device is tablet or not.
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    /**
     * Checks if network available.
     *
     * @param context
     * @return
     */
    public static boolean isOnline(Context context) {
        final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return (null != activeNetwork) && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Returns the active network info.
     *
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return (null != activeNetworkInfo) ? activeNetworkInfo.getTypeName() : null;
    }
}
