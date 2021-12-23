package swarmAlgorithm.topology;

import swarmAlgorithm.Agent;

public class CliqueTopology implements Topology {
    @Override public void agentNeighbourhood(Agent particle, int agentsCount){
        for(int i = 0; i < agentsCount; i++){
            if(particle.index != i)
                particle.neighbours.add(i);
        }
    }

}
