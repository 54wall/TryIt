package pri.weiqiang.tryit.proxy;

public class RealSubject implements Subject {
   @Override
   public void sayGoodBye() {
      System.out.println("RealSubject sayGoodBye");
   }
   @Override
   public void sayHello(String str) {
      System.out.println("RealSubject sayHello  " + str);
   }
}

