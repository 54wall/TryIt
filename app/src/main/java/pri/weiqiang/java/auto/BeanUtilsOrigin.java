//package pri.weiqiang.java.auto;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.OutputStream;
//import java.io.Writer;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
////import org.apache.commons.lang3.StringUtils;
//
//public class BeanUtilsOrigin {
////    static ReflectionUtils reflectionUtils = new ReflectionUtils();
//
//    public static void copyProperties(Object dist, Object orig) {
//
//        Field[] fields_orig = orig.getClass().getDeclaredFields();
//        Field[] fields_dist = dist.getClass().getDeclaredFields();
//
//        try {
//            int i = 0;
//            for (Field field : fields_orig) {
//                System.out.println(field.getName());
//
//                String typeNameOfOrig = field.getType().getName();
//                String fieldNameOrig = field.getName();
//                Method getmethod = ReflectionUtils.getDeclaredMethod(orig, addGetString(field.getName()), null);
//                System.out.println("typeNameOfOrig:"+typeNameOfOrig+",fieldNameOrig:"+fieldNameOrig);
//                System.out.println("i:"+i+++":getmethod:"+getmethod.getName());
//
//                /*
//                *         if (insert.getHotelID() != null) {
//            traveller.setHotelID(insert.getHotelID());
//        }
//                * */
//                Object obj = getmethod.invoke(orig, null);
//
//                for (Field field2 : fields_dist) {
//                    String typeNameOfdist = field2.getType().getName();
//                    String fieldNameDist = field2.getName();
//
//                    System.out.println("typeNameOfdist:"+typeNameOfdist+",fieldNameDist:"+fieldNameDist);
////                    if (StringUtils.equals(fieldNameOrig, fieldNameDist)) {
////                        Class clazz = Class.forName(typeNameOfdist);
////                        if (StringUtils.containsIgnoreCase(typeNameOfOrig, "string") && StringUtils.containsIgnoreCase(typeNameOfdist, "string")) {
////                            Method setmethod = ReflectionUtils.getDeclaredMethod(dist, addSetString(field.getName()), clazz);
////                            setmethod.invoke(dist, obj);
////
////                        }
////                        if (StringUtils.containsIgnoreCase(typeNameOfOrig, "long") && StringUtils.containsIgnoreCase(typeNameOfdist, "long")) {
////                            Method setmethod = ReflectionUtils.getDeclaredMethod(dist, addSetString(field.getName()), clazz);
////                            setmethod.invoke(dist, obj);
////
////                        }
////
////                        if (StringUtils.containsIgnoreCase(typeNameOfOrig, "int") && StringUtils.containsIgnoreCase(typeNameOfdist, "int")) {
////
////                            Method setmethod = ReflectionUtils.getDeclaredMethod(dist, addSetString(field.getName()), clazz);
////                            setmethod.invoke(dist, obj);
////                        }
////
////                        if (StringUtils.containsIgnoreCase(typeNameOfOrig, "float") && StringUtils.containsIgnoreCase(typeNameOfdist, "float")) {
////
////                            Method setmethod = ReflectionUtils.getDeclaredMethod(dist, addSetString(field.getName()), clazz);
////                            setmethod.invoke(dist, obj);
////                        }
////
////                        if (StringUtils.containsIgnoreCase(typeNameOfOrig, "int") && StringUtils.containsIgnoreCase(typeNameOfdist, "float")) {
////
////                            Method setmethod = ReflectionUtils.getDeclaredMethod(dist, addSetString(field.getName()), clazz);
////                            setmethod.invoke(dist, obj);
////                        }
////
////                        if (StringUtils.containsIgnoreCase(typeNameOfOrig, "String") && StringUtils.containsIgnoreCase(typeNameOfdist, "date")) {
////                            Method setmethod = ReflectionUtils.getDeclaredMethod(dist, addSetString(field.getName()), clazz);
////                            Date date = new Date(Long.parseLong(String.valueOf(obj)));
////                            setmethod.invoke(dist, date);
////                        }
////
////                    }
//                }
//
//            }
//        } catch (NumberFormatException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
////        catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
//
//    }
//
//    public static String addGetString(String fieldName) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("get");
//        sb.append(fieldName.substring(0, 1).toUpperCase());
//        sb.append(fieldName.substring(1));
//        return sb.toString();
//    }
//
//    public static String addSetString(String fieldName) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("set");
//        sb.append(fieldName.substring(0, 1).toUpperCase());
//        sb.append(fieldName.substring(1));
//        return sb.toString();
//    }
//
//    public static void charOutStream() throws Exception{
//        // 1：利用File类找到要操作的对象
//        File file = new File("D:" + File.separator + "demo" + File.separator + "test.txt");
//        if(!file.getParentFile().exists()){
//            file.getParentFile().mkdirs();
//        }
//
//        //2：准备输出流
//        Writer out = new FileWriter(file);
//        out.write("测试字符流, 哈哈");
//        out.close();
//
//    }
//
//    public static void byteOutStream() throws Exception {
//
//        //1:使用File类创建一个要操作的文件路径
//        File file = new File("D:" + File.separator + "demo" + File.separator + "test.txt");
//        if(!file.getParentFile().exists()){ //如果文件的目录不存在
//            file.getParentFile().mkdirs(); //创建目录
//
//        }
//
//        //2: 实例化OutputString 对象
//        OutputStream output = new FileOutputStream(file);
//
//        //3: 准备好实现内容的输出
//
//        String msg = "HelloWorld";
//        //将字符串变为字节数组
//        byte data[] = msg.getBytes();
//        output.write(data);
//        //4: 资源操作的最后必须关闭
//        output.close();
//
//    }
//
//
//}
//
