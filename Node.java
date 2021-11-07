import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author GIUSEPPE MUSCHETTA
 */
public class Node {
    //hand-over-hand locking: mutua esclusione solo su piccole porzioni della lista,
    //permettendo ad altri thread l'accesso ad elementi diversi della struttura

    int info;
    Node next;
    Node prev;
    ReentrantLock mutex = new ReentrantLock();

    public Node(int info){
        this.info = info;
        this.next=null;
        this.prev=null;
    }

}
