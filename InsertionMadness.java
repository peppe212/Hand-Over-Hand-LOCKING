import java.util.Random;

/**
 *
 * @author GIUSEPPE MUSCHETTA
 */
public class InsertionMadness implements Runnable{

    MyConcurrentLinkedList list;

    public InsertionMadness(MyConcurrentLinkedList list){
        this.list = list;
    }

    @Override
    public void run() {
        list.insert(new Random().nextInt(10));
    }
}
