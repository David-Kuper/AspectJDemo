
package org.android10.gintonic.aspect;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import org.android10.gintonic.internal.ChooseDialog;
import org.android10.gintonic.internal.DebugLog;
import org.android10.gintonic.internal.MethodMsg;
import org.android10.gintonic.internal.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 截获类名最后含有Activity、Layout的类的所有方法
 * 监听目标方法的执行时间
 */
@Aspect
public class TraceAspect {
  private static Object currentObject = null;

  private static final String POINTCUT_METHOD =
      "(execution(* *..Activity+.*(..)) ||execution(* *..Layout+.*(..))) && target(Object) && this(Object)";

  private static final String POINTCUT_METHOD_MAINACTIVITY = "execution(* *..MainActivity+.onCreate(..))";
    //精确截获MyFrameLayou的onMeasure方法
    private static final String POINTCUT_CALL = "call(* org.android10.viewgroupperformance.component.MyFrameLayout.onMeasure(..))";
  @Pointcut(POINTCUT_METHOD)
  public void methodAnnotated() {}
  @Pointcut(POINTCUT_METHOD_MAINACTIVITY)
  public void methodAnootatedWith(){}


    /**
     *  截获原方法，并替换
     * @param joinPoint
     * @return
     * @throws Throwable
     */
  @Around("methodAnnotated()")
  public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {

    if (currentObject == null){
        currentObject = joinPoint.getTarget();
    }
      //初始化计时器
    final StopWatch stopWatch = new StopWatch();
      //开始监听
      stopWatch.start();
      //调用原方法的执行。
    Object result = joinPoint.proceed();
      //监听结束
    stopWatch.stop();
      //获取方法信息对象
      MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
      String className;
      //获取当前对象，通过反射获取类别详细信息
      className = joinPoint.getThis().getClass().getName();

      String methodName = methodSignature.getName();
      String msg =  buildLogMessage(methodName, stopWatch.getTotalTime(1));
    if (currentObject != null && currentObject.equals(joinPoint.getTarget())){
        DebugLog.log(new MethodMsg(className,msg,stopWatch.getTotalTime(1)));
    }else if(currentObject != null && !currentObject.equals(joinPoint.getTarget())){
        DebugLog.log(new MethodMsg(className, msg,stopWatch.getTotalTime(1)));
        Log.e(className,msg);
        currentObject = joinPoint.getTarget();
//        DebugLog.outPut(new Path());    //日志存储
//        DebugLog.ReadIn(new Path());    //日志读取
    }
    return result;
  }

    @After("methodAnootatedWith()")
    public void onCreateAfter(JoinPoint joinPoint) throws Throwable{
        Log.e("onCreateAfter:","onCreate is end .");
    }
    /**
     * 在截获的目标方法调用之前执行该Advise
     * @param joinPoint
     * @throws Throwable
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Before("methodAnootatedWith()")
    public void onCreateBefore(JoinPoint joinPoint) throws Throwable{
        Activity activity = null;
        //获取目标对象
        activity = ((Activity)joinPoint.getTarget());
        //插入自己的实现，控制目标对象的执行
        ChooseDialog dialog = new ChooseDialog(activity);
        dialog.show();

        //做其他的操作
        buildLogMessage("test",20);
    }
  /**
   * 创建一个日志信息
   *
   * @param methodName 方法名
   * @param methodDuration 执行时间
   * @return
   */
  private static String buildLogMessage(String methodName, double methodDuration) {
    StringBuilder message = new StringBuilder();
    message.append(methodName);
    message.append(" --> ");
    message.append("[");
    message.append(methodDuration);
    if (StopWatch.Accuracy == 1){
        message.append("ms");
    }else {
        message.append("mic");
    }
    message.append("]      \n");

    return message.toString();
  }

}
