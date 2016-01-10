package test;


/**
 *
 * @author harryxu
 * Version 1106
 *
 * Simulates a pendulum's motion using physics equations of torques
 */

public class pendulum
{
   //Constants
   //Constant representing Sqrt[g/L]
   private static double OMEGA = 1.0;
   //viewer object
   private static GraphViewer viewer = null;
   //constant representing 1/2 c a p of the drag equation
   private static double Q = 0.0;
   //gravitational constant
   private static double G = 9.8;
   //constant representing length (of the pendulum) using Sqrt[g/L]
   private static double L = G / (OMEGA*OMEGA);
   //constant for the forcing function: frequency
   private static double A = 1.0;
   //amplitude of the forcing function
   private static double B = 0.1;

   /**
    * Initializes the viewer object, the starting point,
    * and calls the appropriate
    * pendulum function
    * Sets the viewer to 'DISCRETE' to view the motion of the bob
    * @param args
    */
   public static void main(String[] args)
   {
      //default iterations
      int iterations = 100000;
      //default stepsize for euler
      double tstep = 0.001;
      //default angle for initial pendulum motion
      double angle = 10.0;
      viewer = new GraphViewer(iterations,3);
      //initializes the array for points
      viewer.dataSet[0] = new Point[1];
      viewer.dataSet[0][0] = new Point(0,-L);

      //allows for the viewing of the path of the pendulum
      //viewer.mode[0] = "DISCRETE";
      eulerD(angle, iterations, tstep);
   }

   /**
    * Uses Euler's Method to estimate angular velocity and the angle
    * of a pendulum without air drag.
    * Updates the viewer with the angle v time graph.
    * Torque = Moment of Inertia * Angular Acceleration
    * w = Angular Velocity
    * theta = Angle from the vertical axis
    * OMEGA = the constant, Sqrt[G/L]
    *
    * Uses the form of Euler's Method such that
    *
    * wi+1 = wi + acceleration*tstep
    * thetai+1 = thetai + wi+1*tstep
    *
    * where acceleration = - OMEGA*OMEGA * Sin theta
    *
    * @param init0 the initial angle of the pendulum
    * @param iter the number of iterations for the method to run through
    * @param tstep the step size for each iteration
    */
   public static void eulerND(double init0, int iter, double tstep)
   {
      //converts degrees (input) into radians (calculations)
      double i0 = init0 /180.*Math.PI;
      //the pendulum starts at rest (speed = 0)
      double w0 = 0;
      //the array consisting of points that compose the angle vs time graph
      Point[] ret = new Point[iter];

      //initializies the array of points
      for(int i = 0 ; i < iter ; i++)
      {
         ret[i] = new Point(0,0);
      }

      //assigns the viewer's plotted dataset to be this set of points
      viewer.dataSet[0] = ret;

      for(int i = 0 ; i < iter ; i++)
      {
         //angular velocity change
         w0 += (- OMEGA*OMEGA * Math.sin(i0)) * tstep;
         //angle change
         i0 += w0 * tstep;

         ret[i].x = i;
         ret[i].y = i0;

         //         try//pauses for 1 ms; used for
         //gradual graphical update display IF NECESSARY
         //         {
         //            Thread.sleep(1);
         //         }
         //         catch(Exception e)
         //         {
         //
         //         }
         viewer.repaint();

         //debugging
         //System.out.println(ret[i].x + " : " + ret[i].y);
      }
   }

