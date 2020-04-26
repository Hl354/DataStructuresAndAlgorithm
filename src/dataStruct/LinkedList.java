package dataStruct;

/**
 * 无头结点单向链表
 * 以前一直不知道头节点的作用，现在写了代码才知道头节点的作用和好处。
 * 有头节点在实现链表的增加和删除会更容易。
 */
public class LinkedList {

    // 节点内容
    public Integer val;
    // 下一节点指针
    public LinkedList next;

    public LinkedList() {
    }

    public LinkedList(Integer val, LinkedList next) {
        this.val = val;
        this.next = next;
    }

    // 获取一个链表的长度
    public int length(LinkedList list) {
        int length = 0;
        LinkedList temp = list;
        while (temp != null) {
            temp = temp.next;
            length++;
        }
        return length;
    }

    // 无头结点单向链表添加一个元素
    public void add(LinkedList list, int num, int index) {
        if (index < 0 || list == null) {
            return;
        }
        LinkedList newNode = new LinkedList(num, null);
        if (index == 0) {
            newNode.val = list.val;
            newNode.next = list.next;
            list.val = num;
            list.next = newNode;
            return;
        }
        LinkedList temp = list;
        while (temp != null && index > 1) {
            temp = temp.next;
            index--;
        }
        LinkedList oldNode = temp.next;
        temp.next = newNode;
        newNode.next = oldNode;
    }

    // 打印链表
    public void printList(LinkedList list) {
        LinkedList temp = list;
        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList(1, null);
        System.out.println(list.length(list));
        list.printList(list);
        list.add(list, 2, 1);
        list.printList(list);
        list.add(list, -1, 0);
        list.printList(list);
    }

}
