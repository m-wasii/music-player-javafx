package com.example.musicplayer2;

public class doublyLinkedList<F> {
    Node head = null;

    class Node {
        F data;
        Node prev = null;
        Node next = null;

        Node(F d) {
            data = d;
        }
    }

    public void insertFront(F data) {
        Node newNode = new Node(data);

        newNode.next = head;

        newNode.prev = null;

        if (head != null) head.prev = newNode;

        head = newNode;
    }

    public void circular(doublyLinkedList<F> list) {
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        list.head.prev = temp;
        temp.next = head;
    }

    public void linear(doublyLinkedList<F> list) {
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        list.head.prev.next = null;
        list.head.prev = null;
    }


    public void insertAfter(Node prev_node, F data) {

        if (prev_node == null) {
            System.out.println("previous node cannot be null");
            return;
        }

        Node new_node = new Node(data);

        new_node.next = prev_node.next;

        prev_node.next = new_node;

        new_node.prev = prev_node;

        if (new_node.next != null) new_node.next.prev = new_node;
    }

    void insertEnd(F data) {
        Node new_node = new Node(data);

        Node temp = head;

        new_node.next = null;

        if (head == null) {
            new_node.prev = null;
            head = new_node;
            return;
        }

        while (temp.next != null) temp = temp.next;

        temp.next = new_node;

        new_node.prev = temp;
    }

    void deleteNode(Node del_node) {

        if (head == null || del_node == null) {
            return;
        }

        if (head == del_node) {
            head = del_node.next;
        }

        if (del_node.next != null) {
            del_node.next.prev = del_node.prev;
        }

        if (del_node.prev != null) {
            del_node.prev.next = del_node.next;
        }

    }

    public void printList(Node node) {
        Node last = null;
        while (node != null) {
            System.out.print(node.data + "->");
            last = node;
            node = node.next;
        }
        System.out.println();
    }

    public F get(int index) {
        Node node = head;

        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.data;
    }

    public int length() {

        Node node = head;
        int count = 0;

        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }

    public void update(int index, F data) {
        Node node = head;

        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        node.data = data;
    }


}

