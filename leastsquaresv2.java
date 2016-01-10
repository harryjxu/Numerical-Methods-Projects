package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import test.derv.point;



/**
 *
 * @author harryxu
 * Created on 100214
 *
 * The methods of this class contain three least squares implementations
 * to best fit a function to a set of data.
 * !!!The function the methods model and the data can be changed within the class!!!
 * The method, exFunction, is the modeling function and can be changed appropriately.
 *
 * Least squares work by utilizing the error function,
 * E = 1/2 * Sum ( f(xi) - yi )^2,
 * where xi and yi are the various indexes of the data set and f(x) is the modeling function.
 * For example in this class by default, the modeling function,
 * f(x)=A * e ^ -B*x^2 + C,
 * has A, B, and C as error function coefficients which this class determines
 * through a method of error minimization. The methods take the partial derivative
 * of the error function in regards to one of the coefficients of the
 * modeling function at the x coordinate of one of the sets in the data set.
 * Next, the methods utilize the theory of steepest (gradient) descent where a variable lambda,
 * representing step size, is multiplied by the negative of of the partial derivative
 * of the error function in regards to the error function coefficient.
 * The product is repeated across all coefficients and a new error
 * is calculated using the new coefficients.
 * If the resulting new error is smaller than the previous error,
 * the new coefficients replace the old coefficients as error is successfully reduced.
 * If error is not reduced, lambda is divided by 2 and the
 * calculation is attempted again with a new lambda. This process repeats until
 * the change in error becomes insignificant (see method documentation for specific values) or
 * if the iterations surpass a count (see method documentation for specific values).
 *
 * Methods are in the following order:
 * Main
 * exFunction
 * input_data
 * derv_atpointcoefficient
 * errorFunction
 * partialErrorFunction
 * leastsquaresgeneric
 * leastsquareslinear
 * leastsquaresquadratic
 */
public class leastsquaresv2
{

   //change here if more iterations are needed for the least squares loop
   public static final int iterations=10;

   //holds the parameter array for the error function
   //The 0th to nth index of the parameter array represent the coefficients (a,b,c...n)
   public static double[] param;

   //the spacing of the x values for use in the derivative function
   public static double delta=0.1;

   //data consisting of (x,y) coordinates
   public static derv.point[] data;

   //Path file for the loaded data
   public static final String NAME="geigerread.txt";

   //lambda limit for least descent
   public static final double LAMBDA=1E-9;

   /**
    * tester for the least squares for the function defaulted in this class
    * and the data set defaulted in this class
    */
   public static void main(String[] args)
   {
      //inputs data
      input_data(41,3);

      //modifies the coefficients
      leastsquaresgeneric();

   }

   /**
    * This is the modeling function for the data
    * pars[0] represents a, pars[1] represents b, etc
    * BY DEFAULT the method is set to a Gaussian function
    * @param x the x value of the data set
    * @param pars the array of parameters
    * @return the result of the function
    */

   public static double exFunction(double x, double[] pars)
   {
      return pars[0] * Math.exp (-pars[1] * x * x) + pars[2];
   }

   /**
    * This method sets the number of parameters (parameters are assigned to pars)
    * @param pars the number of parameters
    * initializes an array of data points of frequencies from the file path
    * @param size the size of the array for data
    * This method contains a simple y scale x shift adjust for the data
    * The method also contains an upperbounds for random coeffcients and a lower bounds
    */
   public static void input_data(int size, int pars)
   {
      double yadjust=1/10000.0;
      double xadjust=-20.0;
      double upperbounds=1.0;
      double lowerbounds=-1.0;

      param=new double[pars];

      for(int i=0;i<pars;i++)
      {
         param[i]=Math.random()*(upperbounds-lowerbounds)+lowerbounds;
      }
      //specifically for the gaussian b cannot be negative
      param[1]=Math.random()*(upperbounds-lowerbounds);

      try {
         Scanner in = new Scanner(new File(NAME));
         data=new derv.point[size];
         int count=0;

         //scans the file
         while(in.hasNextLine())
         {
            data[count]=new point();
            data[count].x=count+xadjust;
            //adjusts the size of the data for calculations
            data[count].y=Integer.parseInt(in.nextLine())*yadjust;

            count++;
         }

         System.out.println("file: "+NAME+" loaded successfully");
         System.out.println("----------------------------------");

         in.close();
     } catch (FileNotFoundException e) {
         System.out.println("file not found: "+NAME);
     }
     return;
   }

