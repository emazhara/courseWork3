package swarmAlgorithm;

import swarmAlgorithm.topology.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Agent {
    public Vector currentPosition;
    public Vector velocity;
    public ArrayList<Integer> neighbours;
    public Vector bestFitnessFunctionArgument;
    public double bestFitnessFunctionValue;
    public Vector bestNeighbourPosition;
    public double bestNeighbourFitnessFunctionValue;
    public int index;

    public Agent(long seed, int index, FreeParameters freeParameters) {
        this.index = index;
        Random random = new Random(seed);
        this.currentPosition = new Vector(freeParameters.dimension);
        this.velocity = new Vector(freeParameters.dimension);
        for (int i = 0; i < freeParameters.dimension; i++) {
            double residue = freeParameters.maximumRestrictions[index] - freeParameters.minimumRestrictions[index];
            this.currentPosition.coordinates[i] = random.nextDouble() * residue + freeParameters.minimumRestrictions[index];
            this.velocity.coordinates[i] = random.nextDouble() * 2 * residue - residue;
        }
        bestFitnessFunctionArgument = currentPosition;
        bestFitnessFunctionValue = getFitnessFunctionValue(currentPosition);
        bestNeighbourPosition = null;
        bestNeighbourFitnessFunctionValue = Double.MAX_VALUE;
    }

    public double getFitnessFunctionValue(Vector X) {
        double result = 0;
        //реализация данного метода зависит от оптимизируемой функции
        return result;
    }

    public void setNeighbours(int agentsCount) throws Exception {
        int topologyType;
        System.out.print("Insert the number of the topology you wish to select:\n" +
                        "1. Ring topology\n2. Clique topology\n3. Torus topology\n4. Claster topology\n");
        Scanner scanner = new Scanner(System.in);
        topologyType = scanner.nextInt();
        switch (topologyType) {
            case Topology.RING:
                RingTopology ringTopology = new RingTopology();
                ringTopology.agentNeighbourhood(this, agentsCount);
                break;
            case Topology.CLIQUE:
                CliqueTopology cliqueTopology = new CliqueTopology();
                cliqueTopology.agentNeighbourhood(this, agentsCount);
                break;
            case Topology.TORUS:
                TorusTopology torusTopology = new TorusTopology(agentsCount);
                torusTopology.agentNeighbourhood(this, agentsCount);
                break;
            case Topology.CLASTER:
                ClasterTopology clasterTopology = new ClasterTopology(agentsCount);
                clasterTopology.generateCliqueConnectionVertexes();
                clasterTopology.agentNeighbourhood(this, agentsCount);
            default:
                throw new Exception("Topology with such a number does not exist\n");
        }
    }

    public void nextPosition(FreeParameters freeParameters) throws Exception {
        Random random = new Random();
        Vector newVelocity = new Vector(this.currentPosition.dimension);
        newVelocity.sum(this.velocity.scalarMultiplication(freeParameters.inertialComponent)).sum(this.bestFitnessFunctionArgument.
                sum(this.currentPosition.scalarMultiplication(-1)).scalarMultiplication(freeParameters.cognitiveComponent).
                scalarMultiplication(random.nextDouble())).sum(this.bestNeighbourPosition.sum(this.currentPosition.
                scalarMultiplication(-1)).scalarMultiplication(freeParameters.socialComponent).scalarMultiplication(random.nextDouble()));
        Vector newPosition = new Vector(this.currentPosition.dimension);
        newPosition = this.currentPosition.sum(newVelocity);
        for (int i = 0; i < newVelocity.dimension; i++) {
            if (newPosition.coordinates[i] > freeParameters.maximumRestrictions[i])
                newPosition.coordinates[i] = freeParameters.maximumRestrictions[i];
            if (newPosition.coordinates[i] < freeParameters.minimumRestrictions[i])
                newPosition.coordinates[i] = freeParameters.minimumRestrictions[i];
        }
        this.velocity = newVelocity;
        this.currentPosition = this.currentPosition.sum(newVelocity);
    }

    public void privateGuideNextPosition() { //this function updates bestFitnessFunctionArgument and ...Value after nextPosition() was called
        double newFitnessFunctionValue = getFitnessFunctionValue(currentPosition);
        if(newFitnessFunctionValue < bestFitnessFunctionValue){
            this.bestFitnessFunctionValue = newFitnessFunctionValue;
            this.bestFitnessFunctionArgument = currentPosition;
        }
    }
}
