package pri.weiqiang.tryit.aop.aspectj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Xionghu on 2018/1/23.
 * Desc:切点
 */

//自定义注解：使用@interface自定义注解时，自动继承了java.lang.annotation.Annotation接口，由编译程序自动完成其他细节。在定义注解时，
// 不能继承其他的注解或接口。@interface用来声明一个注解，其中的每一个方法实际上是声明了一个配置参数。
// 方法的名称就是参数的名称，返回值类型就是参数的类型（返回值类型只能是基本类型、Class、String、enum）。可以通过default来声明参数的默认值。

//ElementType.METHOD 用于描述方法
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface BehaviorTrace {
    String value();

    int type();
}
