import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        boolean inputValid = false;
        String floatPointString = "";
        while(!inputValid){
            System.out.println("Enter a float value: "); 
            //read in as a string
            floatPointString = scan.nextLine();
            try {
                //will throw expection if not a valid float
                new BigDecimal(floatPointString);
                System.out.println(floatPointString + " entered. Converting and visualizing...");
                inputValid = true; 
            } catch (NumberFormatException e) {
                System.out.println("Error! Please enter a valid floating point number");
            }
        }
        
        //get whole number portion of the float by indexing string and typecasting (all numbers before "." as an int)
        int wholeNum = Integer.parseInt(floatPointString.substring(0, floatPointString.indexOf('.')));
        System.out.println("Whole number portion of input: "+ wholeNum);
        //convert the whole number integer to a binary string through Java 
        String wholeNumBinary = Integer.toBinaryString(wholeNum);
        System.out.println("Whole number portion of input in binary: " + wholeNumBinary);

        //get decimal portion of the float as a double using substring and logical indexing with typecasting 
        String decimalNum = floatPointString.substring(floatPointString.indexOf('.') + 1, floatPointString.length());
        //convert from string to decimal form by casting to an int and shifting decimal place with multiplication
        double decimalNumConvert = (double) ((Integer.parseInt(decimalNum)) * Math.pow(10, -(decimalNum.length())));
        System.out.println("Decimal portion of input: " + decimalNumConvert);

        //convert from decimal form to binary string with a for loop and a string builder
        StringBuilder decimalNumBinaryBuilder = new StringBuilder();

        //use a for loop to loop a maximum of 23 times to generate the binary string for the decimal input
        for(int i = 0; i < 23; i++) {
            //if decimal number is 0.0, break loop
            if(decimalNumConvert == 0.0) break;
            //multiply decimal value by 2 and get leading digit
            decimalNumConvert *= 2;
            int leadingDigit = (int)(decimalNumConvert);
            decimalNumBinaryBuilder.append(leadingDigit);
            decimalNumConvert = decimalNumConvert % 1;
        }

        String decimalNumBinary = decimalNumBinaryBuilder.toString();
        System.err.println("Decimal portion of input in binary: " + decimalNumBinary);

        //combine whole number binary string and decimal binary string into 1 string 
        String wholeAndDecimalNumBinary = wholeNumBinary+decimalNumBinary;
        System.err.println("Whole number and decimal portion of input in binary: " + wholeAndDecimalNumBinary);
    }   
}
