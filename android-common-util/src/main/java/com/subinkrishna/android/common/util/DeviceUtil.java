package com.subinkrishna.android.common.util;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Holds device/Config related methods.
 *
 * @author Subinkrishna Gopi
 */
public class DeviceUtil {

    private static String sDeviceUserAgent;

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

    /**
     * Returns the default User-Agent associated with the device.
     *
     * @param context
     * @return
     */
    @SuppressWarnings("NewApi")
    public static String getUserAgent(Context context) {
        if (null == context) return "";
        if (TextUtils.isEmpty(sDeviceUserAgent)) {
            sDeviceUserAgent = (Build.VERSION.SDK_INT >= 17)
                    ? WebSettings.getDefaultUserAgent(context)
                    : new WebView(context).getSettings().getUserAgentString();
        }
        return sDeviceUserAgent;
    }
}
