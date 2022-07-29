package pri.weiqiang.tryit.lib.chartest;

/**
 * 有效的字母异位词，出现的字母个数全部一样
 */
public class IsAnagram {
   public static void main(String[] args){
      String s = "anagramss";
      String t = "nagaramss";
      System.out.println("isAnagram:"+isAnagram(s,t));
   }

   public static boolean isAnagram(String s,String t){
      char[] arrayLong;
      char[] arrayShort;
      if(s.length()>=t.length()){
         arrayLong = s.toCharArray();
         arrayShort= t.toCharArray();
      }else {
         arrayLong = t.toCharArray();
         arrayShort= s.toCharArray();
      }

      for (int i = 0;i<arrayLong.length;i++){
         boolean isCharExits  = false;
         for (int j = 0;j<arrayShort.length;j++){
            System.out.println("arrayS:"+arrayLong[i]+",arrayT:"+arrayShort[j]);
            if(arrayLong[i]==arrayShort[j]){
               arrayShort[j] = 0;
               isCharExits = true;
               break;
            }
         }

         if (!isCharExits){
            return false;
         }
      }


      return true;
   }

}
