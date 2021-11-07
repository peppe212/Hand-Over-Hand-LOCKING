import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author GIUSEPPE MUSCHETTA
 */
public class MyConcurrentLinkedList {
    //hand-over-hand locking: mutua esclusione solo su piccole porzioni della lista,
    //permettendo ad altri thread l'accesso ad elementi diversi della struttura

    Node head;
    Node tail;
    ReadWriteLock listMutex = new ReentrantReadWriteLock();

    public MyConcurrentLinkedList(){
        this.head = null;
        this.tail = null;
    }

    public void insert(int value) {
        Node nuovo = new Node(value);
        //due thread possono entrare e creare due teste diverse, quindi locko in write la lista
        listMutex.writeLock().lock();
        if (head == null) {
            head = nuovo;
            tail = head;
        }
        else {
            tail.next = nuovo;
            nuovo.prev = tail;
            tail = nuovo;
        }
        listMutex.writeLock().unlock();
    }

    //remove the given element if it belongs to the collection
    //in the removal it is mandatory to gain the lock on previous, current and next element
    public Node removeElement(int info){
        Node predecessor = null;
        Node current = head;
        Node successor = head.next;
        Node former = null;
        while(current!=null) {
            if(current.info == info) {
                if (predecessor == null) {
                    //sono sul primo elemento head
                    current.mutex.lock();
                    successor.mutex.lock();
                    former = head;
                    head = head.next;
                    successor.mutex.unlock();
                    current.mutex.unlock();
                }
                else if (successor == null) {
                    //sono sull'ultimo elemento tail
                    predecessor.mutex.lock();
                    current.mutex.lock();
                    former = tail;
                    tail = tail.prev;
                    current.mutex.unlock();
                    predecessor.mutex.unlock();
                }
                else {
                    //sono in mezzo, su un qualsiasi elemento intermedio
                    predecessor.mutex.lock();
                    current.mutex.lock();
                    successor.mutex.lock();

                    former = current;
                    predecessor.next = successor;
                    successor.prev = predecessor;

                    successor.mutex.unlock();
                    current.mutex.unlock();
                    predecessor.mutex.unlock();
                }
                break;
            }
            //readlock for a better synchronization
            listMutex.readLock().lock();
            predecessor = current;
            current = current.next;
            if(current!=null)
                successor = current.next;
            listMutex.readLock().unlock();
        }
        return former;
    }

    public boolean isEmpty(){
        //return this.size == 0; without synchronization one line of code is enough
        listMutex.readLock().lock();
        boolean esito = false;
        if(getSize() == 0)
            esito = true;
        listMutex.readLock().unlock();
        return esito;
    }

    public int getSize(){
        Node current = head;
        int counter = 0;
        while(current!=null){
            ReentrantLock mutex = current.mutex;
            mutex.lock();
            current = current.next;
            counter++;
            mutex.unlock();
        }
        return counter;
    }

    //a readlock for the whole method is enough
    //other readers are allowed to cross the critic zone
    public void displayElements(){
        listMutex.readLock().lock();
        int counter = 1;
        Node current = head;
        while(current != null) {
            if ((counter % 20) == 0) {
                System.out.println(current.info);
            }
            else {
                if (current.next == null)
                    System.out.println(current.info);
                else
                    System.out.print(current.info + " ");
            }
            current=current.next;
            counter++;
        }
        listMutex.readLock().unlock();
    }

}
