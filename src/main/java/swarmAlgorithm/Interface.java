package swarmAlgorithm;

public class Interface {
    public static void main(String[] args) throws Exception{
        FreeParameters freeParameters = new FreeParameters();
        MultiStart multiStart = new MultiStart(freeParameters);
        System.out.print("Minimum of the inserted function is " + multiStart.finalResult);
    }
}
