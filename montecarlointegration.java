package test;

/**
 *
 * @author harryxu
 * Created on 0916
 *
 * The methods of this class estimates the value of pi by using a Monte Carlo simulation.
 * It creates a grid of size a by size a and encapsulates a circle within
 * the square. The methods calculates random numbers using the Math class's
 * random method. Then, the method calculates the area ratio of the circle
 * and the square to extrapolate PI.
 *
 * First, the Java Math class's random method is random up to
 * decimal places of millionths. This accuracy is calculated by
 * randomly finding random numbers and the frequency of which
 * the numbers are chosen. The results were plotted
 * on a graph to visually determine if the shape of the frequencies
 * was flat like a line, which indicates true randomness, or curved,
 * which indicates a biased randomness.
 *
 * The six methods are two sets of three methods which included have a small difference of
 * a boundary condition of whether or not values on the circle itself counts
 * as in the circle, outside of the circle, or both inside or outside depending
 * on where the last value on the circle has been included. The difference in the
 * two sets are that one set uses **integers** for the calculation that determines whether a
 * number is within the circle while the other uses **doubles** for the calculation.
 *
 * 100,000,000 runs, represented by the variable n in the methods, yields consistent results
 *
 */

public class montecarlointegration {

   /**
    * Prints results for 7 iterations of pi estimations
    * with each iteration having 6 values
    * the first row has values on the circle counted as outside for
    * integers and doubles along with appropriate error calculations
    * the second row has values on the circle counted as inside for
    * integers and doubles along with appropriate error calculations
    * the third row has values on the circle counted as both inside and outside for
    * integers and doubles along with appropriate error calculations
    */
   public static void main(String[] args)
   {
      for(int i=2;i<9;i++)
      {
         double temp1=mci1i(i*1000,100000000);
         double temp2=mci1d(i*10000,100000000);
         double temp3=mci2i(i*1000,100000000);
         double temp4=mci2d(i*10000,100000000);
         double temp5=mci3i(i*1000,100000000);
         double temp6=mci3d(i*10000,100000000);
      System.out.println("1: "+temp1+" : "+temp2+" : "
         +Math.abs((temp1-Math.PI)/Math.PI*100)+" : "+Math.abs((temp2-Math.PI)/Math.PI*100));
      System.out.println("2: "+temp3+" : "+temp4+" : "
         +Math.abs((temp3-Math.PI)/Math.PI*100)+" : "+Math.abs((temp4-Math.PI)/Math.PI*100));
      System.out.println("3: "+temp5+" : "+temp6+" : "
         +Math.abs((temp5-Math.PI)/Math.PI*100)+" : "+Math.abs((temp6-Math.PI)/Math.PI*100)+"...");
      }
   }

   /**
    * Uses a Monte Carlo approach to determine whether random values
    * are within a square or a circle encapsulated into the square.
    *
    * Within a loop with iterations n,
    * the method calculates a random number in the range of 0 to size*size, exclusive.
    * Next, the method calculates the distance of the number to center of the "grid"
    * *Note: there is no grid other than an imaginary grid with size rows and size columns
    * *Note: the row number is calculated by dividing the number by the size
    * *Note: and the column number is calculated by using the modulus operator on the number by the size
    * A series of if statements determine whether the number is within the radius
    * of the circle and appropriately tallies the corresponding value in or out
    * Lastly, the method equates the ratio of 4/PI with (in+out)/in in order to calculate PI
    *
    * This method specifically counts values on the circle as *outside*
    * of the circle.
    * This method also uses *integers* instead of *doubles*
    *
    * @param the size of the edge grid should be odd for symmetric arrangement
    * of the circle
    * @param the number of tries the method should run through
    * @return the estimated value of pi
    */
   public static double mci1i(int size, int n)
   {
      int in=0;
      int out=0;
      int ran=0;
      int rand=0;

      for(int m=n;m>0;m--)
      {
         //this loop calculates random values for the Monte Carlo approach

         ran=(int)(Math.random()*size*size);
         //finds the distance of the point compared to a circle with center size/2,size/2
         rand=(int)(Math.sqrt((ran%size-size/2)*(ran%size-size/2)
               +(ran/size-size/2)*(ran/size-size/2)));
         //if the distance between the point and the center of the circle
         //is less than the radius of the circle then the point is counted as inside
         if(rand<size/2)
         {
            in++;
         }
         else
         {
            out++;
         }
      }

      return 4.0*in/(in+out);
   }


