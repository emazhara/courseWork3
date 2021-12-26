package swarmAlgorithm.topology;

import swarmAlgorithm.Agent;
import java.util.Scanner;

public class TorusTopology implements Topology {
    public int torusSize;

    public TorusTopology(int torusSize){
        this.torusSize = torusSize;
    }

    @Override
    public void agentNeighbourhood(Agent particle, int agentsCount) {
        int lowerRounding = particle.index - particle.index % torusSize;
        particle.neighbours.add((torusSize + particle.index - 1) % torusSize + lowerRounding); //"left" neighbour
        particle.neighbours.add((torusSize + particle.index + 1) % torusSize + lowerRounding); //"right" neighbour
        particle.neighbours.add((particle.index + torusSize) % agentsCount); //"lower" neighbour
        particle.neighbours.add((particle.index + agentsCount - torusSize) % agentsCount); //"upper" neighbour
    }
}
