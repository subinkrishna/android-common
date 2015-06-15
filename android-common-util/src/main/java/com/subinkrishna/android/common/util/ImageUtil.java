package com.subinkrishna.android.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Image utility.
 *
 * @author Subinkrishna Gopi
 */
public class ImageUtil {

    /** Log tag */
    private final static String TAG = ImageUtil.class.getSimpleName();

    private ImageUtil() {

    }

    /**
     * Blurs the given bitmap with a radius of "4"
     *
     * @param context
     * @param bitmap
     * @return
     */
    public static Bitmap blur(final Context context,
                              final Bitmap bitmap) {
        // Abort if the input is invalid
        if ((null == context) ||
            (null == bitmap)) {
            Logger.d(TAG, "Invalid context/bitmap. Unable to blur.");
            return bitmap;
        }
        // Use RenderScript to blur the bitmap if the SDK version is greater than
        // or equal to 17 (JELLY_BEAN_MR1), else use the custom script from
        // Android open source.
        Bitmap blurredBitmap = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                ? blurUsingRenderScript(context, bitmap)
                : GaussianBlurUtil.blur(bitmap);
        return blurredBitmap;
    }

    /**
     * Blurs the given Bitmap using RenderScript.
     *
     * @param context
     * @param bitmap
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static Bitmap blurUsingRenderScript(final Context context,
                                                final Bitmap bitmap) {
        // Create another bitmap that will hold the results of the filter.
        Bitmap blurredBitmap = bitmap.copy(bitmap.getConfig(), true);

        if (blurredBitmap != null) {
            // Create the Renderscript instance that will do the work.
            RenderScript rs = RenderScript.create(context);

            // Allocate memory for Renderscript to work with
            Allocation input = Allocation.createFromBitmap(rs,
                    blurredBitmap,
                    Allocation.MipmapControl.MIPMAP_FULL,
                    Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs,
                    input.getType());

            // Load up an instance of the specific script that we want to use.
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setInput(input);
            script.setRadius(4);              // Set the blur radius
            script.forEach(output);           // Start the ScriptIntrinisicBlur
            output.copyTo(blurredBitmap);     // Copy the output to the blurred bitmap
        }

        return blurredBitmap;
    }

}
