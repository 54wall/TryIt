package pri.weiqiang.tryit.lib.arraytest;

class Intersect {
    public static void main(String[] args){
        int[] nums1 = {1,2,2,1};
        int[] nums2 = {2,2};
        //result:{2,2}
        intersect(nums1,nums2);
    }

    public static int[] intersect(int[] nums1,int[] nums2){
        int[] state1 = new int[nums1.length];
        int[] state2 = new int[nums2.length];
        for (int i = 0;i<nums1.length;i++){
            for (int j = 0;j<nums2.length;j++){
                if(nums1[i] == nums2[j]&&state1[i]!=1&&state2[j]!=1){
                    state1[i] = 1;
                    state2[j] = 1;
                }
            }
        }
        int len = 0;
        for (int i=0;i<state1.length;i++){
            if (state1[i]==1){
                len++;
            }
        }

        int[] result = new int[len];
        int index = 0;
        for (int i=0;i<state1.length;i++){
            if (state1[i]==1){
                result[index]=nums1[i];
                System.out.println("result:"+result[index]);
                index++;

            }
        }
        return result;
    }
}
