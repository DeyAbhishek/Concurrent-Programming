// Link to compiled code: http://goo.gl/SXurhO
// Link to compiled code: http://www.tutorialspoint.com/compile_java_online.php?PID=0Bw_CjBb95KQMX3pYM2xNeWx5OUk
// Reference: http://www.journaldev.com/1090/java-callable-future-example
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
 
public class HelloWorld implements Callable<String> {
 
    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }
     
    public static void main(String args[]){
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //create a list to hold the Future object associated with Callable
        List<Future<String>> list = new ArrayList<Future<String>>();
        //Create MyCallable instance
        Callable<String> callable = new HelloWorld();
        for(int i=0; i< 100; i++){
            //submit Callable tasks to be executed by thread pool
            Future<String> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
        }
        for(Future<String> fut : list){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(new Date()+ "::"+fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

            executor.shutdown();  //call shutdown before awaitTermination. otherwise you may wait
                                  // unnecessary for 30 seconds in some cases even when not needed to wait and 
                                  //all threads have terminated already
    
        
        try{
            // waits for termination for 30 seconds only
            executor.awaitTermination(30,TimeUnit.SECONDS);
        }catch(InterruptedException ex){}
        
}
}