   /**
    * Takes the derivative of the modeling function in regards to a coefficient
    * using the parabolic method solved for 3 points in Mathematica
    * The three points' x values are the param, param+delta, param-delta
    * The three points' y values are the functions calculated with new parameters
    * The newly calculated points are entered into the derivative method
    * from the derv class
    * @param x the x variable where to take a derivative at
    * @param pars the coefficient take a derivative of
    * @return the derivative of a point in regards to a given parameter
    */
   public static double derv_atpointcoefficient(int paramnum, double x, double[] pars)
   {
      //initializes the point array and point objects
      derv.point[] temp=new derv.point[3];

      for(int i=0;i<3;i++)
      {
         temp[i]=new derv.point();
      }

      //determines points used for the derivative
      temp[1].x=pars[paramnum];
      temp[1].y=exFunction(x,pars);

      temp[0].x=pars[paramnum]-delta;
      pars[0]+=-delta;
      temp[0].y=exFunction(x,pars);

      temp[2].x=pars[paramnum]+2*delta;
      pars[2]+=2*delta;
      temp[2].y=exFunction(x,pars);

      pars[2]+= -delta;

      //replaces the array of x and y values with x and dy values
      temp=derv.derv1(temp);

      return temp[1].y;
   }

   /**
    * Calculates the total error using the error function
    * 1/2 Sum (f(xi) - yi)^2
    * uses the data contained in the data variable (in point form)
    * @param pars the array of coefficients
    * @return returns the total error
    */
   public static double errorFunction(double[] pars)
   {
      double ret=0;

      for (int i=0;i<data.length;i++)
      {
         double temp = exFunction(data[i].x,pars) - data[i].y;
         ret += temp*temp;
      }

      return ret/2.0;
   }


   /**
    * Calculates the partial error (in respect to one of the coefficeints)
    * Sum (f(xi) - yi) multiplied by the partial derivative at the point
    * @param par the indexes of which coefficient in the array param
    * @return the partial error (to be used with the partial derivative function)
    */
   public static double partialErrorFunction(int par)
   {
      double ret=0;

      for ( int i=0 ; i < data.length ; i++ )
      {
         double comp1 = (exFunction(data[i].x , param) - data[i].y);//split for DEBUG
         double comp2 = derv_atpointcoefficient(par , data[i].x , param);//^
         ret += comp1*comp2;
      }


      return ret;
   }

   /**
    * This method finds the least squares best fit for the data in this class located in the input_data
    * function.
    * This method adjusts the param array of coefficients to the best fit function.
    *
    * The method runs through a series of iterations, as dictacted by iterations or the value of lambda.
    * The method runs through iteration number of iterations until the count is achieved,
    * indicating no convergence, or until lambda becomes small enough (10^-9 by default)
    * such that convergence is assumed (as the number is small enough for this assumption).
    * Within the iterations, an error value is calculated
    * and either the parameters shift by their partial derivative
    * (as described in the class documentation) or lambda is decreased,
    * error is tested and the cycle repeats.
    * Prints coefficients when the method is finished.
    */
   public static void leastsquaresgeneric()
   {
      int count=0;
      double lambda=10.0;//default "stepsize" of the error function
      double errorprev=errorFunction(param);
      double next=0;
      double[] tempparam=new double[param.length];

      //Original error
      System.out.println("init error: "+errorprev);

      //checks if the error is reduced using the partial derivative method
      while ( count < iterations && lambda > LAMBDA )
      {
         count++;

         //determines the new coefficients of the new error function
         for (int i=0 ; i < param.length ; i++)
         {
            double temp=param[i];
            tempparam[i]=-lambda*partialErrorFunction(i)+temp;
         }

         //new calculation of error with changed parameters
         next=errorFunction(tempparam);

         System.out.println("calc error: "+next+" "+tempparam[0]+" "+tempparam[1]+" "+tempparam[2]);

         //checking if error increases, if so, divide lambda and try again/reset parameters
         if(next>errorprev)
         {
            lambda=lambda/2;

            //DEBUG
            System.out.println("ERROR TOO BIG");
         }
         else
         {
            errorprev=next;
            param=tempparam;
         }

      }

      if(count>=iterations)
         System.out.println("exceeded iterations: loops stopped. lambda is: "+lambda);
      if(lambda<1E-9)
         System.out.println("lambda too small");

      System.out.println("finalerror: "+errorFunction(param));
      for(int i=0;i<param.length;i++)
      {
         System.out.println(" coef. "+(i+1)+": "+param[i]);
      }
      return;
   }

