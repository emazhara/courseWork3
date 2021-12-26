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
        int radius = 5;
        //реализация данного метода зависит от оптимизируемой функции
        for(int i = 0; i < X.dimension; i++)
            result += X.coordinates[i] * X.coordinates[i];
        /*for(int i = 0; i < X.dimension; i++)
            result -= X.coordinates[i] * X.coordinates[i];
        result += radius * radius;
        result = -Math.sqrt(result);*/
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
                TorusTopology torusTopology = new TorusTopology(freeParameters.agentsCount);
                torusTopology.agentNeighbourhood(this, freeParameters.agentsCount);
                break;
            case Topology.CLASTER:
                ClasterTopology clasterTopology = new ClasterTopology(freeParameters.agentsCount);
                clasterTopology.generateCliqueConnectionVertexes();
                clasterTopology.agentNeighbourhood(this, freeParameters.agentsCount);
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
