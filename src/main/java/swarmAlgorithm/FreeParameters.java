package swarmAlgorithm;

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
        topologyType = scanner.nextInt();
        System.out.print("Insert maximum number of algorithm iterations\n" + "Maximum number of iterations: ");
        this.maximumIterationsNumber = scanner.nextInt();
        System.out.print("Insert needed number of iterations while stagnation of the process\n" + "Stagnation limit: ");
        this.stagnationLimit = scanner.nextInt();
        System.out.print("Insert number of algorithm starts\n" + "Multi start executions number: ");
        this.multiStartNumber = scanner.nextInt();
    }
}
