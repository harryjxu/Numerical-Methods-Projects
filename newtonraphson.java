package test;


/**
 *
 * @author harryxu
 * Created on 0922
 *
 * The methods of this class estimate the zeros of a function using a Newton-Raphson approach.
 * By using the equation of x next=x - f(x)/f'(x), the methods eventually work towards the zeros of a function.
 * The user can manually adjust the function and the zero the main method is searching for.
 */

public class newtonraphson {


   /**
    * tests the Newton-Raphson method
    * @param args
    */
   public static void main(String[] args)
   {
      System.out.println(newtraph(50));
   }

   /**
    * A function used for testing the Newton-Raphson method
    * @param x the input variable to a function
    * @return the y value of the function at the given x
    */

   public static double func(double x)
   {
      return x*x*x+1;
   }

   /**
    * Calculates the derivative at x
    * The derivative class lacked a derivative function for a specific
    * point for a function as the function derv1 only returned an array
    * of points but without a specific domain.
    * This method is only applicable within the class where the function is defined.
    *
    * Creates a point array of three points with the middle point as point x
    * The spacing between the points are delta (a variable) for accurate calculations
    * Then calls the derivative function from the derv class to calculate the derivative.
    * @param x the point to "center" the array around
    * @return the derivative of the function at the point indicated by the middle value of the array
    */

   public static double derv1atp(double x)
   {
      //initializes the point array and point objects
      derv.point[] temp=new derv.point[3];
      double delta=0.01;
      for(int i=0;i<3;i++)
      {
         temp[i]=new derv.point();
      }

      temp[0].x=x-delta;
      temp[1].x=x;
      temp[2].x=x+delta;
      temp[0].y=func(x-delta);
      temp[1].y=func(x);
      temp[2].y=func(x+delta);

      //replaces the array of x and y values with x and dy values
      temp=derv.derv1(temp);


      return temp[1].y;
   }

   /**
    * Uses the Newton-Raphson method to calculate the nearest zero of
    * the function (previously defined in the class) closest to the x coordinate
    * all within a while loop counting iterations.
    * Returns the zero.
    *
    * This method does NOT guarantee if there is a zero for the function
    * and will continue to run.
    *
    * Has a variable to count the number iterations before stopping the loop
    * (max iterations is 1000)
    * and prints the zero approximation every iteration of the loop
    * @param x the point to find the nearest zero at
    * @return the closest zero of the function in the class
    */

   public static double newtraph(double x)
   {
      double xinit=x;
      int count=0;

      while(count!=1000&&func(xinit)!=0)
      {

         xinit=xinit-func(xinit)/derv1atp(xinit);
         count++;

         System.out.println(count+" "+xinit);

      }

      return xinit;
   }
}
