package swarmAlgorithm;

public class Swarm {
    public Agent[] particles;
    public int agentsCount;

    //constructor initializes the swarm of randomly placed particles
    public Swarm(FreeParameters freeParameters) throws Exception {
        this.agentsCount = freeParameters.agentsCount;
        this.particles = new Agent[agentsCount];
        for(int i = 0; i < agentsCount; i++){
            long seed = System.currentTimeMillis();
            this.particles[i] = new Agent(seed, i, freeParameters);
            this.particles[i].setNeighbours(agentsCount);
        }
    }

    //this method updates bestNeighbourPosition and ...Value of each agent after nextPosition() was called for all the particles
    public void swarmLocalGuideNextPosition() {
        for(int i = 0; i < this.agentsCount; i++){
            for(int j = 0; j < this.particles[i].neighbours.size(); j++){
                Agent neighbour = this.particles[this.particles[i].neighbours.get(j)];
                if(neighbour.getFitnessFunctionValue(neighbour.currentPosition) < this.particles[i].bestNeighbourFitnessFunctionValue){
                    this.particles[i].bestNeighbourFitnessFunctionValue = neighbour.getFitnessFunctionValue(neighbour.currentPosition);
                    this.particles[i].bestNeighbourPosition = neighbour.currentPosition;
                }
            }
        }
    }
    //this method updates all dynamic fields of every particle (such as currentPosition, velocity, etc)
    public void nextIteration(FreeParameters freeParameters) throws Exception{
        for(int i = 0; i < this.agentsCount; i++){
            particles[i].nextPosition(freeParameters);
            particles[i].privateGuideNextPosition();
        }
        this.swarmLocalGuideNextPosition();
    }
}
