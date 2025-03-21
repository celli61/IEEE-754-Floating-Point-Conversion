import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class FloatToIEEE754 {
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
        int wholePart = Integer.parseInt(floatPointString.substring(0, floatPointString.indexOf('.')));
        System.out.println("Whole number portion of input: "+ wholePart);
        //convert the whole number integer to a binary string through Java 
        String wholeNumBinary = Integer.toBinaryString(Math.abs(wholePart));
        System.out.println("Whole number portion of input in binary: " + wholeNumBinary);

        //get decimal portion of the float as a BigDecimal to prevent floating point precision issues
        BigDecimal decimalPart = new BigDecimal(floatPointString).remainder(BigDecimal.ONE);
        System.out.println("Decimal portion of input: " + decimalPart);


        //convert from decimal form to binary string with a for loop and a string builder
        StringBuilder decimalNumBinaryBuilder = new StringBuilder();


        //use a for loop to loop a maximum of 23 times to generate the binary string for the decimal input
        for(int i = 0; i < 23; i++) {
            if (decimalPart.compareTo(BigDecimal.ZERO) == 0) break; 
            decimalPart = decimalPart.multiply(BigDecimal.valueOf(2)); 
            int bit = Math.abs(decimalPart.intValue());
            decimalNumBinaryBuilder.append(bit);
            decimalPart = decimalPart.remainder(BigDecimal.ONE);
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
        String mantissaBinary;
        if (wholePart != 0) {
            mantissaBinary = wholeAndDecimalNumBinary.substring(1); // Remove leading 1
        } else {
            mantissaBinary = decimalNumBinary.substring(decimalNumBinary.indexOf("1") + 1);
        }

        //normalize length of mantissa to 23 floating-point rounding (down) with or padding with 0s (up)
        if (mantissaBinary.length() > 23) {
            //24th bit is guard bit
            if (mantissaBinary.charAt(23) == '1') {
                //if 1, round to nearest
                //convert binary string to int with BigInteger
                BigInteger mantissaInt = new BigInteger(mantissaBinary.substring(0, 23), 2);
                //increment int by 1
                mantissaInt = mantissaInt.add(BigInteger.ONE);
                //store int as binary string again with BigInt toString, replacing spaces with 0s
                mantissaBinary = String.format("%23s", mantissaInt.toString(2)).replace(' ', '0');
            } else {
                //if 0, simply truncate
                mantissaBinary = mantissaBinary.substring(0, 23);
            }
        } else {
            //if less than 23 bits, pad with 0s
            mantissaBinary = mantissaBinary + "0".repeat(23 - mantissaBinary.length());
        }
        System.out.println("Binary string for mantissa: " + mantissaBinary);


        //create string builder for final binary string
        StringBuilder IEEE754BinaryBuilder = new StringBuilder();

        //assign sign bit based off stored whole number of input
        if(wholePart < 0) {
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
