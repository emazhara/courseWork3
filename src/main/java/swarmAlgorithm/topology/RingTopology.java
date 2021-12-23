package swarmAlgorithm.topology;

import swarmAlgorithm.Agent;

public class RingTopology implements Topology {
    @Override
    public void agentNeighbourhood(Agent particle, int agentsCount){
        if(particle.index == 0)
            particle.neighbours.add(agentsCount - 1);
        else
            particle.neighbours.add(particle.index - 1);
        if(particle.index == agentsCount - 1)
            particle.neighbours.add(0);
        else
            particle.neighbours.add(particle.index + 1);
    }

}
