package com.mingyi.concurrentprogramming;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.meican.android.inject.SmartKnife;

/**
 * 自定义activity基类
 * Created by liumingyi on 2/14/16.
 */
public class BaseActivity extends Activity {

  public static void initInjectedView(Activity activity) {
    SmartKnife.bind(activity);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    initInjectedView(this);
  }

  @Override public void setContentView(View view) {
    super.setContentView(view);
    initInjectedView(this);
  }

  @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
    super.setContentView(view, params);
    initInjectedView(this);
  }
}
