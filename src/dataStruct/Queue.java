package dataStruct;

public class Queue {

    // 队尾指针
    private int tail = -1;

    // 初始容量
    private final int START_CAPACITY = 10;

    // 数据存放数组
    private int[] data;

    // 默认数组初始容量构造器
    public Queue() {
        data = new int[START_CAPACITY];
    }

    // 自定数组初始容量构造器
    public Queue(int capacity) {
        data = new int[capacity > 0 ? capacity : START_CAPACITY];
    }

    // 队列长度
    public int length() {
        return tail + 1;
    }

    // 入队
    public void enqueue(int num) {
        if (tail == data.length - 1) {
            dilatationArray();
        }
        data[++tail] = num;
    }

    // 出队
    public void pop() {
        if (tail < 0) {
            return;
        }
        for (int i = 0; i <= tail; i++) {
            data[i] = data[i + 1];
        }
        tail--;
    }

    // 数组扩容
    public void dilatationArray() {
        int[] newArray = new int[data.length + START_CAPACITY];
        for (int i = 0, len = data.length; i < len; i++) {
            newArray[i] = data[i];
        }
        data = newArray;
    }

    // 打印数据
    public void print() {
        for (int i = 0; i <= tail; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Queue queue = new Queue(2);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.print();
        queue.enqueue(3);
        queue.print();
        queue.pop();
        queue.print();
    }

}
