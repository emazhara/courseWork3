package swarmAlgorithm.topology;

import swarmAlgorithm.Agent;
import java.util.Scanner;

public class TorusTopology implements Topology {
    int torusSize;

    public TorusTopology(int agentsCount){
        System.out.print("For this topology you have to insert the size of the torus\n");
        Scanner scanner = new Scanner(System.in);
        int torusSize = scanner.nextInt();
        while(agentsCount % torusSize != 0) {
            System.out.print("Agents amount = " + agentsCount + " cannot be divided by the size of torus = "
                    + torusSize + "\n");
            torusSize = scanner.nextInt();
        }
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