   /**
    * Uses a Monte Carlo approach to determine whether random values
    * are within a square or a circle encapsulated into the square.
    *
    * Within a loop with iterations n,
    * the method calculates a random number in the range of 0 to size*size, exclusive.
    * Next, the method calculates the distance of the number to center of the "grid"
    * *Note: there is no grid other than an imaginary grid with size rows and size columns
    * *Note: the row number is calculated by dividing the number by the size
    * *Note: and the column number is calculated by using the modulus operator on the number by the size
    * A series of if statements determine whether the number is within the radius
    * of the circle and appropriately tallies the corresponding value in or out
    * Lastly, the method equates the ratio of 4/PI with (in+out)/in in order to calculate PI
    *
    * This method specifically counts values on the circle as *inside*
    * of the circle.
    * This method also uses *integers* instead of *doubles*
    *
    * @param the size of the edge grid should be odd for symmetric arrangement
    * of the circle
    * @param the number of tries the method should run through
    * @return the estimated value of pi
    */
   public static double mci2i(int size, int n)
   {
      int in=0;
      int out=0;
      int ran=0;
      int rand=0;

      for(int m=n;m>0;m--)
      {
         //this loop calculates random values for the Monte Carlo approach

         ran=(int)(Math.random()*size*size);
         //finds the distance of the point compared to a circle with center size/2,size/2
         rand=(int)(Math.sqrt((ran%size-size/2)*(ran%size-size/2)
               +(ran/size-size/2)*(ran/size-size/2)));
         //if the distance between the point and the center of the circle
         //is less than the radius of the circle then the point is counted as inside
         if(rand>size/2)
         {
            out++;
         }
         else
         {
            in++;
         }
      }

      return 4.0*in/(in+out);
   }

   /**
    * Uses a Monte Carlo approach to determine whether random values
    * are within a square or a circle encapsulated into the square.
    *
    * Within a loop with iterations n,
    * the method calculates a random number in the range of 0 to size*size, exclusive.
    * Next, the method calculates the distance of the number to center of the "grid"
    * *Note: there is no grid other than an imaginary grid with size rows and size columns
    * *Note: the row number is calculated by dividing the number by the size
    * *Note: and the column number is calculated by using the modulus operator on the number by the size
    * A series of if statements determine whether the number is within the radius
    * of the circle and appropriately tallies the corresponding value in or out
    * Lastly, the method equates the ratio of 4/PI with (in+out)/in in order to calculate PI
    *
    * This method specifically counts values on the circle as *inside/outside depending on a value*
    * of the circle.
    * The first value on the circle is added in, the next is added out, the next is added in, etc.
    * This method also uses *integers* instead of *doubles*
    *
    * @param the size of the edge grid should be odd for symmetric arrangement
    * of the circle
    * @param the number of tries the method should run through
    * @return the estimated value of pi
    */
   public static double mci3i(int size, int n)
   {
      int in=0;
      int out=0;
      int ran=0;
      int rand=0;
      //if ranb is false the value is not added outside
      boolean ranb=false;

      for(int m=n;m>0;m--)
      {
         //this loop calculates random values for the Monte Carlo approach

         ran=(int)(Math.random()*size*size);
         //finds the distance of the point compared to a circle with center size/2,size/2
         rand=(int)(Math.sqrt((ran%size-size/2)*(ran%size-size/2)
               +(ran/size-size/2)*(ran/size-size/2)));
         //if the distance between the point and the center of the circle
         //is less than the radius of the circle then the point is counted as inside
         //yet if the value is on the line
         //ranb is called to alternate between counting in or out of the circle
         if(rand>size/2)
         {
            out++;
         }
         else if(rand==size/2&&ranb)
         {
            out++;
            ranb=false;
         }
         else if(rand==size/2&&!ranb)
         {
            in++;
            ranb=true;
         }
         else
         {
            in++;
         }
      }

      return 4.0*in/(in+out);
   }

   //second set of methods using doubles
   /**
    * Uses a Monte Carlo approach to determine whether random values
    * are within a square or a circle encapsulated into the square.
    *
    * Within a loop with iterations n,
    * the method calculates a random number in the range of 0 to size*size, exclusive.
    * Next, the method calculates the distance of the number to center of the "grid"
    * *Note: there is no grid other than an imaginary grid with size rows and size columns
    * *Note: the row number is calculated by dividing the number by the size
    * *Note: and the column number is calculated by using the modulus operator on the number by the size
    * A series of if statements determine whether the number is within the radius
    * of the circle and appropriately tallies the corresponding value in or out
    * Lastly, the method equates the ratio of 4/PI with (in+out)/in in order to calculate PI
    *
    * This method specifically counts values on the circle as *outside*
    * of the circle.
    * This method also uses *doubles* instead of *integers*
    *
    * @param the size of the edge grid should be odd for symmetric arrangement
    * of the circle
    * @param the number of tries the method should run through
    * @return the estimated value of pi
    */
   public static double mci1d(int size, int n)
   {
      int in=0;
      int out=0;
      double ran=0;
      double rand=0;

      for(int m=n;m>0;m--)
      {
         //this loop calculates random values for the Monte Carlo approach

         ran=(Math.random()*size*size);
         //finds the distance of the point compared to a circle with center size/2,size/2
         rand=(Math.sqrt((ran%size-size/2)*(ran%size-size/2)
               +(ran/size-size/2)*(ran/size-size/2)));
         //if the distance between the point and the center of the circle
         //is less than the radius of the circle then the point is counted as inside
         if(rand<size/2)
         {
            in++;
         }
         else
         {
            out++;
         }
      }

      return 4.0*in/(in+out);
   }


