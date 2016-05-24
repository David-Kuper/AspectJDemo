
package org.android10.viewgroupperformance.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import org.android10.gintonic.annotation.DebugTrace;

/**
 *
 */
public class MyFrameLayout extends FrameLayout {
  public MyFrameLayout(Context context) {
    super(context);
  }

  public MyFrameLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MyFrameLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @DebugTrace
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @DebugTrace
  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }
}
