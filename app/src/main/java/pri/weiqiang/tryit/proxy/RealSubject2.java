package pri.weiqiang.tryit.proxy;

public class RealSubject2 implements Subject {
   @Override
   public void sayGoodBye() {
      System.out.println("RealSubject2 sayGoodBye");
   }
   @Override
   public void sayHello(String str) {
      System.out.println("RealSubject2 sayHello  " + str);
   }
}

