package test;

/**
 * @author harryxu
 * 103114
 *
 * Models the exponential model of radioactive decay
 * N(t) = N0 e ^ ( -t/tau )
 * with the derivative
 * dN/dt = -N0/tau * e ^ (-t/tau)
 * Uses the Euler and Runge-Katta methods to approximate the remaining
 * amount of material and compares these results to the actual solution.
 */

public class radioactivedecay
{
   /**
    * The tester
    * Change variables within method to change specifications
    * @param args
    *
    * Prints and compares the result from the Euler, Runge-Kutta, and actual
    * solutions
    *
    * Iterations, timestep, starting(amount), and tau are set by default.
    */
   public static void main(String[] args)
   {
      //DEFAULT VARIABLES
      int iterations=1000;
      double timestep=0.01;
      double starting=100.00;
      double tau=5.0;

      System.out.println(decayEuler(starting,iterations,timestep,tau)
            + " : " + decayRK4(starting,iterations,timestep,tau)
      + " : " + starting*Math.exp(-timestep*iterations/tau));
   }

   /**
    * Uses Euler's method with the formula
    * ni+1 = ni + N0/tau * e ^ (-t/tau) * delta
    * to estimate the amount decayed after iter*deltat seconds.
    * A variable with the starting amount is added to
    * the derivative multiplied to the stepsize over
    * a series of iterations and returned.
    *
    * @param ni the initial concentration of particles
    * @param iter the number of iterations for Euler's method calculations
    * @param deltat the stepsize between each Euler's method iteration
    * @param tau the time constant
    */
   public static double decayEuler(double ni, int iter, double deltat, double tau)
   {
      //the amount decayed
      double ret=ni;

      for(int i = 0 ; i < iter ; i++)
      {
         ret += - ni/tau * Math.exp(-deltat*i/tau) * deltat;
      }

      return ret;
   }

   /**
    * Uses the Runge-Kutta Method (RK4) to approximate the
    * amount of material remaining after radioactive decay over
    * iter*deltat seconds.
    * Uses the derivative
    * N0/tau * e ^ (-t/tau)
    * and the Runge-Kutta form
    * ni+1 = ni + h/6 * (k1 + 2k2 + 2k3 + k4)
    * where h is the stepsize and
    * k1 = f(xi, yi)
    * k2 = f(xi + h/2, yi + h/2 k1)
    * k3 = f(xi + h/2, yi + h/2 k2)
    * k4 = f(xi + h, yi + h k3).
    * However, the derivative is only variable on x meaning the
    * changes in yi from the Runge-Kutta are not considered in the calculations.
    * The Runge-Kutta form of ni+1 = ni ... is applied to a variable of amount of
    * material remaining and the variable is returned.
    * @param ni the initial concentration of particles
    * @param iter the number of iterations for Euler's method calculations
    * @param deltat the stepsize between each Euler's method iteration
    * @param tau the time constant
    */
   public static double decayRK4(double ni, int iter, double deltat, double tau)
   {
      //the amount decayed
      double ret=ni;
      //the steps used in the RK4 method
      double k1=0;
      double k2=0;
      double k3=0;
      double k4=0;

      for(int i = 0 ; i < iter ; i++)
      {
         //Runge-Kutta calculations
         k1 = ni/tau * Math.exp(-deltat*i/tau);
         k2 = ni/tau * Math.exp(-(deltat*i + deltat/2.)/tau);
         k3 = ni/tau * Math.exp(-(deltat*i + deltat/2.)/tau);
         k4 = ni/tau * Math.exp(-(deltat*i + deltat)/tau);
         ret += - (k1 + 2*k2 + 2*k3 + k4) * deltat/6.;
      }

      return ret;
   }
}
