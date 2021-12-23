package swarmAlgorithm.topology;

import swarmAlgorithm.Agent;

public interface Topology {
    int RING = 1;
    int CLIQUE = 2;
    int TORUS = 3;
    int CLASTER = 4;
    void agentNeighbourhood(Agent particle, int agentsCount);
}
