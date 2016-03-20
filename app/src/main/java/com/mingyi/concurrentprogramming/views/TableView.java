package com.mingyi.concurrentprogramming.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 桌子View
 * Created by liumingyi on 2/21/16.
 */
public class TableView extends View {

	private static final String TAG = "TableView";
	private int width;
	private int height;
	private int radius;
	private int chopstickNum = 5;
	private Paint tablePaint;
	private Paint strokePaint;
	private int strokeWidth = 10;
	private int intersectionAngle;
	private int startAngle;
	private Paint backgroundPaint;

	public TableView(Context context) {
		super(context);
	}

	public TableView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TableView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (width == 0 || height == 0) {
			height = width = MeasureSpec.getSize(widthMeasureSpec);
			radius = width / 2 - strokeWidth;
			this.setMeasuredDimension(width, height);
			Log.d(TAG, "onMeasure - " + width + " ---- " + height);
			init();
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private void init() {
		tablePaint = new Paint();
		tablePaint.setColor(Color.RED);
		tablePaint.setStyle(Paint.Style.STROKE);
		tablePaint.setStrokeWidth(strokeWidth);

		strokePaint = new Paint();
		strokePaint.setColor(Color.RED);
		strokePaint.setStyle(Paint.Style.STROKE);
		strokePaint.setStrokeWidth(strokeWidth);

		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.BLACK);

		intersectionAngle = 360 / chopstickNum;
		startAngle = -90 - intersectionAngle / 2;
	}

	@Override protected void onDraw(Canvas canvas) {
		canvas.drawCircle(width / 2, height / 2, radius, tablePaint);
		for (int i = 0; i < chopstickNum; i++) {
			canvas.drawLine(width / 2, height / 2, calculateX(startAngle + i * intersectionAngle),
				calculateY(startAngle + i * intersectionAngle), strokePaint);
		}
		canvas.drawCircle(width / 2, height / 2, radius / 3, backgroundPaint);
	}

	private int calculateX(double angle) {
		//(a+R*cosα,b+R*sinα)
		return (int) (width / 2 + radius * Math.cos(Math.toRadians(angle)));
	}

	private int calculateY(double angle) {
		return (int) (height / 2 + radius * Math.sin(Math.toRadians(angle)));
	}
}
