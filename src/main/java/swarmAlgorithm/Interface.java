package swarmAlgorithm;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Interface {
    public static void main(String[] args) throws Exception{
        FreeParameters freeParameters = new FreeParameters();
        MultiStart multiStart = new MultiStart(freeParameters);
        multiStart.multiStartRun();
        DecimalFormat df = new DecimalFormat("#.##########");
        df.setRoundingMode(RoundingMode.CEILING);
        System.out.print("Minimum of the inserted function is " + df.format(multiStart.finalResult));
    }
}