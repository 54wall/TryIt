package pri.weiqiang.tryit.lib.nodetest;

class SortNode {
    public static void main(String[] args) {

    }

    class ListNode {
        int value;
        ListNode next;

        ListNode(int value, ListNode next) {
            this.value = value;
            this.next = next;
        }
    }
    /**思路：把cur和next想象成两个指针，同时像后移动，首先把cur的next指定为previous,然后把previous的指针指向
     * 现在的cur,然后是cur指针指向next,四段代码，正好衔接在一起*/
    public static void sortNode(ListNode head) {
        ListNode cur = head;
        ListNode previous = null;
        while (cur.next != null) {
            ListNode next = cur.next;
            cur.next = previous;
            previous = cur;
            cur = next;

        }
    }

}
