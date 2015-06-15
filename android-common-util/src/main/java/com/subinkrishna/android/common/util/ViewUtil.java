package com.subinkrishna.android.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import static com.subinkrishna.android.common.util.Logger.d;

/**
 * View Utility.
 *
 * @author Subinkrishna Gopi
 */
public class ViewUtil {

    /** Log tag */
    public static final String TAG = ImageUtil.class.getSimpleName();

    private ViewUtil() {

    }

    /**
     * NOTE:
     * To avoid creating an array, if there is only one input parameter.
     *
     * @param aView
     */
    public static void show(View aView) {
        if (null != aView) {
            aView.setVisibility(View.VISIBLE);
        }
    }

    public static void show(View...views) {
        for (View aView : views) {
            if (null != aView) {
                aView.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void hide(View aView) {
        if (null != aView) {
            aView.setVisibility(View.GONE);
        }
    }

    public static void hide(View...views) {
        for (View aView : views) {
            if (null != aView) {
                aView.setVisibility(View.GONE);
            }
        }
    }

    public static void enable(final boolean status,
                              final View aView) {
        if (null != aView) {
            aView.setEnabled(status);
        }
    }

    public static void enable(final boolean status,
                              final View... views) {
        for (View aView : views) {
            if (null != aView) {
                aView.setEnabled(status);
            }
        }
    }

    public static void hideKeyboard(Activity activity) {
        View focusedView = (null != activity) ? activity.getCurrentFocus() : null;
        if (null != focusedView) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    /**
     * Sets a Bitmap as background to a view.
     *
     * @param context
     * @param view
     * @param bitmap
     */
    public static void setBitmapBackground(final Context context,
                                           final View view,
                                           final Bitmap bitmap) {
        if ((null != context) &&
            (null != view) &&
            (null != bitmap)) {
            if (Build.VERSION.SDK_INT >= 16) {
                setBitmapBackgroundV16Plus(context, view, bitmap);
            } else {
                setBitmapBackgroundV16Minus(context, view, bitmap);
            }
        } else {
            d(TAG, "Invalid inputs. Unable to set the bitmap background");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static void setBitmapBackgroundV16Plus(final Context context,
                                                   final View view,
                                                   final Bitmap bitmap) {
        view.setBackground(new BitmapDrawable(context.getResources(), bitmap));
    }

    @SuppressWarnings("deprecation")
    private static void setBitmapBackgroundV16Minus(final Context context,
                                                    final View view,
                                                    final Bitmap bitmap) {
        view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bitmap));
    }

}
