package Aufgaben.Aufgabe10;

import java.util.concurrent.Semaphore;

public class VerketteteList {
    private ListItem head;
    private ListItem tail;
    private Semaphore semaphore;

    public VerketteteList(int semaphoreNum) {
       this.semaphore = new Semaphore(semaphoreNum);
    }

    public ListItem get() {
        try {
            semaphore.acquire();
            if (head != null) {
                var next = head.getNext();
                var element = this.head;
                this.head = next;
                semaphore.release();

                return element;
            } else {
                semaphore.release();
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void push(String str) {
        try {
            semaphore.acquire();
            ListItem i = new ListItem(str);
            if (this.head == null) {
                this.head = i;
                this.head.setNext(null);
                this.tail = i;
                semaphore.release();
                return;
            }
            this.tail.setNext(i);
            this.tail = i;
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String ret = "";
        ListItem i = this.get();
        while (i != null) {
            ret += i.getValue() + "\n";
            i = this.get();
        }
        return ret;
    }

    public int count() {
        int count = 0;
        ListItem i = this.get();
        while (i != null) {
            count++;
            i = this.get();
        }
        return count;
    }
}
