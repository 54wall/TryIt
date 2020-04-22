package pri.weiqiang.java.construct;

class Construct {

    public void main(String[] args) {

    }

    class Flower {
        private String name;
        private String color;

        Flower(String name, String color) {
            this.name = name;
            this.color = color;
        }
    }

    class FlowerA extends Flower {
        private String name;
        private String color;

        FlowerA(String name, String color) {
            super(name, color);//没有空构造函数，必须调用super(name, color)
            this.name = name;
            this.color = color;
        }
    }

    class Flower0 {
        private String name;
        private String color;

        Flower0() {
        }

        Flower0(String name, String color) {
            this.name = name;
            this.color = color;
        }
    }

    class FlowerB extends Flower0 {
        private String name;
        private String color;

        FlowerB(String name, String color) {
            /*
             * 这样就解决了问题,所以以后在书写子类的构造方法时记得构造方法的第一行是隐式的调用了父类的
             * 无参构造方法,所以父类有无参的构造方法,不然就需要自己显示的效用父类的构造方法来保证父类的初始化
             * */
            this.name = name;
            this.color = color;
        }
    }

}
