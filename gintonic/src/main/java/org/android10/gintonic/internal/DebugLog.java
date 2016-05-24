/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package org.android10.gintonic.internal;

import android.graphics.Path;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

/**
 * Wrapper around {@link android.util.Log}
 */
public class DebugLog {
  private static TreeSet<MethodMsg> treeMethods = new TreeSet<MethodMsg>();     //不可重复方法

  private DebugLog() {}

  /**
   * Send a debug log message
   *
   * @param methodMsg The message you would like logged.
   */
  public static void log(MethodMsg methodMsg) {
    if (methodMsg != null){
        treeMethods.add(methodMsg);
    }else {
      throw new NullPointerException("MethodMsg is null!");
    }
  }
  public static void ReadIn(Path path) throws IOException {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      File sdCardDir = Environment.getExternalStorageDirectory();
      File file = new File(sdCardDir, "MyFile/MethodInvokeTimeList.txt");
      if (file != null && file.exists()) {
          FileReader fr = null;
        try {
          fr = new FileReader(file);
          BufferedReader br = new BufferedReader(fr);
          String stri = null;
          StringBuilder strb = new StringBuilder();
          while ((stri = br.readLine()) != null) {
            strb.append(stri+"\n");
          }
          Log.e("TimeMonitor:", strb.toString());
        } finally {
          if (fr != null) {
            fr.close();
          }
        }
      }
    }
  }
  /**
   * 需要有SDCard才能存储
   */
  public static void outPut(Path path){
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
        File sdCardDir = Environment.getExternalStorageDirectory();
        File file = new File(sdCardDir,"MyFile/MethodInvokeTimeList.txt");
        boolean flag = false;
        if (!file.getParentFile().exists()){
            flag = file.getParentFile().mkdirs();
        }else {
            flag = true;
        }
        if (flag){
            writeTotxt(file);
        }else{
           Log.e("outPut","創建文件失敗");
        }
    }else {
        Log.e("outPut","沒有SDCard！");
    }
  }

    public static void reSort(){
        TreeSet<MethodMsg> temp = new TreeSet<MethodMsg>();
        for (MethodMsg method: treeMethods) {
            temp.add(method);
        }
        treeMethods = temp;
    }

private static void writeTotxt(File file){
     PrintWriter pr = null;
    try {
      pr = new PrintWriter(new FileOutputStream(file));
      String str = null;
      for (MethodMsg method : treeMethods) {
        str = method.toString();
        pr.println(str);
      }
        pr.flush();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } finally {
      if (pr != null) {
          pr.close();
      }
    }
  }

}
