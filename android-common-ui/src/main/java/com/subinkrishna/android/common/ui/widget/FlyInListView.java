package com.subinkrishna.android.common.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ListView;

/**
 * ListView implementation with children flying-in on the initial draw.
 *
 * @author Subinkrishna Gopi
 */
public class FlyInListView
        extends ListView
        implements ViewTreeObserver.OnPreDrawListener {

    private Interpolator mInterpolator = null;
    private static int DURATION = 600;
    private static int BASE_START_DELAY = 200;

    public FlyInListView(Context context) {
        super(context);
        configure(context, null);
    }

    public FlyInListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        configure(context, attrs);
    }

    public FlyInListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        configure(context, attrs);
    }

    private void configure(final Context context, final AttributeSet attrs) {
        // Attach pre-draw listener
        getViewTreeObserver().addOnPreDrawListener(this);
    }

    /**
     * Enables view animations.
     */
    public void enableAnimations(final boolean enable) {
        if (enable) {
            getViewTreeObserver().addOnPreDrawListener(this);
        } else {
            getViewTreeObserver().removeOnPreDrawListener(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    synchronized
    public boolean onPreDraw() {
        // Remove the listener
        getViewTreeObserver().removeOnPreDrawListener(this);

        // Don't animate if the size list has no children
        final int childCount = getChildCount();
        if (childCount <= 0) {
            return true;
        }

        // Don't animate if the list is scrolled
        if ((getFirstVisiblePosition() > 0) ||
            (getChildAt(0).getTop() < 0)) {
            return true;
        }

        // Disable the vertical scrollbars until animation is over
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        // TODO: This may cause issues when BatterySavingMode is turned on.
        // Need to check if saving mode is on in android.os.PowerManager
        // setEnabled(false);

        mInterpolator = (null == mInterpolator) ? new DecelerateInterpolator() : mInterpolator;

        final int parentHeight = getHeight();
        for (int i = 0; i < childCount; i++) {
            final View v = getChildAt(i);
            // Set the initial position
            v.setTranslationY(parentHeight);

            // TODO: May need to cancel pending animations onDetachedFromWindow() ?
            // Set an animator
            final ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", parentHeight, 0);
            animator.setDuration(DURATION);
            animator.setStartDelay((long)(i * .5 * BASE_START_DELAY));
            animator.start();

            // Enable the list after finishing the last animation
            if (i == childCount - 1) {
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        setEnabled(true);
                        setVerticalScrollBarEnabled(true);
                        setHorizontalScrollBarEnabled(true);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
            }
        }

        // Send "false" to avoid current draw pass and restart it based on the
        // new translateY values of the children
        return false;
    }
}
