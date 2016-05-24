/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package org.android10.gintonic.internal;

import java.util.concurrent.TimeUnit;

/**
 * Class representing a StopWatch for measuring time.
 */
public class StopWatch {
  private long startTime;
  private long endTime;
  private long elapsedTime;
  public static int Accuracy = 1; // 1：毫秒   2：微秒
  public StopWatch() {
    //empty
  }

  private void reset() {
    startTime = 0;
    endTime = 0;
    elapsedTime = 0;
  }

  public void start() {
    reset();
    startTime = System.nanoTime();
  }

  public void stop() {
    if (startTime != 0) {
      endTime = System.nanoTime();
      elapsedTime = endTime - startTime;
    } else {
      reset();
    }
  }
  public long getTotalTime(int type){
    if (type == 1){
      return getTotalTimeMillis();
    }else{
      return getTotalTimeMicros();
    }
  }
  public long getTotalTimeMicros() {
    return (elapsedTime != 0) ? TimeUnit.NANOSECONDS.toMicros(endTime - startTime) : 0;
  }
  public long getTotalTimeMillis(){
    return (elapsedTime != 0) ? TimeUnit.NANOSECONDS.toMillis(endTime - startTime) : 0;
  }
}
