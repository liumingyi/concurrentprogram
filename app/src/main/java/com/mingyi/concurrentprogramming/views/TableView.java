package com.mingyi.concurrentprogramming.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 桌子View
 * Created by liumingyi on 2/21/16.
 */
public class TableView extends View {

  private static final String TAG = "TableView";

  private Paint tablePaint;
  private Paint strokePaint;
  private Paint backgroundPaint;

  private int width;
  private int height;
  private int radius;
  private int strokeWidth = 10;

  private int chopstickNum = 5;
  private int startAngle;
  private int intersectionAngle;

  public TableView(Context context) {
    super(context);
    initPaints();
  }

  public TableView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initPaints();
  }

  public TableView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initPaints();
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (width == 0 || height == 0) {
      height = width = MeasureSpec.getSize(widthMeasureSpec);
      radius = width / 2 - strokeWidth;
      intersectionAngle = 360 / chopstickNum;
      startAngle = -90 - intersectionAngle / 2;
    }
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  private void initPaints() {
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
