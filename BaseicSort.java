// Another google engineer is solving strange problems again.
// They need a program that will take a number in hex and convert
// this number to all bases from 16 to 2 inclusive and append to the end of
// hex value.
//
// This program would be pretty easy to add parameters to change the range
// of bases we want to convert to. Because there's no standard number system
// that I know of past hexadecimal, I made it based off of the NUMBER_KEY length!
//
// It will then count the individual digits inside the String and print out
// all values in order from most common to least common.
// If the two digits occur with same freq. the higher value has priority.
import java.util.Arrays;
public class BaseicSort {
   // used to cross reference hex values to array index!
   public static final String NUMBER_KEY = "0123456789abcdef";
   public static void main(String[] args) {
      testCase(0x64);
      testCase(0x12345);
   }
   
   // Tests the program using given number.
   // Base used is assumed to be the length of the constant NUMBER_KEY
   // cannot change base for different cases unless code is rewritten.
   //
   // example: passed  0x64
   //          prints the following
   //
   // "      expanded: 646a727984911001211442022444001210102011100100
   //  frequency count: [12, 12, 7, 0, 7, 0, 2, 2, 1, 2, 1, 0, 0, 0, 0, 0]
   //           output: 1042976a8"
   //
   // PARAMETERS
   // testVal: integer of value to be converted and counted.
   //
   // RETURN
   // none.
   public static void testCase(int testVal) {
      // expand string to all bases
      String expanded = printAllBase(testVal);
      System.out.println("       expanded: " + expanded);
      // count digits in String
      int[] freq = countDigits(expanded);
      System.out.println("frequency count: " + Arrays.toString(freq));
      // print out answer
      String answer = orderDigits(freq);
      System.out.println("         output: " + answer);
      System.out.println();
   }
   
   // Takes an array of digit counts and length of given String
   // returns a String of the digits in order from highest to lowest frequency
   // If two digits have the same frequency then higher digits are
   // given priority. Does not print any digits with 0 frequency.
   //
   // complexity: n + n * m where
   //    n is largest base used (16 if we use hexadecimal)
   //    m is the length of the string.
   // This would probably be more intuitive if frequency count was passed
   // as it only requires the array, but the complexity would be roughly the
   // same (n^2) and has much more difficulty with prioritizing larger values.
   //
   // example: passed  [1 2 1 2 6 3 1] (base 6)
   //          returns "4531520"
   // PARAMETERS
   // freq: array of digit counts
   //
   // RETURN
   // output String of digits in order of most common to least common.
   public static String orderDigits(int[] freq) {
      String output = "";
      // iterate through indeces of freq
      for (int count = countLength(freq); count > 0; count--) {
         // iterate through all values of 
         for (int i = freq.length - 1; i >= 0; i--) {
            if (freq[i] == count) {
               output += NUMBER_KEY.charAt(i);
               freq[i] = 0;
            }
         }
      }
      return output;
   }
   
   // iterates through the array and counts all values in the array.
   // returns the count.
   //
   // example: passed  [2 1 3 4 5 2 1 1 1 1 3]
   //          returns 24
   //
   // PARAMETERS
   // arr: array of digit counts
   //
   // RETURN
   // integer sum of all values contained in arr.
   private static int countLength(int[] arr) {
      int total = 0;
      for (int count : arr)
         total += count;
      return total;
   }
   
   // Accepts a String of numbers and returns an array of digit counts
   // currently only works if your numbering system uses 0-9 then a-z.
   // returns an array of counts of digits. Array size is equal to base used.
   //
   // example: passed "1432532" with assumed base of 10
   //          returns [0 1 2 2 1 1 0 0 0 0] (counts of digits)
   //
   // PARAMETERS
   // number: String of consecutive digits of all bases.
   //
   // RETURN
   // array of digit counts.
   public static int[] countDigits(String number) {
      int[] freq = new int[NUMBER_KEY.length()];
      for (char digit : number.toCharArray()) {
         if (digit >= '0' && digit <= '9')
            // digit is a number
            freq[digit - '0']++;
         else
            // digit is a letter
            freq[digit - 'a' + 10]++;
      }
      return freq;
   }
   
   // accepts a digit in given base and converts it to all bases below it.
   // returns a large String of digits of all bases consecutively.
   //
   // example: passed 0x64    base: 16
   //          converts number to all bases from 16 to 2 inclusive.
   //          returns "646a727984911001211442022444001210102011100100"
   //
   // PARAMETERS
   // val: integer value of number
   //
   //
   // RETURN
   // String of consecutive digits.
   public static String printAllBase(int val) {
      String output = "";
      int reducedVal = val;
      // iterate through all bases down to 2.
      for (int i = NUMBER_KEY.length(); i >= 2; i--) {
         // build string for current base
         String currentBase = "";
         while (reducedVal != 0) {
            currentBase += NUMBER_KEY.charAt(reducedVal % i);
            reducedVal /= i;
         }
         // reverse string and append to output; reset the reduced value
         output += reverseString(currentBase);
         reducedVal = val;
      }
      return output;
   }
   
   // iterates through a string and reverses it.
   // returns the reversed string.
   // 
   // example: Passed: "hello"
   //          returns "olleh"
   //
   // PARAMETERS
   // original: original String to be reversed
   //
   // RETURN
   // reversed String
   private static String reverseString(String original) {
      String output = "";
      for (int i = original.length() - 1; i >= 0; i--)
         output += original.charAt(i);
      return output;
   }
}
