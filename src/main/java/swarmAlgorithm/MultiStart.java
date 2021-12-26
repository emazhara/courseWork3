package swarmAlgorithm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiStart{
    public FreeParameters freeParameters;
    double finalResult;
    int averageIterationCount;

    public MultiStart(FreeParameters freeParameters){
        this.freeParameters = freeParameters;
        this.finalResult = Double.MAX_VALUE;
        this.averageIterationCount = 0;
    }

    public void multiStartRun() throws Exception{
        int averageIterationCount = 0;
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
                this.averageIterationCount += running.iterationCounter;
            });
        }
        for(Future task:futureTasks){
            task.get();
        }
        this.averageIterationCount /= this.freeParameters.multiStartNumber;
        System.out.print("Average iteration count: " + this.averageIterationCount + "\n");
    }
}
