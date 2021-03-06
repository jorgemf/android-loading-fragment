package com.livae.android.loading;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

/**
 * Simple circular loading drawable with progression.
 */
public class CircularLoadingDrawable extends Drawable {

	private static final int MAXIMUM_LEVEL = 10000;
	private static final int LINE_WIDTH_DP = 4;
	private float mProgress = 0;
	private Paint mPaint;

	private RectF mArcBounds;

	private float mStrokeWidthPx;

	private int mColor;
	private int mColorActive;

	public CircularLoadingDrawable(Context context, int color, int colorActive) {
		mPaint = new Paint();
		Resources resources = context.getResources();
		mStrokeWidthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LINE_WIDTH_DP,
				resources.getDisplayMetrics());
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(mStrokeWidthPx);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mColor = color;
		mColorActive = colorActive;
		mPaint.setColor(mColor);
		mArcBounds = new RectF();
	}

	@Override
	public void draw(Canvas canvas) {
		Rect bounds = getBounds();
		int radius = (Math.min(bounds.width(), bounds.height()) - (int) mStrokeWidthPx) / 2;
		mArcBounds.set(bounds.centerX() - radius,
				bounds.centerY() - radius,
				bounds.centerX() + radius,
				bounds.centerY() + radius);
		/* (90 + 540 * mProgress) % 360 */
		canvas.drawArc(mArcBounds, 270, 360 * mProgress, false, mPaint);
	}

	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return android.graphics.PixelFormat.OPAQUE;
	}

	@Override
	protected boolean onLevelChange(int level) {
		if (level >= 0) {
			mProgress = (float) level / MAXIMUM_LEVEL;
			if (level == MAXIMUM_LEVEL) {
				mPaint.setColor(mColorActive);
			} else {
				mPaint.setColor(mColor);
			}
			invalidateSelf();
			return true;
		}
		return false;
	}
}