   /**
    * The method returns the coefficients for a linear least squares fit.
    * As the linear form is a closed form solution, the coefficients determined to be
    * in the form of y=Ax+B with
    * B=( X * XY - X2 * Y )/( X * X - X2 * I)
    * A=( X * Y - XY * I)/(X * X - X2 * I)
    * !These are calculated from Mathematica!
    * where X is the sum of all x values from the data
    * where Y is the sum of all y values from the data
    * where XY is the sum of all xy multiplication from the pairs in the data
    * (data was arranged by pairs and this is the summation of the
    * multiplication of the pairs)
    * where X^2 is the sum of all x^2 values from the data (x values were multiplied
    * by themselves then added)
    * where I is the number of data points within the data (data.length)
    * Array is only size 2 because there are only an A and B coefficient
    * Prints the coefficients when the method is finished.
    */
   public static void leastsquareslinear()
   {
      double X=0;
      double Y=0;
      double XY=0;
      double X2=0;
      double[] ret=new double[2];

      for(int i=0;i<data.length;i++)
      {
         X+=data[i].x;
         Y+=data[i].y;
         XY+=data[i].x*data[i].y;
         X2+=data[i].x*data[i].x;
      }

      ret[1]=(X*XY-X2*Y)/(X*X-X2*data.length);
      ret[0]=(X*Y-XY*data.length)/(X*X-X2*data.length);

      //linear fit doesnt need the 3rd term; the term is set to 0 for clarity
      param[0]=ret[0];
      param[1]=ret[1];
      param[2]=0;
      for(int i=0;i<param.length;i++)
      {
         System.out.println(" coef. "+(i+1)+": "+param[i]);
      }
      return;
   }

   /**
    * The method returns the coefficients for a quadratic least squares fit.
    * As the quadratic form is also a closed form solution, the coefficients determined to be
    * in the form of y=Ax^2+Bx+C with
    * A=-((x4 x5^2 - x3 x5 x6 + x3^2 x7 - x2 x5 x7 - x3 x4 z + x2 x6 z)/(
 x3^3 - 2 x2 x3 x5 + x1 x5^2 + x2^2 z - x1 x3 z))
    * B=-((-x3 x4 x5 + x3^2 x6 - x2 x3 x7 + x1 x5 x7 + x2 x4 z - x1 x6 z)/(
 x3^3 - 2 x2 x3 x5 + x1 x5^2 + x2^2 z - x1 x3 z))
    * C=-((x3^2 x4 - x2 x4 x5 - x2 x3 x6 + x1 x5 x6 + x2^2 x7 - x1 x3 x7)/(
 x3^3 - 2 x2 x3 x5 + x1 x5^2 + x2^2 z - x1 x3 z))
    * !These values are calculated form Mathematica!
    * where x1 represents each x value to the power of 4, then summed (X^4)
    * where x2 represents each x value to the power of 3, then summed (X^3)
    * where x3 represents each x value to the power of 2, then summed (X^2)
    * where x4 represents each x value squared then multiplied by the y (X^2Y)
    * by the values within their respective pairs (data is organized by the point class)
    * then summed
    * where x5 represents the summation of x values (X)
    * where x6 represents the multiplication of the x value by the y (XY)
    * value within the same pair
    * where x7 represents the summation of the y values (Y)
    * where z represents the size of the data set
    * Array is only size 2 because there are only an A and B coefficient
    * Prints the coefficients when the method is finished
    */
   public static void leastsquaresquadratic()
   {
      //naming convention is adjusted for mathematica input
      //see method documentation for equivalence
      double x5=0;//x
      double x7=0;//y
      double x6=0;//xy
      double x3=0;//x^2
      double x2=0;//x^3
      double x1=0;//x^4
      double x4=0;//x^2y
      double z=data.length;

      double[] ret=new double[3];

      for(int i=0;i<data.length;i++)
      {
         x5+=data[i].x;
         x7+=data[i].y;
         x6+=data[i].x*data[i].y;
         x3+=data[i].x*data[i].x;
         x2+=Math.pow(data[i].x,3);
         x1+=Math.pow(data[i].x,4);
         x4+=data[i].x*data[i].x*data[i].y;

      }

      ret[0]=((x4*x5*x5 - x3*x5*x6 + x3*x3*x7 - x2*x5*x7 - x3*x4*z + x2*x6*z)/
            (x3*x3*x3 - 2*x2*x3*x5 + x1*x5*x5 + x2*x2*z - x1*x3*z));
      ret[1]=((-x3*x4*x5 + x3*x3*x6 - x2*x3*x7 + x1*x5*x7 + x2*x4*z - x1*x6*z)/
            (x3*x3*x3 - 2*x2*x3*x5 + x1*x5*x5 + x2*x2*z - x1*x3*z));
      ret[2]=((x3*x3*x4 - x2*x4*x5 - x2*x3*x6 + x1*x5*x6 + x2*x2*x7 - x1*x3*x7)/
            (x3*x3*x3 - 2*x2*x3*x5 + x1*x5*x5 + x2*x2*z - x1*x3*z));

      for(int i=0;i<param.length;i++)
      {
         param[i]=ret[i];
         System.out.println(" coef. "+(i+1)+": "+param[i]);
      }
      return;
   }
}
