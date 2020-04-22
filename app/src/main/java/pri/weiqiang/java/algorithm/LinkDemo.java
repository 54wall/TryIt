package pri.weiqiang.java.algorithm;

public class LinkDemo {
    //https://blog.csdn.net/pbrlovejava/article/details/82975746
    public static void main(String[] args) {
        LinkList linkList = new LinkList();
        linkList.insertFirst(1);
        linkList.insertFirst(2);
        linkList.insertFirst(3);
        linkList.insertFirst(4);
        linkList.insertFirst(5);
        System.out.println("遍历1");
        linkList.displayLinkList();
        System.out.println("删除两个链头");
        linkList.deleteFirst();
        linkList.deleteFirst();
        System.out.println("遍历2");
        linkList.displayLinkList();
    }
}
