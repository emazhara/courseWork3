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

    public Agent(long seed, int index, FreeParameters freeParameters) {
        this.index = index;
        Random random = new Random(seed);
        this.currentPosition = new Vector(freeParameters.dimension);
        this.velocity = new Vector(freeParameters.dimension);
        for (int i = 0; i < freeParameters.dimension; i++) {
            double residue = freeParameters.maximumRestrictions[i] - freeParameters.minimumRestrictions[i];
            this.currentPosition.coordinates[i] = random.nextDouble() * residue + freeParameters.minimumRestrictions[i];
            this.velocity.coordinates[i] = random.nextDouble() * 2 * residue - residue;
        }
        this.bestFitnessFunctionArgument = this.currentPosition;
        this.bestFitnessFunctionValue = getFitnessFunctionValue(this.currentPosition);
        this.bestNeighbourPosition = this.currentPosition;
        this.bestNeighbourFitnessFunctionValue = Double.MAX_VALUE;
    }

    public double getFitnessFunctionValue(Vector X) {
        double result = 0;
        double tmp = 0;
        double tmp2 = 0;
        for(int i = 0; i < X.dimension; i++) {
            tmp += X.coordinates[i] * X.coordinates[i];
            tmp2 += Math.cos(2*Math.PI*X.coordinates[i]);
        }
        tmp /= X.dimension;
        tmp2 /= X.dimension;
        result = -20 * Math.exp(-0.2 * Math.sqrt(tmp));
        result -= Math.exp(tmp2);
        result += 20 + Math.exp(1);
        return result;
    }

    public void setNeighbours(FreeParameters freeParameters) throws Exception {
        this.neighbours = new ArrayList<>();
        switch (freeParameters.topologyType) {
            case Topology.RING:
                RingTopology ringTopology = new RingTopology();
                ringTopology.agentNeighbourhood(this, freeParameters.agentsCount);
                break;
            case Topology.CLIQUE:
                CliqueTopology cliqueTopology = new CliqueTopology();
                cliqueTopology.agentNeighbourhood(this, freeParameters.agentsCount);
                break;
            case Topology.TORUS:
                TorusTopology torusTopology = new TorusTopology(freeParameters.torusSize);
                torusTopology.agentNeighbourhood(this, freeParameters.agentsCount);
                break;
            case Topology.CLASTER:
                ClasterTopology clasterTopology = new ClasterTopology(freeParameters.agentsCount, freeParameters.cliquesCount);
                clasterTopology.agentNeighbourhood(this, freeParameters.agentsCount);
                break;
            default:
                throw new Exception("Topology with such a number does not exist\n");
        }
    }

    public void nextPosition(FreeParameters freeParameters) throws Exception {
        Random random = new Random(System.currentTimeMillis());
        Vector newVelocity;
        Vector inertion = this.velocity.scalarMultiplication(freeParameters.inertialComponent);
        Vector cognition = this.currentPosition.scalarMultiplication(-1).sum(this.bestFitnessFunctionArgument).
                scalarMultiplication(freeParameters.cognitiveComponent).scalarMultiplication(random.nextDouble());
        Vector socialisation = this.currentPosition.scalarMultiplication(-1).sum(this.bestNeighbourPosition).
                scalarMultiplication(freeParameters.socialComponent).scalarMultiplication(random.nextDouble());
        newVelocity = inertion.sum(cognition).sum(socialisation);

        Vector newPosition;
        newPosition = this.currentPosition.sum(newVelocity);
        for (int i = 0; i < newVelocity.dimension; i++) {
            if (newPosition.coordinates[i] > freeParameters.maximumRestrictions[i])
                newPosition.coordinates[i] = freeParameters.maximumRestrictions[i] -
                        random.nextDouble() * (freeParameters.maximumRestrictions[i] - freeParameters.minimumRestrictions[i]);
            if (newPosition.coordinates[i] < freeParameters.minimumRestrictions[i])
                newPosition.coordinates[i] = freeParameters.minimumRestrictions[i] +
                        random.nextDouble() * (freeParameters.maximumRestrictions[i] - freeParameters.minimumRestrictions[i]);
        }
        this.velocity = newVelocity;
        this.currentPosition = newPosition;
    }

    public void privateGuideNextPosition() { //this function updates bestFitnessFunctionArgument and ...Value after nextPosition() was called
        double newFitnessFunctionValue = getFitnessFunctionValue(currentPosition);
        if(newFitnessFunctionValue < bestFitnessFunctionValue){
            this.bestFitnessFunctionValue = newFitnessFunctionValue;
            this.bestFitnessFunctionArgument = currentPosition;
        }
    }
}
