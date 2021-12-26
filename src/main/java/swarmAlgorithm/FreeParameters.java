package swarmAlgorithm;

import swarmAlgorithm.topology.Topology;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FreeParameters {
    public int agentsCount; //number of agents spread all over the search area
    public int dimension; //dimension of the search area
    public double[] minimumRestrictions; //left part of inequality restriction on each coordinate of particles' position
    public double[] maximumRestrictions; //right part of inequality restriction on each coordinate of particles' position
    public double inertialComponent;
    public double cognitiveComponent; //free parameters of newPosition() method
    public double socialComponent;
    public int maximumIterationsNumber; //once iteration count gets over maximumIterationsNumber the algorithm stops its work
    public int stagnationLimit; //once stagnation counter gets over stagnationLimit the algorithm stops its work
    public int multiStartNumber; //amount of start method calls
    public int topologyType; //type of topology used for filling array of neighbours for each particle
    public int torusSize; //size of torus which is filled in if selected type of topology is torus
    public int cliquesCount;

    public FreeParameters() throws NoSuchElementException {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        System.out.print("Insert the number of agents - particles in the search area\n" + "Number of agents: ");
        this.agentsCount = scanner.nextInt();
        System.out.print("Insert the dimension of the search area\n" + "Dimension: ");
        this.dimension = scanner.nextInt();
        System.out.print("Insert minimum restrictions for each coordinate of particle position\n");
        this.minimumRestrictions = new double[this.dimension];
        for(int i = 0; i < this.dimension; i++){
            System.out.print("Minimum restriction for " + (i+1) + " coordinate: ");
            this.minimumRestrictions[i] = scanner.nextDouble();
        }
        System.out.print("Insert maximum restrictions for each coordinate of particle position\n");
        this.maximumRestrictions = new double[this.dimension];
        for(int i = 0; i < this.dimension; i++){
            System.out.print("Maximum restriction for " + (i+1) + " coordinate: ");
            this.maximumRestrictions[i] = scanner.nextDouble();
        }
        System.out.print("Insert inertial component\n" + "Inertial component: ");
        this.inertialComponent = scanner.nextDouble();
        System.out.print("Insert cognitive component\n" + "Cognitive component: ");
        this.cognitiveComponent = scanner.nextDouble();
        System.out.print("Insert social component\n" + "Social component: ");
        this.socialComponent = scanner.nextDouble();
        System.out.print("Insert the number of the topology you wish to select:\n" +
                "1. Ring topology\n2. Clique topology\n3. Torus topology\n4. Claster topology\n");
        this.topologyType = scanner.nextInt();
        if(this.topologyType == Topology.TORUS){
            System.out.print("For this topology you have to insert the size of the torus\n");
            this.torusSize = scanner.nextInt();
            while(this.agentsCount % this.torusSize != 0) {
                System.out.print("Agents amount = " + this.agentsCount + " cannot be divided by the size of torus = "
                        + this.torusSize + "\n");
                this.torusSize = scanner.nextInt();
            }
        }
        if(this.topologyType == Topology.CLASTER){
            System.out.print("For this topology you have to insert the amount of cliques in the neighbourhood graph\n");
            this.cliquesCount = scanner.nextInt();
            while(this.agentsCount % this.cliquesCount != 0 || this.agentsCount / this.cliquesCount < this.cliquesCount - 1) {
                System.out.print("Agents amount = " + this.agentsCount +
                        " cannot be divided by the number of cliques = " + this.cliquesCount + "\n");
                this.cliquesCount = scanner.nextInt();
            }
        }
        System.out.print("Insert maximum number of algorithm iterations\n" + "Maximum number of iterations: ");
        this.maximumIterationsNumber = scanner.nextInt();
        System.out.print("Insert needed number of iterations while stagnation of the process\n" + "Stagnation limit: ");
        this.stagnationLimit = scanner.nextInt();
        System.out.print("Insert number of algorithm starts\n" + "Multi start executions number: ");
        this.multiStartNumber = scanner.nextInt();
    }
}