   /**
    * Uses a Monte Carlo approach to determine whether random values
    * are within a square or a circle encapsulated into the square.
    *
    * Within a loop with iterations n,
    * the method calculates a random number in the range of 0 to size*size, exclusive.
    * Next, the method calculates the distance of the number to center of the "grid"
    * *Note: there is no grid other than an imaginary grid with size rows and size columns
    * *Note: the row number is calculated by dividing the number by the size
    * *Note: and the column number is calculated by using the modulus operator on the number by the size
    * A series of if statements determine whether the number is within the radius
    * of the circle and appropriately tallies the corresponding value in or out
    * Lastly, the method equates the ratio of 4/PI with (in+out)/in in order to calculate PI
    *
    * This method specifically counts values on the circle as *inside*
    * of the circle.
    * This method also uses *doubles* instead of *integers*
    *
    * @param the size of the edge grid should be odd for symmetric arrangement
    * of the circle
    * @param the number of tries the method should run through
    * @return the estimated value of pi
    */
   public static double mci2d(int size, int n)
   {
      int in=0;
      int out=0;
      double ran=0;
      double rand=0;

      for(int m=n;m>0;m--)
      {
         //this loop calculates random values for the Monte Carlo approach

         ran=(Math.random()*size*size);
         //finds the distance of the point compared to a circle with center size/2,size/2
         rand=(Math.sqrt((ran%size-size/2)*(ran%size-size/2)
               +(ran/size-size/2)*(ran/size-size/2)));
         //if the distance between the point and the center of the circle
         //is less than the radius of the circle then the point is counted as inside
         if(rand>size/2)
         {
            out++;
         }
         else
         {
            in++;
         }
      }

      return 4.0*in/(in+out);
   }

   /**
    * Uses a Monte Carlo approach to determine whether random values
    * are within a square or a circle encapsulated into the square.
    *
    * Within a loop with iterations n,
    * the method calculates a random number in the range of 0 to size*size, exclusive.
    * Next, the method calculates the distance of the number to center of the "grid"
    * *Note: there is no grid other than an imaginary grid with size rows and size columns
    * *Note: the row number is calculated by dividing the number by the size
    * *Note: and the column number is calculated by using the modulus operator on the number by the size
    * A series of if statements determine whether the number is within the radius
    * of the circle and appropriately tallies the corresponding value in or out
    * Lastly, the method equates the ratio of 4/PI with (in+out)/in in order to calculate PI
    *
    * This method specifically counts values on the circle as *inside/outside depending on a value*
    * of the circle.
    * The first value on the circle is added in, the next is added out, the next is added in, etc.
    * This method also uses *doubles* instead of *integers*
    *
    * @param the size of the edge grid should be odd for symmetric arrangement
    * of the circle
    * @param the number of tries the method should run through
    * @return the estimated value of pi
    */   public static double mci3d(int size, int n)
   {
      int in=0;
      int out=0;
      double ran=0;
      double rand=0;
      //if ranb is false the value is not added outside
      boolean ranb=false;

      for(int m=n;m>0;m--)
      {
         //this loop calculates random values for the Monte Carlo approach

         ran=(Math.random()*size*size);
         //finds the distance of the point compared to a circle with center size/2,size/2
         rand=(Math.sqrt((ran%size-size/2)*(ran%size-size/2)
               +(ran/size-size/2)*(ran/size-size/2)));
         //if the distance between the point and the center of the circle
         //is less than the radius of the circle then the point is counted as inside
         //yet if the value is on the line
         //ranb is called to alternate between counting in or out of the circle
         if(rand>size/2)
         {
            out++;
         }
         else if(rand==size/2&&ranb)
         {
            out++;
            ranb=false;
         }
         else if(rand==size/2&&!ranb)
         {
            in++;
            ranb=true;
         }
         else
         {
            in++;
         }
      }

      return 4.0*in/(in+out);
   }
}
