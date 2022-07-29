package pri.weiqiang.tryit.lib.nodetest;

/**
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 */
class DeleteLastNumNode {
   public static void main(String[] args) {
      ListNode n1 = new ListNode(4);
      ListNode n2 = new ListNode(5);
      ListNode n3 = new ListNode(1);
      ListNode n4 = new ListNode(9);
      n1.next = n2;
      n2.next = n3;
      n3.next = n4;
      removeNthFromEnd(n1,2);
   }


   private static class ListNode {
      int val;
      ListNode next;
      ListNode(int x){
         val = x;
      }

      ListNode(int x,ListNode next){
         val = x;
         this.next = next;
      }
   }

   public static ListNode removeNthFromEnd(ListNode head, int n) {
      //初始化一个空节点，初始赋值为0，并且list的下一个next指针指向head，指针指向为dummy
      ListNode dummy = new ListNode(0, head);
      //快指针
      ListNode first = head;
      //慢指针
      ListNode second = dummy;
      //提前n步，确定快指针比慢指针快n个结点
      for (int i = 0; i < n; ++i) {
         first = first.next;
      }
      //如果快指针不为空，便证明慢指针还没有到指定的位置
      while (first != null) {
         //快指针和慢指针同时移动到下一步
         first = first.next;
         second = second.next;
      }
      //快慢指针到指定位置，要慢指针的后继结点指向后面的结点
      second.next = second.next.next;
      //
      ListNode ans = dummy.next;
      return ans;
   }
}
