package com.subinkrishna.android.common.util;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.subinkrishna.android.common.util.Logger.d;
import static com.subinkrishna.android.common.util.Logger.e;

/**
 * Network related utility methods
 *
 * @author Subinkrishna Gopi
 */
public class NetworkUtil {

    /** 1 Kilo Byte */
    public static final int ONE_KILO_BYTE = 1024;

    private NetworkUtil() {

    }

    /** Log Tag */
    private static final String TAG = NetworkUtil.class.getSimpleName();

    /**
     * Fetches the URL contents as byte stream.
     *
     * @param url
     * @return
     */
    public static byte[] fetch(final String url) {
        // Exit if URL is invalid
        if (TextUtils.isEmpty(url)) {
            e(TAG, "Aborting URL fetch. Invalid URL");
            return null;
        }

        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] bytes = null;

        try {
            d(TAG, "Read from URL: " + url);

            // Create an HttpUrlConnection
            final URL aUrl = new URL(url);
            final HttpURLConnection connection = (HttpURLConnection) aUrl.openConnection();
            inputStream = connection.getInputStream();
            byteArrayOutputStream = new ByteArrayOutputStream();

            // Copy URL contents to byte stream
            NetworkUtil.copyStream(inputStream, byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();

        } catch (Exception e) {
            e(TAG, "Exception while connecting to URL: " + url);
            bytes = null;
        }

        // Do the cleanup
        finally {
            // Close streams
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
                inputStream = null;
            }

            byteArrayOutputStream = null;
        }

        return bytes;
    }

    /**
     * Copy stream contents from the specified InputStream to OutputStream.
     *
     * @param from
     * @param to
     */
    public static void copyStream(final InputStream from,
                                  final OutputStream to) throws IOException {
        byte[] buffer = null;
        int count = -1;

        // Copy contents if source and destinations are not-null
        if ((null != from) && (null != to)) {
            try {
                buffer = new byte[ONE_KILO_BYTE];
                while (-1 != (count = from.read(buffer))) {
                    to.write(buffer, 0, count);
                }
            } finally {
                buffer = null;
            }
        }
    }

}
