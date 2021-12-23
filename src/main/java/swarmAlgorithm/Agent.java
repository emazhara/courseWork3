package swarmAlgorithm;

import swarmAlgorithm.topology.*;

import java.util.ArrayList;
import java.util.Random;

public class Agent {
    public Vector currentPosition;
    public Vector velocity;
    public ArrayList<Integer> neighbours;
    public Vector bestFitnessFunctionArgument;
    public double bestFitnessFunctionValue;
    public Vector bestNeighbourPosition;
    public double bestNeighbourFitnessFunctionValue;
    public int index;

    public Agent(int seed, int dimension, int index, double[] minimumRestrictions, double[] maximumRestrictions) {
        this.index = index;
        Random random = new Random(seed);
        this.currentPosition = new Vector(dimension);
        this.velocity = new Vector(dimension);
        for (int i = 0; i < dimension; i++) {
            double residue = maximumRestrictions[index] - minimumRestrictions[index];
            this.currentPosition.coordinates[i] = random.nextDouble() * residue + minimumRestrictions[index];
            this.velocity.coordinates[i] = random.nextDouble() * 2 * residue - residue;
        }
        bestFitnessFunctionArgument = currentPosition;
        bestFitnessFunctionValue = getFitnessFunctionValue(currentPosition);
        bestNeighbourPosition = null;
        bestNeighbourFitnessFunctionValue = 0;
    }

    public double getFitnessFunctionValue(Vector X) {
        double result = 0;
        //реализация данного метода зависит от оптимизируемой функции
        return result;
    }

    public void setNeighbours(int topologyType, int agentsCount) throws Exception {
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
                throw new Exception("Topology with such an index does not exist\n");
        }
    }

    public void nextPosition(double inertialComponent, double cognitiveComponent, double socialComponent,
                             double[] minimumRestrictions, double[] maximumRestrictions) throws Exception {
        Random random = new Random();
        Vector newVelocity = new Vector(this.currentPosition.dimension);
        newVelocity.sum(this.velocity.scalarMultiplication(inertialComponent)).sum(this.bestFitnessFunctionArgument.
                sum(this.currentPosition.scalarMultiplication(-1)).scalarMultiplication(cognitiveComponent).
                scalarMultiplication(random.nextDouble())).sum(this.bestNeighbourPosition.sum(this.currentPosition.
                scalarMultiplication(-1)).scalarMultiplication(socialComponent).scalarMultiplication(random.nextDouble()));
        Vector newPosition = new Vector(this.currentPosition.dimension);
        newPosition = this.currentPosition.sum(newVelocity);
        for (int i = 0; i < newVelocity.dimension; i++) {
            if (newPosition.coordinates[i] > maximumRestrictions[i])
                newPosition.coordinates[i] = maximumRestrictions[i];
            if (newPosition.coordinates[i] < minimumRestrictions[i])
                newPosition.coordinates[i] = minimumRestrictions[i];
        }
        this.velocity = newVelocity;
        this.currentPosition = this.currentPosition.sum(newVelocity);
    }

    public void privateGuideNextPosition() { //this function updates bestFitnessFunctionArgument and ...Value after nextPosition() is called
        double newFitnessFunctionValue = getFitnessFunctionValue(currentPosition);
        if(newFitnessFunctionValue < bestFitnessFunctionValue){
            bestFitnessFunctionValue = newFitnessFunctionValue;
            bestFitnessFunctionArgument = currentPosition;
        }
    }
    public void localGuideNextPosition() { //this function updates bestNeighbourPosition and ...Value after nextPosition() call
        double[] allNeighbourFitnessFunctionValues = new double[this.neighbours.size()];
        for(int i = 0; i < this.neighbours.size(); i++){

        }
    }
}
