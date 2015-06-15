package com.subinkrishna.android.common.ui.enumeration;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;

/**
 * Font enumeration for CurahTextView.
 *
 * @author Subinkrishna Gopi
 */
public enum Font {

    Regular         (0, ""),
    Light           (1, "Roboto-Light.ttf"),
    Condensed       (2, "RobotoCondensed-Regular.ttf"),
    CondensedItalic (3, "RobotoCondensed-Italic.ttf");

    /** Log Tag */
    private static final String TAG = Font.class.getSimpleName();

    public int mId;
    public String mFontFilename;
    private Typeface mTypeface;

    private Font(final int id, final String filename) {
        mId = id;
        mFontFilename = filename;
        mTypeface = null;
    }

    /**
     * Return a singleton instance of the custom font.
     *
     * @param context
     * @return
     */
    public Typeface getTypeface(final Context context) {
        // Abort if context is null
        // Abort if there is no filename mentioned for the specified type
        if ((null == context) || TextUtils.isEmpty(mFontFilename))
            return null;

        // Create a new typeface instance if there is no instance exists
        if (null == mTypeface) {
            mTypeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), mFontFilename);
        }

        return mTypeface;
    }

    /**
     * Get Typeface for the specified ID.
     *
     * @param context
     * @param id
     * @return
     */
    public static Typeface getTypeface(final Context context,
                                       final int id) {
        // Abort if context is invalid
        if (null == context)
            return null;

        // Find the enum with the specified ID
        final Font[] fonts = values();
        Typeface typeface = null;

        //  Abort if there are no values
        if ((null == fonts) || (0 == fonts.length)) {
            return null;
        }

        for (Font aFont : fonts) {
            if (aFont.mId == id) {
                typeface = aFont.getTypeface(context);
                break;
            }
        }

        return typeface;
    }
}

