package dataStruct;

public class Stack {

    // 栈顶指针
    private int top = -1;

    // 初始容量
    private final int START_CAPACITY = 10;

    // 数据存放数组
    private int[] data;

    // 默认数组初始容量构造器
    public Stack() {
        data = new int[START_CAPACITY];
    }

    // 自定数组初始容量构造器
    public Stack(int capacity) {
        data = new int[capacity > 0 ? capacity : START_CAPACITY];
    }

    // 栈中元素个数
    public int length() {
        return top + 1;
    }

    // 入栈
    public void push(int num) {
        // 是否需要扩容
        if (top == data.length - 1) {
            dilatationArray();
        }
        data[++top] = num;
    }

    // 出栈
    public Integer pop() {
        return top > -1 ? data[top--] : null;
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
        for (int i = 0; i <= top; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Stack stack = new Stack(2);
        stack.push(1);
        stack.push(2);
        stack.print();
        stack.push(3);
        stack.print();
        stack.pop();
        stack.print();
    }

}
