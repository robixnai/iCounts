package com.robson.icounts.libraries;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * Created by robson on 22/03/16.
 */
public class AnimateHorizontalProgressBar  extends ProgressBar {

    private static final String TAG = AnimateHorizontalProgressBar.class.getName();
    private static final long DEFAULT_DURATION = 1200;

    private ValueAnimator mProgressAnimator;
    private ValueAnimator mMaxAnimator;
    private boolean isAnimating = false;

    private AnimateProgressListener mAnimateProgressListener;

    public AnimateHorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimateHorizontalProgressBar(Context context) {
        this(context, null, 0);
    }

    public AnimateHorizontalProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, 0);
        setUpAnimator();
    }

    private void setUpAnimator() {
        mProgressAnimator = new ValueAnimator();
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                AnimateHorizontalProgressBar.super.setProgress((Integer) animation.getAnimatedValue());
            }
        });
        mProgressAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
                if (mAnimateProgressListener != null) {
                    mAnimateProgressListener.onAnimationStart(getProgress(), getMax());
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                if (mAnimateProgressListener != null) {
                    mAnimateProgressListener.onAnimationEnd(getProgress(), getMax());
                }
            }
        });
        mProgressAnimator.setDuration(DEFAULT_DURATION);


        mMaxAnimator = new ValueAnimator();
        mMaxAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                AnimateHorizontalProgressBar.super.setMax((Integer) animation.getAnimatedValue());
            }
        });
        mMaxAnimator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
                if (mAnimateProgressListener != null) {
                    mAnimateProgressListener.onAnimationStart(getProgress(), getMax());
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                if (mAnimateProgressListener != null) {
                    mAnimateProgressListener.onAnimationEnd(getProgress(), getMax());
                }
            }
        });
        mMaxAnimator.setDuration(DEFAULT_DURATION);
    }

    /**
     * Animation Progress
     *
     * @param progress animationEnd progress point
     */
    public void setProgressWithAnim(int progress) {
        if (isAnimating) {
            Log.w(TAG, "now is animating. cant override animator");
            return;
        }
        if (mProgressAnimator == null) {
            setUpAnimator();
        }
        mProgressAnimator.setIntValues(getProgress(), progress);
        mProgressAnimator.start();
    }

    @Override
    public void setProgressDrawable(Drawable d) {
        super.setProgressDrawable(d);
    }

    @Override
    public synchronized void setProgress(int progress) {
        if (!isAnimating) {
            super.setProgress(progress);
        }
    }

    @Deprecated
    @Override
    public synchronized void setSecondaryProgress(int secondaryProgress) {
        // do Nothing
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mProgressAnimator != null) {
            mProgressAnimator.cancel();
        }
        if (mMaxAnimator != null) {
            mMaxAnimator.cancel();
        }
    }

    /**
     * Animation Progress
     *
     * @param max animationEnd max point
     */
    public void setMaxWithAnim(int max) {
        if (isAnimating) {
            Log.w(TAG, "now is animating. cant override animator");
            return;
        }
        if (mMaxAnimator == null) {
            setUpAnimator();
        }
        mMaxAnimator.setIntValues(getMax(), max);
        mMaxAnimator.start();
    }

    @Override
    public synchronized void setMax(int max) {
        if (!isAnimating) {
            super.setMax(max);
        }
    }

    public long getAnimDuration() {
        return mProgressAnimator.getDuration();
    }

    public void setAnimDuration(long duration) {
        mProgressAnimator.setDuration(duration);
        mMaxAnimator.setDuration(duration);
    }

    public void setStartDelay(long delay) {
        mProgressAnimator.setStartDelay(delay);
        mMaxAnimator.setStartDelay(delay);
    }

    public void setAnimInterpolator(@NonNull TimeInterpolator timeInterpolator) {
        mProgressAnimator.setInterpolator(timeInterpolator);
        mMaxAnimator.setInterpolator(timeInterpolator);
    }

    public void setAnimateProgressListener(AnimateProgressListener animateProgressListener) {
        this.mAnimateProgressListener = animateProgressListener;
    }

    public interface AnimateProgressListener {
        void onAnimationStart(int progress, int max);
        void onAnimationEnd(int progress, int max);
    }

    private class SimpleAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {}

        @Override
        public void onAnimationEnd(Animator animation) {}

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}

    }

}
