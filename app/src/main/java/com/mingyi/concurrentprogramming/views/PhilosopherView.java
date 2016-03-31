package com.mingyi.concurrentprogramming.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.mingyi.concurrentprogramming.R;

/**
 * 表示哲学家的view 3个矩形，两个表示手，一个表示身子
 * Created by liumingyi on 2/14/16.
 */
public class PhilosopherView extends View {

	private static final String TAG = "PhilosopherView";

	private Paint bodyPaint;
	private Paint handPaint;
	private Paint handUsedPaint;

	private RectF body;
	private RectF leftHand;
	private RectF rightHand;

	private int width;
	private int height;
	private int angle;
	private boolean leftHandUsed = false;
	private boolean rightHandUsed = false;

	public PhilosopherView(Context context) {
		super(context);
		initPaints();
	}

	public PhilosopherView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaints();
		getAngleFromAttrs(context, attrs);
	}

	public PhilosopherView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initPaints();
		getAngleFromAttrs(context, attrs);
	}

	private void getAngleFromAttrs(Context context, AttributeSet attrs) {
		if (attrs == null) {
			return;
		}
		//获取TypedArray，attrs是构造方法中提供的参数
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PhilosopherView);
		angle = typedArray.getColor(R.styleable.PhilosopherView_angle, 0);
		typedArray.recycle();
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (width == 0 || height == 0) {
			height = width = MeasureSpec.getSize(widthMeasureSpec);
			int bodySide = width / 2;
			int handSide = bodySide / 5;
			body = new RectF(width / 4, height / 4, width / 4 + bodySide, height / 4 + bodySide);
			leftHand = new RectF(width / 4 - handSide, height / 4, width / 4, bodySide);
			rightHand = new RectF(width / 4 + bodySide, height / 4, width / 4 + bodySide + handSide, bodySide);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private void initPaints() {
		bodyPaint = new Paint();
		bodyPaint.setColor(Color.RED);
		bodyPaint.setStyle(Paint.Style.STROKE);
		bodyPaint.setAntiAlias(true);
		bodyPaint.setStrokeWidth(5);

		handPaint = new Paint();
		handPaint.setColor(Color.RED);
		handPaint.setStyle(Paint.Style.STROKE);
		handPaint.setAntiAlias(true);
		handPaint.setStrokeWidth(5);

		handUsedPaint = new Paint();
		handUsedPaint.setColor(Color.RED);
	}

	@Override protected void onDraw(Canvas canvas) {
		canvas.rotate(angle, width / 2, height / 2);
		canvas.drawRoundRect(body, 100, 100, bodyPaint);
		if (leftHandUsed) {
			canvas.drawRoundRect(leftHand, 20, 20, handUsedPaint);
		} else {
			canvas.drawRoundRect(leftHand, 20, 20, handPaint);
		}
		if (rightHandUsed) {
			canvas.drawRoundRect(rightHand, 20, 20, handUsedPaint);
		} else {
			canvas.drawRoundRect(rightHand, 20, 20, handPaint);
		}
	}

	//////////////////////////public////////////////////////////////

	public void left() {
		leftHandUsed = true;
		invalidate();
	}

	public void right() {
		rightHandUsed = true;
		invalidate();
	}

	public void thinking() {
		leftHandUsed = false;
		rightHandUsed = false;
		invalidate();
	}

	public void eat() {
		leftHandUsed = true;
		rightHandUsed = true;
		invalidate();
	}
}
