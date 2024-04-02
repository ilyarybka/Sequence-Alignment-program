/* Iliya Klishin
 * Dr. Steinberg
 * COP3503 Summer 2022
 * Programming Assignment 4
 */
 
 import java.util.HashSet;
 
 public class SequenceAlignment
 {
 
 private HashSet<Character> hsv;
 private HashSet<Character> hsc;
 private String x;
 private String y;
 private int[][] numarr;
 private Character[][] chr;
 private int p;
 private String xp;
 private String yp;
 
   public SequenceAlignment(String x, String y)
   {
     this.x = "-" + x;
     this.y = "-" + y;
     this.p = 2;
     this.hsv = new HashSet<Character>(5);//Hashset for the vowels
     this.hsc = new HashSet<Character>(21);//Hashset for the consonants
     this.xp = "";
     this.yp = "";
     
     hsv.add('a'); hsv.add('e'); hsv.add('i'); hsv.add('o'); hsv.add('u');// Filling in the hashset with vowels
     
     hsc.add('b'); hsc.add('c'); hsc.add('d'); hsc.add('f'); hsc.add('g'); hsc.add('h'); hsc.add('j'); hsc.add('k'); hsc.add('l');// Filling in hashset
     hsc.add('m'); hsc.add('n'); hsc.add('p'); hsc.add('q'); hsc.add('r'); hsc.add('s'); hsc.add('t'); hsc.add('v'); hsc.add('w');// with consonants
     hsc.add('x'); hsc.add('y'); hsc.add('z');
     
   }
   
   public int min(int a, int b, int c, int i, int j)// This method chooses the minimum integer values from a, b, and c and
   {                                                // this method updates the character array with directions after choosing the minimum value
     int minimum;                                   // a for diagonal, or 'd'; b for upward, or 'u', and c for horizontal, or 'h'
     
     if(b >= a && c >= a)
     {
       minimum = a;
       chr[i][j] = 'd'; // if a is a minimum, update char array with 'd' at index [i][j]
     }
     else if(a >= b && c >= b)
     {
       if(a == b)
       {
         minimum = a;
         chr[i][j] = 'd'; // if a and b are equal, picking a for the minimum and updating char array with 'd'
       }
         
       minimum = b;
       chr[i][j] = 'u';// if the minimum value is b, update char array with 'u'
       }
       
     else
     {
       if(a == c)
       {
         minimum = a;// if a and c are equal, picking a as minimum and updating char array with 'd'
         chr[i][j] = 'd';
       }
       else if(b == c)
       {
         minimum = b;// if b and c are equal, picking b as minimum, and updating char array with 'u'
         chr[i][j] = 'u';
       }
       minimum = c;
       chr[i][j] = 'h';// if c is a minimum value, updating char array with 'h'.
     }
       
     return minimum;
   }
   
   
   public void computeAlignment(int p)// This method finds the sequence alignment and updates num and char arrays for x and y strings.
   {
     int xl = x.length();
     int yl = y.length();
     
     this.p = p;
     this.numarr = new int[x.length()][y.length()];// intializing numarray
     this.chr = new Character[x.length()][y.length()];// initializing character array
     
     numarr[0][0] = 0;
     chr[0][0] = '0';
     
     for(int i = 1; i < xl; i++)
     {
       numarr[i][0] = numarr[i - 1][0] + p;// filling in first column in numarray with gap penalty values
       chr[i][0] = 'u';                    // and character array with 'u' directions
     }
     
     for(int j = 1; j < yl; j++)
     {
       numarr[0][j] = numarr[0][j - 1] + p;// filling in first row with gap values in numarray
       chr[0][j] = 'h';                    // and char array with appropriate direction 'h'.
     }
     
     
     for(int i = 1; i < xl; i++)
     {
       for(int j = 1; j < yl; j++)
       {
         if(x.charAt(i) == y.charAt(j))// if letters in x and y str. are equal, mismatch penalty is 0
         {
           numarr[i][j] = min(0 + numarr[i - 1][j - 1], p + numarr[i - 1][j], p + numarr[i][j - 1], i, j); //Calculating penalties for mismatch in x and y strings
           // in three directions: diagonal, upward, horizontal, and picking the minimum.
         }
         
         else if((x.charAt(i) != y.charAt(j)) && hsv.contains(x.charAt(i)) && hsv.contains(y.charAt(j)))//if letters in x and y strings are not equal, but
         
                     //those 2 letters are both vowels, mismatch penalty is 1
         {
           numarr[i][j] = min(1 + numarr[i - 1][j - 1], p + numarr[i - 1][j], p + numarr[i][j - 1], i, j);// Calculating minimum mismatch.
         }
         
         else if((x.charAt(i) != y.charAt(j)) && hsc.contains(x.charAt(i)) && hsc.contains(y.charAt(j)))//if lett. in x and y str. are not equal, but they are
         //consonants, mismatch penalty is 1
         {
           numarr[i][j] = min(1 + numarr[i - 1][j - 1], p + numarr[i - 1][j], p + numarr[i][j - 1], i, j);
         }
         
         else // finally, if letters in x and y are not equal, and one of them is vowel and the other is consonant, mismatch penalty is 3.
         {
           numarr[i][j] = min(3 + numarr[i - 1][j - 1], p + numarr[i - 1][j], p + numarr[i][j - 1], i, j);
         }
       }
     }
   
   }
   
   public void printAlignment(int i, int j)// this method helps to print the correct sequence alignment for x and y strings.
   {
     if(i == 0 || j == 0)
       return;
     
     if(chr[i][j] == 'd')// if the direction is 'd' in character array at index [i][j], printing letters for x and y strings.
     {
       xp = x.charAt(i) + xp;
       yp = y.charAt(j) + yp;
       printAlignment(i - 1, j - 1);
       
     }
     
     else if(chr[i][j] == 'u')// if the direction is 'u', skipping letter for y string and priting '-' and priting letter in x string
     {
       xp = x.charAt(i) + xp;
       yp = '-' + yp;
       printAlignment(i - 1, j);
     }
     
     else// if the direction is 'h', printing '-' for x string and priting letter in the y string
     {
       xp = '-' + xp;
       yp = y.charAt(j) + yp;
       printAlignment(i, j - 1);
     }
     
     
   }
   
   public String getAlignment()// This method calls printAlignment and returns sequence alignment for x and y string by separating them with space char ' '.
   {
     int i = x.length() - 1;
     int j = y.length() - 1;
     
     printAlignment(i, j);
     
     System.out.println(xp);
     System.out.println(yp);
     
     return (xp + " " + yp);
   }
 
 }