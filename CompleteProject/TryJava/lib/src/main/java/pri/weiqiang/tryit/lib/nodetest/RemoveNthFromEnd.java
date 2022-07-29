package pri.weiqiang.tryit.lib.nodetest;

/**
 * 删除链表的倒数第N个节点
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * * https://leetcode.cn/leetbook/read/top-interview-questions-easy/xn2925/
 */
class RemoveNthFromEnd {
    public static void main(String[] args) {
        ListNode node5 = new ListNode(5,null);
        ListNode node4 = new ListNode(4,node5);
//        ListNode node3 = new ListNode(3,node4);
//        ListNode node2 = new ListNode(2,node3);
//        ListNode node1 = new ListNode(1,node2);
        removeNthFromEnd(node4,1);
    }

    static class ListNode {
        int value;
        ListNode next;

        ListNode(int value, ListNode next) {
            this.value = value;
            this.next = next;
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode nodeStart = head;
        ListNode nodeEnd = head;
        int index = 0;
        while (nodeStart.next != null && index < n) {
            nodeStart = nodeStart.next;
            index++;
        }
        //如果nodeStart为空，表示删除的是头结点
        if (nodeStart.next == null) {
            System.out.println("head.next:"+head.next.value);
            head = head.next;
            return head;
        }
        while (nodeStart.next != null) {
            nodeEnd = nodeEnd.next;
            nodeStart = nodeStart.next;
        }
        nodeEnd.next = nodeEnd.next.next;
        System.out.println("head:"+head.value);
        return head;
    }
}
