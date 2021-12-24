package swarmAlgorithm;

import java.util.Arrays;

public class Vector {
    public int dimension;
    public double[] coordinates;

    public Vector(int dimension){
        this.dimension = dimension;
        this.coordinates = new double[dimension];
    }
    public Vector(double[] coordinates){
        this.coordinates = coordinates;
        this.dimension = coordinates.length;
    }

    public Vector sum(Vector operand) throws Exception{
        if(this.dimension != operand.dimension)
            throw new Exception("Vectors cannot be added: difference in operands' dimensions\n");
        double[] coordinatesSum = new double[this.dimension];
        for(int i = 0; i < this.dimension; i++)
            coordinatesSum[i] = this.coordinates[i] + operand.coordinates[i];
        return new Vector(coordinatesSum);
    }
    public Vector scalarMultiplication(double scalar){
        double[] newCoordinates = new double[this.dimension];
        for(int i = 0; i < this.dimension; i++)
            newCoordinates[i] = this.coordinates[i] * scalar;
        return new Vector(newCoordinates);
    }
    public double scalarProduct(Vector operand) throws Exception{
        if(this.dimension != operand.dimension)
            throw new Exception("Scalar product cannot be calculated: difference in operands' dimensions\n");
        double result = 0;
        for(int i =  0; i < this.dimension; i++)
            result += this.coordinates[i] * operand.coordinates[i];
        return result;
    }
    //this method returns the number of coordinate if tha argument is in vector's coordinates and -1 otherwise
    public int isIncluded(double number){
        for(int i = 0; i < this.dimension; i++)
            if(this.coordinates[i] == number)
                return i;
        return -1;
    }

    public String toString(){
        String result = "";
        for(int i = 0; i < this.dimension; i++){
            result = result.concat(Double.toString(this.coordinates[i]));
            result = result.concat(" ");
        }
        result = result.substring(0, result.length() - 1);
        result = result.concat("\n");
        return result;
    }
    private boolean equals(Vector other){
        return Arrays.equals(this.coordinates, other.coordinates);
    }
    @Override public boolean equals(Object other){
        if(other instanceof Vector)
            return this.equals((Vector)other);
        return false;
    }
    public int hashCode(){
        int result = 0;
        result += Arrays.hashCode(this.coordinates);
        result *= 29;
        result += this.dimension;
        return result;
    }
}