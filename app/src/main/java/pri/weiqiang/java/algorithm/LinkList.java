package pri.weiqiang.java.algorithm;

/**
 * @Description:LinkList Demo
 * @CreateDate: Created in 2018/10/8 23:05
 * @Author: <a href="https://blog.csdn.net/pbrlovejava">arong</a>
 */
public class LinkList {
    //第一个链结点
    private Link first;

    //含参构造，将first赋为null
    public LinkList() {
        this.first = null;
    }


    //判断链表是否为空
    public boolean isEmpty() {
        return (this.first == null);
    }

    //插入链头结点
    public void insertFirst(int data) {
        Link linkFirst = new Link(data);
        linkFirst.next = this.first;//linkHead -> old first
        this.first = linkFirst;//new first -> linkHead
    }

    //遍历链表
    public void displayLinkList() {
        Link current = this.first;//从链头开始遍历
        while (current != null) {
            //当前链结点不为空即输出内容
            current.displayLink();
            //将当前链结点指向下一个链结点
            current = current.next;
        }
    }

    //删除链头
    public void deleteFirst() {
        this.first = this.first.next;
    }

    //链表结点
    static class Link {
        public int data;//结点中存储的数据
        public Link next;//下一个结点

        //含参构造方法
        public Link(int data) {
            this.data = data;
        }

        //输出链结点
        public void displayLink() {
            System.out.print(this.data + "->");
        }
    }

}

