public class MathProcess {
    private double val1;
    private double val2;
    private double result;

    // Empty MathProcess constructor
    public MathProcess(){
        val1 = 0;
        val2 = 0;
        result = 0;
    }

    // Constructor for double value processes
    public MathProcess(double v1, double v2){
        val1 = v1;
        val2 = v2;
        result = 0;
    }

    // Constructor for single value processes
    public MathProcess(double v1){
        val1 = v1;
        result = 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public double getVal1(){
        return val1;
    }

    public double getVal2(){
        return val2;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Single value methods
    public double squareRoot(){
        result = Math.sqrt(val1);
        return result;
    }

    public double powerTwo(){
        result = Math.pow(val1, 2);
        return result;
    }

    public double powerThree(){
        result = Math.pow(val1, 3);
        return result;
    }

    public double sin(){
        result = Math.sin(val1);
        return result;
    }

    public double cos(){
        result = Math.cos(val1);
        return result;
    }

    public double tan(){
        result = Math.tan(val1);
        return result;
    }

    public double log(){
        result = Math.log10(val1);
        return result;
    }

    public double ln(){
        result = Math.log(val1);
        return result;
    }

    public double factorial(){
        int times;
        result = 1;
        for(times = 2; times <= val1; times++){
            result *= times;
        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Double value methods
    public double plus(){
        result = val1 + val2;
        return result;
    }

    public double minus(){
        result = val1 - val2;
        return result;
    }

    public double mult(){
        result = val1 * val2;
        return result;
    }

    public double division(){
        result = val1 / val2;
        return result;
    }

    public double modular(){
        result = val1 % val2;
        return result;
    }

    public double powerY(){
        result = Math.pow(val1, val2);
        return result;
    }
}
