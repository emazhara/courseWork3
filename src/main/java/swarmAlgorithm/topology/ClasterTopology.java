package swarmAlgorithm.topology;

import swarmAlgorithm.Agent;
import swarmAlgorithm.Vector;
import java.util.Random;

public class ClasterTopology implements Topology {
    public int cliquesCount;
    public int numberOfCliqueVertexes;

    public ClasterTopology(int agentsCount, int cliquesCount){
        this.cliquesCount = cliquesCount;
        this.numberOfCliqueVertexes = agentsCount / cliquesCount;
    }
    @Override
    public void agentNeighbourhood(Agent particle, int agentsCount){
        for(int i = particle.index - particle.index % this.numberOfCliqueVertexes; //adding neighbours in the clique
            i < particle.index - particle.index % this.numberOfCliqueVertexes + this.numberOfCliqueVertexes; i++)
        {
            if(i != particle.index)
                particle.neighbours.add(i);
        }
        int thisCliqueNumber = particle.index / this.cliquesCount + 1;
        int vertexNumber = particle.index % this.numberOfCliqueVertexes + 1; //number of vertex in clique counting only this cliques verteces
        if((vertexNumber <= this.cliquesCount) && (vertexNumber != thisCliqueNumber)){
            int neighbourIndex = (vertexNumber - 1) * this.numberOfCliqueVertexes + thisCliqueNumber - 1;
            particle.neighbours.add(neighbourIndex);
        }
    }
}
