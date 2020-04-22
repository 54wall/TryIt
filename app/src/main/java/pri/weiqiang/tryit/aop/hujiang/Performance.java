package pri.weiqiang.tryit.aop.hujiang;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 推荐原生调用AOP因为那里需要计算时间，直接在哪里引入@BehaviorTrace(value = " ", type = 1)即可
 * 而这个需要去写不同类名
 */
@Aspect
public class Performance {
    private String TAG = Performance.class.getCanonicalName();

    //    @Around("call(* pri.weiqiang.tryit.aop.hujiang.PerformanceActivity).**(..)")
    @Around("call(* pri.weiqiang.tryit.aop.hujiang.PerformanceActivity.**(..))")
    public void getTime(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.e(TAG, signature.getName() + " cost " + (System.currentTimeMillis() - time));
    }

    //    @Around("call(* pri.weiqiang.tryit.seekbar.SeekRecycleviewActivity.**(..))")
    public void getTime2(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.e(TAG, signature.getName() + " cost " + (System.currentTimeMillis() - time));
    }
}
