package swarmAlgorithm.topology;

public class Factorial {
    public int factorial(int n){
        if(n < 0)
            n = -n;
        if(n == 0)
            return 0;
        if(n == 1)
            return 1;
        int result = 1;
        for(int i = 2; i <= n; i++)
            result *= i;
        return result;
    }
}
