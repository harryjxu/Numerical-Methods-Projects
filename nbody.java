package test;


/**
 *
 * @author harryxu version 0105
 *
 *         Models orbital bodies using gravitational forces
 *
 *         objects have information stored in a double array
 *         (mass,x,y,z,xv,yv,zv)
 */
public class nbody
{
   // from wikipedia: 6.673 * 10^-11 n * (m/kg)^2 (the gravitational force
   // constant)
   private static double G = 6.673E-11;

   // the timestep for the Euler approximation is an hour so 3600*1000
   // milliseconds
   private static double DELTAT = 720.0*2.0 *4.0;

   private static double AU = 1.496E11;

   /**
    * Runs the orbital body simulation for N objects Creates the viewer and
    * calls on individual methods for various configurations The total time
    * elapsed = DELTAT * iterations
    */
   public static void main(String[] args)
   {
      // number of objects - should match the program being called
      int nbody = 9;
      // default iterations
      int iterations = 8500*5*5;
      // default viewer window
      GraphViewer viewer = new GraphViewer(10.*AU, 10.*AU);
      // initiates points all at (0,0)
      viewer.dataSet[0] = new Point[nbody];
      for (int i = 0; i < nbody; i++) {
         viewer.dataSet[0][i] = new Point(0, 0);
      }

      // calls a specific body program
      obj_9_SOLAR(viewer, iterations);
   }

   /**
    * Given a viewer object, initializes a solar system with
    * Sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune
    *
    *  The values are given by using
    * WolframAlpha's database The method uses an Euler approximate to find the
    * change in velocity with the acceleration from gravity, Ag, Calculates the
    * velocity with acceleration Vi+1 = Vi + A*tstep Adjusts the position Xi+1 =
    * Xi + Vi+1*tstep The method uses 3D vectors, but can only plot 2D
    * coordinates Thus the 3 bodies are confined to a 2D plane
    *
    * @param viewer
    *           plots the points
    * @param iterations
    *           number of iterations for the Euler estimation to run through
    */
   public static void obj_9_SOLAR(GraphViewer viewer, int iterations)
   {

      // initializes two objects with the 7element array of
      // mass,x,y,z,xvelocity,yvelocity,zvelocity
      // Array contains Sun, Earth, and Moon along with respective elements
      double[][] objects = new double[9][];
      for(int i = 0 ; i<9 ;i++)
      {
         objects[i] = new double[7];
      }
      // configures the viewer to plot points
      viewer.mode[0] = "DISCRETE";

      // Sets the initial values of the objects
      // Uses the values given from WolframAlpha
      // masses of the objects
      // sun
      objects[0][0] = 1.988435E30;
      // mercury
      objects[1][0] = 3.30104E23;
      // venus
      objects[2][0] = 4.86732E24;
      // earth
      objects[3][0] = 5.9721986E24;
      // mars
      objects[4][0] = 6.14693E23;
      // jupiter
      objects[5][0] = 1.89813E27;
      // saturn
      objects[6][0] = 5.68319E26;
      // uranus
      objects[7][0] = 8.68103E25;
      // neptune
      objects[8][0] = 1.0241E26;
      // Sun xyz coordinates
      // lets center it around the sun
      objects[0][1] = 0.0;
      // Sun xyz velocities
      objects[0][5] = 0.0;

      // mercury
      objects[1][1] = 0.39528297*AU;
      objects[1][5] = 47.4E3;
      // venus
      objects[2][1] = 0.72334858*AU;
      objects[2][5] = 35E3;
      // earth
      objects[3][1] = 1.00013973*AU;
      objects[3][5] = 29.8E3;
      // mars
      objects[4][1] = 1.53030994*AU;
      objects[4][5] = 24.1E3;
      // jupiter
      objects[5][1] = 5.20945576*AU;
      objects[5][5] = 13E3;
      // saturn
      objects[6][1] = 9.551053*AU;
      objects[6][5] = 9.64E3;
      // uranus
      objects[7][1] = 19.21261222*AU;
      objects[7][5] = 6.8E3;
      // neptune
      objects[8][1] = 30.07007178*AU;
      objects[8][5] = 5.43E3;
      //sets a lot to 0 to make things easier to calculate
      for(int i=0;i<objects.length;i++)
      {
         objects[i][2] = 0.0;
         objects[i][3] = 0.0;
         objects[i][4] = 0.0;
         objects[i][6] = 0.0;
      }


      // the loop calculates Euler estimations for the position of the objects
      // using the accleration
      for (int i = 0; i < iterations; i++) {
         // adjusts velocity
         for (int j = 0; j < objects.length; j++) {
            // acceleration calculations between ALL objects
            for (int k = 0; k < objects.length; k++) {
               // dont count yourself in the gravity calculation otherwise
               // you will divide by zero and destroy the known universe
               // making gravitational acceleration useless

               // gravitational acceleration from each of the objects in the
               // system
               if (k != j) {
                  objects[j][4] += Ag(objects[j], objects[k])[0] * DELTAT;
                  objects[j][5] += Ag(objects[j], objects[k])[1] * DELTAT;
                  objects[j][6] += Ag(objects[j], objects[k])[2] * DELTAT;
               }
            }
         }

         // adjusts positions
         for (int j = 0; j < objects.length; j++) {
            objects[j][1] += objects[j][4] * DELTAT;
            objects[j][2] += objects[j][5] * DELTAT;
            objects[j][3] += objects[j][6] * DELTAT;
         }

         // updates only the x and y positions as the viewer is 2D only
         for (int j = 0; j < objects.length; j++) {
            //raw cartesian coordinates
            viewer.dataSet[0][j].x = objects[j][1];
            viewer.dataSet[0][j].y = objects[j][2];
         }

         if(i%(12*5 /4)==0)
            System.out.println(i/(5*12 /4)%(12*30) +" days "+
         i/(5*12*30 /4)%12 + " months " + i/(5*12*348 /4) + " years");

         viewer.dataSet[0][0].x = 0.0;
         viewer.dataSet[0][0].y = 0.0;

         // pauses the program for 5 ms for the objects' movements to be visible
         try {
            Thread.sleep(1);
         } catch (Exception e) {

         }
         // }

         viewer.repaint();
      }
   }

