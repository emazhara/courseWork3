package swarmAlgorithm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiStart{
    public FreeParameters freeParameters;
    double finalResult;

    public MultiStart(FreeParameters freeParameters){
        this.freeParameters = freeParameters;
        finalResult = Double.MAX_VALUE;
    }

    public void multiStartRun() throws Exception{
        ExecutorService service = Executors.newWorkStealingPool();
        Future[] futureTasks = new Future[this.freeParameters.multiStartNumber];
        for(int start = 0; start < this.freeParameters.multiStartNumber; start++){
            final int startNumber = start;
            futureTasks[startNumber] = service.submit(()->{
                StartAlgorithm running = new StartAlgorithm(freeParameters);
                running.run();
                if(running.overallBestFitnessFunctionValue < this.finalResult){
                    this.finalResult = running.overallBestFitnessFunctionValue;
                }
            });
        }
        for(Future task:futureTasks){
            task.get();
        }
    }
}
