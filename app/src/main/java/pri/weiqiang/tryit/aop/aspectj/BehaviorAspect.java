package pri.weiqiang.tryit.aop.aspectj;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Xionghu on 2018/1/23.
 * Desc: 切面
 * 你想要切下来的部分（代码逻辑功能重复模块）
 */
@Aspect
public class BehaviorAspect {
    private static final String TAG = BehaviorAspect.class.getSimpleName();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 根据切点 切成什么样子
     */
    //@Pointcut("execution(@pri.weiqiang.tryit.aop.aspectj.BehaviorTrace**(..))")//错误 必须有空格
    @Pointcut("execution(@pri.weiqiang.tryit.aop.aspectj.BehaviorTrace * *(..))")
    public void annoBehavior() {

    }

    /**
     * 切成什么样子之后，怎么去处理
     */

    @Around("annoBehavior()")
    public Object dealPoint(ProceedingJoinPoint point) throws Throwable {
        //方法执行前
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Signature signature = point.getSignature();
        Log.e(TAG, "dealPoint signature.getName() " + signature.getName());
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        //此处method获取的是代理对象（由代理模式生成的）的方法
        Annotation an = method.getAnnotation(BehaviorTrace.class);
//        //此处 an 为 null
//        Log.e(TAG,"an:"+((BehaviorTrace) an).value());
        Method realMethod = point.getTarget().getClass().getDeclaredMethod(signature.getName(),
                method.getParameterTypes());
        //此处realMethod是目标对象（原始的）的方法
        an = method.getAnnotation(BehaviorTrace.class);
        /* 此处 an 不为null
        Could not execute method for android:onClick
        java.lang.String pri.weiqiang.tryit.aop.aspectj.BehaviorTrace.value()' on a null object reference
        at pri.weiqiang.tryit.aop.aspectj.AspectjActivity.mShake_aroundBody1$advice(AspectjActivity.java:56)
        at pri.weiqiang.tryit.aop.aspectj.AspectjActivity.mShake*/
//        Log.e(TAG, "an:" + ((BehaviorTrace) an).value());

        //*同样报上边的错
        // https://blog.csdn.net/frightingforambiti*/
//        BehaviorTrace behaviorTrace = methodSignature.getMethod().getAnnotation(BehaviorTrace.class);
//        String contentType = behaviorTrace.value();
//        int type = behaviorTrace.type();
        Log.i(TAG,/*contentType+*/"66666使用时间：   " + simpleDateFormat.format(new Date()));
        long beagin = System.currentTimeMillis();
        //方法执行时
        Object object = null;
        try {
            object = point.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //方法执行完成
        Log.i(TAG, "消耗时间：" + (System.currentTimeMillis() - beagin) + "ms");
        return object;
    }
}
