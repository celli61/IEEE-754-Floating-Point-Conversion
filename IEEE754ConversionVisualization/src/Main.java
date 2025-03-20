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
        System.out.println("Decimal portion of input in binary: " + decimalNumBinary);


        //combine whole number binary string and decimal binary string into 1 string 
        String wholeAndDecimalNumBinary = wholeNumBinary+decimalNumBinary;
        System.out.println("Whole number and decimal portion of input in binary: " + wholeAndDecimalNumBinary);


        /*determine exponent size by subtracting the length of the substring from 0 
        * to the first occurrence of "1" from the length of the entire whole num 
        * binary string */
        int exponentValue = wholeNumBinary.length() - wholeNumBinary.substring(0, wholeNumBinary.indexOf('1') + 1).length();
        System.out.println("Calculated value of the exponent: " + exponentValue);


        /*get final binary string for mantissa by taking the substring of the 
        * combined whole and decimal binary strings after the first occurrence of 1 to the end*/
        String mantissaBinary = wholeAndDecimalNumBinary.substring(wholeNumBinary.indexOf('1') + 1);
        //check length of binary string and increase/decrease to 23
        if(mantissaBinary.length() < 23) {
            mantissaBinary = mantissaBinary + "0".repeat(23 - mantissaBinary.length());
        } else {
            mantissaBinary = mantissaBinary.substring(0, 23);
        }
        System.out.println("Binary string for mantissa: " + mantissaBinary);
        System.out.println("Binary string for mantissa length: " + mantissaBinary.length());


        //create string builder for final binary string
        StringBuilder IEEE754BinaryBuilder = new StringBuilder();

        //assign sign bit based off stored whole number of input
        if(wholeNum < 0) {
            IEEE754BinaryBuilder.append("1");
        } else {
            IEEE754BinaryBuilder.append("0");
        }

        //add a space to final string to show different parts of binary representation
        IEEE754BinaryBuilder.append(" ");

        //calculate the binary string for the exponent bits and append to final binary string
        String exponentBinary = Integer.toBinaryString(exponentValue + 127);
        System.out.println("Binary string for final exponent value: " + exponentBinary);
        IEEE754BinaryBuilder.append(exponentBinary);

        IEEE754BinaryBuilder.append(" ");

        //finally, append the mantissa
        IEEE754BinaryBuilder.append(mantissaBinary);

        String IEEE754Binary = IEEE754BinaryBuilder.toString();
        System.out.println(IEEE754Binary);

    }   
}
