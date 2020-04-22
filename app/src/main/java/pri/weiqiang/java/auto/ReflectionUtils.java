package pri.weiqiang.java.auto;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//这个类没什么特别的，网上多的是
public class ReflectionUtils {

    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;

        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {

            }
        }

        return null;
    }

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        method.setAccessible(true);

        try {
            if (null != method) {

                return method.invoke(object, parameters);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;

        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {

            }
        }

        return null;
    }

    public static void setFieldValue(Object object, String fieldName, Object value) {

        Field field = getDeclaredField(object, fieldName);

        field.setAccessible(true);

        try {
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static Object getFieldValue(Object object, String fieldName) {

        Field field = getDeclaredField(object, fieldName);

        field.setAccessible(true);

        try {
            return field.get(object);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}