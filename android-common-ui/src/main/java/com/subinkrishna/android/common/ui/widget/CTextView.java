package com.subinkrishna.android.common.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.subinkrishna.android.common.ui.R;
import com.subinkrishna.android.common.ui.enumeration.Font;

/**
 * @author Subinkrishna Gopi
 */
public class CTextView extends TextView {

    public CTextView(Context context) {
        super(context);
        configure(context, null);
    }

    public CTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        configure(context, attrs);
    }

    public CTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void configure(final Context context, final AttributeSet attrs) {
        // To avoid exceptions during editing in IDE
        if (isInEditMode()) return;

        TypedArray t = null;
        if (null != attrs) {
            t = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.CTextView, 0, 0);
        }

        // Apply font styles
        if (null != t) {
            final int fontId = t.getInteger(R.styleable.CTextView_fontFamily, 0);
            Typeface typeface = Font.getTypeface(context, fontId);
            // Set custom typeface
            if (null != typeface) {
                setTypeface(typeface);
            }
            t.recycle();
        }
    }
}
