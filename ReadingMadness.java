/**
 *
 * @author GIUSEPPE MUSCHETTA
 */
public class ReadingMadness implements Runnable{

    MyConcurrentLinkedList list;

    public ReadingMadness(MyConcurrentLinkedList list){
        this.list = list;
    }

    @Override
    public void run() {
        System.out.println("Actual size = "+list.getSize());
    }
}
