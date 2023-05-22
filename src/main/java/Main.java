import function.FileRecorder;
import function.Grouper;

import java.io.File;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static long memory(){
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void main(String[] args){
        long memoryBefore = memory();
        long start = System.nanoTime();

        Grouper grouper = new Grouper(new File(args[0]));
        grouper.separator();
        FileRecorder fileRecorder  = new FileRecorder(grouper.getResult());
        fileRecorder.writeInFile();


        long finish = System.nanoTime();
        double result = TimeUnit.SECONDS.convert((finish - start), TimeUnit.NANOSECONDS);
        long memoryAfter = memory();
        System.out.println( "Number of groups = " + grouper.getResult().size() + "\n" +
                "Time " + result + " sec" + "\n"
                + "Memory used " + ((memoryAfter - memoryBefore)/1048576) + " Mb");

    }
}
