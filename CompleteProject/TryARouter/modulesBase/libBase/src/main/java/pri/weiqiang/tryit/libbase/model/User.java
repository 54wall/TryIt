package pri.weiqiang.tryit.libbase.model;

import java.io.Serializable;

public class User implements Serializable {
   private String useName;

   public String getUseName() {
      return useName;
   }

   public void setUseName(String useName) {
      this.useName = useName;
   }

   @Override
   public String toString() {
      return "User{" +
              "useName='" + useName + '\'' +
              '}';
   }

   public User(String useName) {
      this.useName = useName;
   }


}