   /**
    * Uses Euler's Method to estimate angular velocity and the angle
    * of a pendulum without air drag.
    * Updates the viewer with the angle v time graph.
    * Torque = Moment of Inertia * Angular Acceleration
    * w = Angular Velocity
    * theta = Angle from the vertical axis
    * OMEGA = the constant, Sqrt[G/L]
    *
    * Uses the form of Euler's Method such that
    *
    * thetai+1 = thetai + wi+1*tstep
    * wi+1 = wi + acceleration*tstep
    * (acceleration uses the previous unchanged theta)
    * !!!incorrect; compounding error of Euler's method!!!
    *
    * where acceleration = - OMEGA*OMEGA * Sin theta
    *
    * @param init0 the initial angle of the pendulum
    * @param iter the number of iterations for the method to run through
    * @param tstep the step size for each iteration
    */
   public static void eulerND2(double init0, int iter, double tstep)
   {
      //converts degrees (input) into radians (calculations)
      double i0 = init0 /180.*Math.PI;
      //the pendulum starts at rest (speed = 0)
      double w0 = 0;
      //the array consisting of points that compose the angle vs time graph
      Point[] ret = new Point[iter];
      //holds old value of the angle change
      double temp = 0;

      //initializies the array of points for the viewer to plot
      for(int i = 0 ; i < iter ; i++)
      {
         ret[i] = new Point(0,0);
      }

      //assigns the viewer's plotted dataset to be this set of points
      viewer.dataSet[0] = ret;

      for(int i = 0 ; i < iter ; i++)
      {
         temp = i0;
         //angle change
         i0 += w0 * tstep;

         ret[i].x = i;
         ret[i].y = i0;

         //angular velocity change
         w0 += (- OMEGA*OMEGA * Math.sin(temp)) * tstep;

         //debug
         //System.out.println(i + " : " + i0 + " : " + w0);

         //         try//pauses for 1 ms; used for
         // gradual graphical update display IF NECESSARY
         //         {
         //            Thread.sleep(1);
         //         }
         //         catch(Exception e)
         //         {
         //
         //         }
         viewer.repaint();
      }
   }

   /**
    * Uses Euler's Method to estimate angular velocity and the angle
    * of a pendulum with air drag and a forcing function.
    * Updates the viewer with the angle v time graph.
    * Torque = Moment of Inertia * Angular Acceleration
    * w = Angular Velocity
    * theta = Angle from the vertical axis
    * OMEGA = the constant, Sqrt[G/L]
    * q = the constant, 1/2 C A Rho of drag
    *
    * Uses the form of Euler's Method such that
    *
    * wi+1 = wi + acceleration*tstep
    * thetai+1 = thetai + wi+1*tstep
    *
    * where acceleration = - OMEGA*OMEGA * Sin theta -
    * * q * r * w * Abs[w] + Sin[a t]
    * drag counteracts motion as the w variable indicates the velocity
    *
    * @param init0 the initial angle of the pendulum
    * @param iter the number of iterations for the method to run through
    * @param tstep the step size for each iteration
    */
   public static void eulerD(double init0, int iter, double tstep)
   {
      //converts degrees (input) into radians (calculations)
      double i0 = init0 /180.*Math.PI;
      //the pendulum starts at rest (speed = 0)
      double w0 = 0;
      //the array consisting of points that compose the angle vs time graph
      Point[] ret = new Point[iter];

      //initializies the array of points
      for(int i = 0 ; i < iter ; i++)
      {
         ret[i] = new Point(0,0);
      }

      //assigns the viewer's plotted dataset to be this set of points
      viewer.dataSet[0] = ret;

      for(int i = 0 ; i < iter ; i++)
      {
         //angular velocity change
         w0 += (
               - OMEGA*OMEGA * Math.sin(i0)
        //- L * Q * w0 * Math.abs(w0) + //drag
         + B * Math.sin(A * i * tstep)
         ) * tstep;

         //angle change
         i0 += w0 * tstep;

         ret[i].x = i; //for theta by time
         ret[i].y = i0;

                        //try//pauses for 1 ms; used for
//               gradual graphical update display IF NECESSARY
//                        {
//                           Thread.sleep(1);
//                        }
//                        catch(Exception e)
//                        {
//
//                        }
         viewer.repaint();

               //debugging
              //System.out.println(ret[i].x + " : " + ret[i].y);
      }
   }
}
