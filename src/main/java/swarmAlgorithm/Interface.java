package swarmAlgorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Interface {
    public static void main(String[] args) throws Exception{
        FreeParameters freeParameters = new FreeParameters();
        double startTime = System.nanoTime();
        MultiStart multiStart = new MultiStart(freeParameters);
        multiStart.multiStartRun();
        double executionTime = System.nanoTime() - startTime;
        DecimalFormat df = new DecimalFormat("#.##########");
        df.setRoundingMode(RoundingMode.CEILING);
        System.out.print("Minimum of the inserted function is " + df.format(multiStart.finalResult) + "\n");
        System.out.print("The exact value of function minimum is " + multiStart.finalResult + "\n");
        System.out.print("Execution time: " + (int)((executionTime / 600000000)/100) +  " min " +
                (int)(executionTime / 1000000000) + " sec " + (int)((executionTime % 1000000 - executionTime % 1000)/1000) + " ms " + "\n");
    }
}