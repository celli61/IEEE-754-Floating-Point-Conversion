import java.math.BigDecimal;
import java.math.BigInteger;

public class FloatToIEEE754 {

    public static String convertFloatToIEEE754(String floatString) {
        //handle special case of "0.0"
        if (new BigDecimal(floatString).compareTo(BigDecimal.ZERO) == 0) return "0 00000000 00000000000000000000000";
        
        //get whole portion of the float as a BigInteger 
        BigInteger wholePart = BigInteger.ZERO;
        if(floatString.contains(".")){
            wholePart = new BigInteger(floatString.substring(0, floatString.indexOf('.')));
        } else {
            wholePart = new BigInteger(floatString);
        }
        //get decimal portion of the float as a BigDecimal to prevent floating point precision issues
        BigDecimal decimalPart = new BigDecimal(floatString).remainder(BigDecimal.ONE);

        //store whole part binary string from BigInteger toString
        String wholePartBinary = wholePart.toString(2);

        //build decimal part binary string 
        StringBuilder decimalPartBinaryBuilder = new StringBuilder();
        //iterate 25 times to allow extra bits for rounding if applicable
        for(int i = 0; i < 30; i++) {
            //if the decimal part is 0, break
            if (decimalPart.equals(BigDecimal.ZERO)) break; 
            //multiply decimal value by 2
            decimalPart = decimalPart.multiply(BigDecimal.valueOf(2)); 
            //append the whole part of the product to the string builder
            decimalPartBinaryBuilder.append(Math.abs(decimalPart.intValue()));
            //set decimal part value to decimal part of product
            decimalPart = decimalPart.remainder(BigDecimal.ONE);
        }
        //store built decimal part binary string
        String decimalPartBinary =  decimalPartBinaryBuilder.toString();
        
        //append whole and decimal part binary strings together
        String wholePlusDecimalBinary = wholePartBinary + decimalPartBinary;

        //determine exponent size
        int exponentValue;
        if(!wholePart.equals(BigInteger.ZERO)) {
            //set value to length of whole part binary - the index of the first "1" - 1
            exponentValue = wholePartBinary.length() - wholePartBinary.indexOf('1') - 1;
        } else {
            //if there is no "1" in the decimal part
            if(decimalPartBinary.indexOf('1') == -1) {
                //input is 0
                exponentValue = 0;
            } else {
                //exponent is negative based off index of first "1"
                exponentValue = -(decimalPartBinary.indexOf('1') + 1);
            }
        }
        System.out.println(exponentValue);
        //store mantissa bits in a new string
        String mantissaBinary;
        //if the whole part of the float is not 0
        if (!wholePart.equals(BigInteger.ZERO)) {
            //remove leading 1 
            mantissaBinary = wholePlusDecimalBinary.substring(1); 
        } else {
            mantissaBinary = decimalPartBinary.substring(decimalPartBinary.indexOf("1") + 1);
        }

        //normalize mantissa bit string length to 23 by rounding or padding with 0s
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

        //create string builder for final binary string
        StringBuilder IEEE754BinaryBuilder = new StringBuilder();

        //assign sign bit based off stored whole number of input
        if(wholePart.compareTo(BigInteger.ZERO) < 0) {
            IEEE754BinaryBuilder.append("1");
        } else {
            IEEE754BinaryBuilder.append("0");
        }

        //add a space to final string to show different parts of binary representation
        IEEE754BinaryBuilder.append(" ");

        //calculate the binary string for the exponent bits and append to final binary string
        String exponentBinary = String.format("%8s", Integer.toBinaryString(exponentValue + 127)).replace(' ', '0');

        IEEE754BinaryBuilder.append(exponentBinary);

        IEEE754BinaryBuilder.append(" ");

        //finally, append the mantissa
        IEEE754BinaryBuilder.append(mantissaBinary);
        
        return IEEE754BinaryBuilder.toString();
    }
}
