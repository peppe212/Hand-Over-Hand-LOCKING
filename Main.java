import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author GIUSEPPE MUSCHETTA
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        MyConcurrentLinkedList list =  new MyConcurrentLinkedList();

        //Even with this amount of threads the program won't deadlock
        for(int i=0;i<300;i++){
            for(int j=0;j<10;j++){
                threadPool.execute(new InsertionMadness(list));
                Thread.sleep(2);
                threadPool.execute(new ReadingMadness(list));
            }
            Thread.sleep(1);
            for(int k=0;k<3;k++) {
                threadPool.execute(new RemovalMadness(list));
            }
        }

        Thread.sleep(500);
        System.out.println("\nLa lista contiene i seguenti elementi: \n");
        list.displayElements();
        threadPool.shutdownNow();

    }

}