   /**
    * Receives an input of two object positions in the double array notation and
    * returns the gravitational acceleration on the first object from the second
    * based on the gravitational force equation: F = m1 m2 G / |r|^3 * r
    * simplified to the acceleration equation by canceling masses in F=ma, thus
    * a = m2 G / |r|^3 * r r is the distance between the two objects m1 is the
    * mass of the first object m2 is the mass of the second object G is the
    * gravitational force constant |r| is the magnitude of the difference in
    * position vectors (distance between objects)
    *
    * @return the gravitational force in vector notation on object1 by object2
    */
   public static double[] Ag(double[] object1, double[] object2)
   {
      // force vector in xyz notation
      double[] ret = new double[3];

      // calculates the distance between the objects
      double d = Math.abs(Math.sqrt((object2[1] - object1[1])
            * (object2[1] - object1[1]) + (object2[2] - object1[2])
            * (object2[2] - object1[2]) + (object2[3] - object1[3])
            * (object2[3] - object1[3])));

      // calculates part of the gravitational acceleration equation without the
      // direction
      // given by the difference in position vector
      double g = object2[0] * G / (d * d * d);

      // multiplies by the difference in position vector to determine direction
      // x coordinate
      ret[0] = (object2[1] - object1[1]) * g;
      // y coordinate
      ret[1] = (object2[2] - object1[2]) * g;
      // z coordinate
      ret[2] = (object2[3] - object1[3]) * g;

      return ret;
   }

   /**
    * Runs an
    *
    * circular orbit should occur given a starting velocity of v=Sqrt[MG/4r]
    */

}
