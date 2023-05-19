import function.Grouper;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class Main {

    public static long memory(){
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void main(String[] args){
        long memoryBefore = memory();
        long start = System.nanoTime();

        Grouper grouper = new Grouper(new File("src/main/resources/lng.txt"));
        grouper.separator();





        long finish = System.nanoTime();
        double result = TimeUnit.SECONDS.convert((finish - start), TimeUnit.NANOSECONDS);
        long memoryAfter = memory();
        System.out.println( "Time " + result + " sec" + "\n" + "Memory used " + ((memoryAfter - memoryBefore)/1048576) + " Mb");

    }
}
