package pl.dawidgdanski.tictactoe.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

abstract class AbstractView extends View {

    private static final String TAG = AbstractView.class.getSimpleName();

    protected abstract int getMaximumViewWidth();

    protected abstract int getMinimumViewWidth();

    protected abstract int getMaximumViewHeight();

    protected abstract int getMinimumViewHeight();

    public AbstractView(Context context) {
        super(context);
    }

    public AbstractView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbstractView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        logSpec(MeasureSpec.getMode(widthMeasureSpec));

        final int width = getImprovedDefaultWidth(widthMeasureSpec);
        final int height = getImprovedDefaultHeight(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    private int getImprovedDefaultWidth(final int widthMeasureSpec) {
        final int specMode = MeasureSpec.getMode(widthMeasureSpec);
        final int specSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (specMode) {

            case MeasureSpec.UNSPECIFIED:
                return getMaximumViewWidth();

            case MeasureSpec.EXACTLY:
                return specSize;

            case MeasureSpec.AT_MOST:
                return getMinimumViewWidth();

            default:
                Log.d(TAG, "unknown Spec Mode: " + specMode);
                return specSize;
        }
    }

    private int getImprovedDefaultHeight(final int heightMeasureSpec) {
        final int specMode = MeasureSpec.getMode(heightMeasureSpec);
        final int specSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (specMode) {

            case MeasureSpec.UNSPECIFIED:
                return getMaximumViewHeight();

            case MeasureSpec.EXACTLY:
                return specSize;

            case MeasureSpec.AT_MOST:
                return getMinimumViewHeight();

            default:
                Log.d(TAG, "unknown Spec Mode: " + specMode);
                return specSize;
        }
    }

    private static void logSpec(final int specMode) {
        if(specMode == MeasureSpec.UNSPECIFIED) {
            Log.d(TAG, "mode: unspecified");
            return;
        }

        if(specMode == MeasureSpec.AT_MOST) {
            Log.d(TAG, "mode: at most");
            return;
        }

        if(specMode == MeasureSpec.EXACTLY) {
            Log.d(TAG, "mode: exactly");
        }
    }
}
