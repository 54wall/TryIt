package pri.weiqiang.java.algorithm;

public class LinkReverse {
    public static void main(String[] args) {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Node e = new Node(5);
        a.setNext(b);
        b.setNext(c);
        c.setNext(d);
        d.setNext(e);
        System.out.println(a);/*data=1-->data=2-->data=3-->data=4-->data=5-->null*/
        Node reverse = reverse(a);/*递归方式反转*/
        System.out.println(reverse);/*data=5-->data=4-->data=3-->data=2-->data=1-->null*/
    }


    private static Node reverse(Node node) {
        /*如果是空链或者只是单个节点的链表  将直接返回*/
        if (node == null || node.getNext() == null) {
            return node;
        }
        Node reverse = reverse(node.getNext());/*找到了最后一个   也就是5   当前head为4  reverse为5*/
        node.getNext().setNext(node);/* 1-->2-->3-->4-->5   变为   5-->4  1-->2-->3-->4  此时4指向5  5 也指向4*/
        node.setNext(null);  /*4-->null    5-->4-->null  1-->2-->3-->4 */
        return reverse;    /*返回5-->4-->null*/
    }
}
