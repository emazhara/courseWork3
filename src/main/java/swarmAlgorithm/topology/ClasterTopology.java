package swarmAlgorithm.topology;

import swarmAlgorithm.Agent;
import swarmAlgorithm.Vector;
import java.util.Random;
import java.util.Scanner;

public class ClasterTopology implements Topology {
    public int cliquesCount;
    public int numberOfCliqueVertexes;
    private Vector[] cliqueConnectionVertexes;
    private int[] vertexesPower;

    public ClasterTopology(int agentsCount){
        System.out.print("For this topology you have to insert the amount of cliques in the neighbourhood graph\n");
        Scanner scanner = new Scanner(System.in);
        cliquesCount = scanner.nextInt();
        while(agentsCount % cliquesCount != 0 || agentsCount / cliquesCount < cliquesCount - 1) {
            System.out.print("Agents amount = " + agentsCount +
                    " cannot be divided by the number of cliques = " + cliquesCount + "\n");
            cliquesCount = scanner.nextInt();
        }
        numberOfCliqueVertexes = agentsCount / cliquesCount;
        vertexesPower = new int[agentsCount];
        for(int vertexPower:vertexesPower)
            vertexPower = numberOfCliqueVertexes - 1;
    }
    public void generateCliqueConnectionVertexes() throws Exception{
        cliqueConnectionVertexes = new Vector[new Factorial().factorial(cliquesCount - 1)];
        for(Vector v:cliqueConnectionVertexes){
            v = new Vector(2);
        }
        Random random = new Random(System.currentTimeMillis());
        int vectorIndex = 0;
        for(int i = 1; i < cliquesCount; i++){
            for(int j = i + 1; j <= cliquesCount; j++){
                int vertex1 = (random.nextInt() % numberOfCliqueVertexes + 1) * i;
                while(vertexesPower[vertex1] >= numberOfCliqueVertexes)
                    vertex1 = (random.nextInt() % numberOfCliqueVertexes + 1) * i;
                vertexesPower[vertex1]++;
                int vertex2 = (random.nextInt() % numberOfCliqueVertexes + 1) * j;
                while(vertexesPower[vertex2] >= numberOfCliqueVertexes)
                    vertex2 = (random.nextInt() % numberOfCliqueVertexes + 1) * j;
                vertexesPower[vertex2]++;
                double[] coords = new double[2];
                coords[0] = vertex1;
                coords[1] = vertex2;
                cliqueConnectionVertexes[vectorIndex++].sum(new Vector(coords));
            }
        }
    }
    @Override
    public void agentNeighbourhood(Agent particle, int agentsCount){
        for(int i = particle.index - particle.index % numberOfCliqueVertexes + 1; //adding neighbours in the clique
            i < particle.index - particle.index % numberOfCliqueVertexes + numberOfCliqueVertexes; i++)
        {
            if(i != particle.index)
                particle.neighbours.add(i);
        }
        for(Vector v:cliqueConnectionVertexes){
            int particleInclusion = v.isIncluded(particle.index);
            if(particleInclusion >= 0)
                particle.neighbours.add((int)v.coordinates[(particleInclusion + 1) % 2]);
        }
    }
}
