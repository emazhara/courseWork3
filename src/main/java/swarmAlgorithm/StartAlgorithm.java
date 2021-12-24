package swarmAlgorithm;

import java.util.Arrays;

public class StartAlgorithm implements Runnable{
    public FreeParameters freeParameters;
    public double overallBestFitnessFunctionValue;
    private int stagnationCounter;

    public StartAlgorithm(FreeParameters freeParameters){
        this.freeParameters = freeParameters;
        this.overallBestFitnessFunctionValue = Double.MAX_VALUE;
        this.stagnationCounter = 0;
    }

    @Override
    public void run(){
        try {
            Swarm swarm = new Swarm(this.freeParameters);
            double[] allFitnessFunctionValues = new double[this.freeParameters.agentsCount];
            for(int iterationNumber = 0; iterationNumber < freeParameters.maximumIterationsNumber; iterationNumber++){
                if(this.stagnationCounter >= this.freeParameters.stagnationLimit)
                    return;
                swarm.nextIteration(this.freeParameters);
                for(int i = 0; i < this.freeParameters.agentsCount; i++)
                    allFitnessFunctionValues[i] = swarm.particles[i].getFitnessFunctionValue(swarm.particles[i].currentPosition);
                Arrays.sort(allFitnessFunctionValues);
                double bestFitnessFunctionValue = allFitnessFunctionValues[0];
                if(bestFitnessFunctionValue < overallBestFitnessFunctionValue){
                    overallBestFitnessFunctionValue = bestFitnessFunctionValue;
                    stagnationCounter = 0;
                }
                else if(bestFitnessFunctionValue == overallBestFitnessFunctionValue)
                    stagnationCounter++;
                else
                    stagnationCounter = 0;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}