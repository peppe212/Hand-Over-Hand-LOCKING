import java.util.Random;

/**
 *
 * @author GIUSEPPE MUSCHETTA
 */
public class RemovalMadness implements Runnable{

    MyConcurrentLinkedList list;

    public RemovalMadness(MyConcurrentLinkedList list){
        this.list = list;
    }

    @Override
    public void run() {
        list.removeElement(new Random().nextInt(10));
    }
}
