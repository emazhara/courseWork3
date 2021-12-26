package swarmAlgorithm;

import java.util.Arrays;

public class StartAlgorithm implements Runnable{
    public FreeParameters freeParameters;
    public double overallBestFitnessFunctionValue;
    private int stagnationCounter;
    private int iterationCounter;

    public StartAlgorithm(FreeParameters freeParameters){
        this.freeParameters = freeParameters;
        this.overallBestFitnessFunctionValue = Double.MAX_VALUE;
        this.stagnationCounter = 0;
        this.iterationCounter = 0;
    }

    @Override
    public void run(){
        try {
            Swarm swarm = new Swarm(this.freeParameters);
            double[] allFitnessFunctionValues = new double[this.freeParameters.agentsCount];
            for(this.iterationCounter = 0; this.iterationCounter < freeParameters.maximumIterationsNumber; this.iterationCounter++){
                if(this.stagnationCounter >= this.freeParameters.stagnationLimit) {
                    System.out.print("Process finished because of stagnation\n");
                    System.out.print("Iterations: " + this.iterationCounter);
                    return;
                }
                swarm.nextIteration(this.freeParameters);
                for(int i = 0; i < this.freeParameters.agentsCount; i++)
                    allFitnessFunctionValues[i] = swarm.particles[i].getFitnessFunctionValue(swarm.particles[i].currentPosition);
                Arrays.sort(allFitnessFunctionValues);
                double bestFitnessFunctionValue = allFitnessFunctionValues[0];
                if(this.overallBestFitnessFunctionValue > bestFitnessFunctionValue){
                    this.overallBestFitnessFunctionValue = bestFitnessFunctionValue;
                    this.stagnationCounter = 0;
                }
                else
                    this.stagnationCounter++;
            }
            System.out.print("Iterations: " + this.iterationCounter + "\n");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}