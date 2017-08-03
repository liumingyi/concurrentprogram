package com.mingyi.concurrentprogramming;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.mingyi.concurrentprogramming.utils.Constants;

/**
 * 自定义的Application
 * Created by liumingyi on 2/21/16.
 */
public class ConcurrentApplication extends Application {

  private static final String TAG = "ConcurrentApplication";

  @Override public void onCreate() {
    super.onCreate();

    WindowManager wm =
        (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    Constants.screenWidth = size.x;
    Constants.screenHeight = size.y;

    Log.d(TAG, "" + Constants.screenWidth + " , " + Constants.screenHeight);
  }
}
