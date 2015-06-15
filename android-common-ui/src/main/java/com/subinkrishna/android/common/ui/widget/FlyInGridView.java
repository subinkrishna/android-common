package com.subinkrishna.android.common.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.GridView;

/**
 * @author Subinkrishna Gopi
 */
public class FlyInGridView
        extends GridView
        implements ViewTreeObserver.OnPreDrawListener {

    private Interpolator mInterpolator = null;
    private static int DURATION = 600;
    private static int BASE_START_DELAY = 200;

    public FlyInGridView(Context context) {
        super(context);
        configure(context, null);
    }

    public FlyInGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        configure(context, attrs);
    }

    public FlyInGridView(Context context, AttributeSet attrs, int defStyle) {
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

        setEnabled(false);
        mInterpolator = (null == mInterpolator) ? new DecelerateInterpolator() : mInterpolator;
        final int numOfColumns = getNumColumns();

        final int parentHeight = getHeight();
        for (int i = 0; i < childCount; i++) {
            final View v = getChildAt(i);
            // Set the initial position
            v.setTranslationY(parentHeight);

            // TODO: May need to cancel pending animations onDetachedFromWindow() ?
            // Set an animator
            final ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", parentHeight, 0);
            animator.setDuration(DURATION);

            // Set numOfColumns to "1" to animate all the cells one after other
            //animator.setStartDelay((long)((i / numOfColumns) * .5 * BASE_START_DELAY));
            animator.setStartDelay((long)(i * .5 * BASE_START_DELAY));
            animator.start();

            // Enable the list after finishing the first animation
            if (i == childCount - 1) {
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        setEnabled(true);
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
