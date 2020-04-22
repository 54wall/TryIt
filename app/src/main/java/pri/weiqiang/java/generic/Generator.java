package pri.weiqiang.java.generic;

//定义一个泛型接口
public interface Generator<T> {
    public T next();
    //放到泛型接口类中

    /**
     * 未传入泛型实参时，与泛型类的定义相同，在声明类的时候，需将泛型的声明也一起加到类中
     * 即：class FruitGenerator<T> implements Generator<T>{
     * 如果不声明泛型，如：class FruitGenerator implements Generator<T>，编译器会报错："Unknown class"
     */
    class FruitGenerator<T> implements Generator<T> {
        @Override
        public T next() {
            return null;
        }
    }
}
