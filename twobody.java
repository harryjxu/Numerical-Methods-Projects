package test;

/**
 *
 * @author harryxu version 1120
 *
 *         Models orbital bodies using gravitational forces
 *
 *         objects have information stored in a double array
 *         (mass,x,y,z,xv,yv,zv)
 */
public class twobody
{
   // from wikipedia: 6.673 * 10^-11 n * (m/kg)^2 (the gravitational force
   // constant)
   private static double G = 6.673E-11;

   // the timestep for the Euler approximation is an hour so 3600*1000
   // milliseconds
   private static double DELTAT = 720.0*2.0;

   /**
    * Runs the orbital body simulation for N objects Creates the viewer and
    * calls on individual methods for various configurations The total time
    * elapsed = DELTAT * iterations
    */
   public static void main(String[] args)
   {
      // number of objects - should match the program being called
      int nbody = 3;
      // default iterations
      int iterations = 8500*5*5;
      // default viewer window
      GraphViewer viewer = new GraphViewer(1.5E11, 1.5E11);
      GraphViewer viewer2 = new GraphViewer(1.5E9, 1.5E9);
      // initiates points all at (0,0)
      viewer.dataSet[0] = new Point[nbody];
      viewer2.dataSet[0] = new Point[nbody];
      for (int i = 0; i < nbody; i++) {
         viewer.dataSet[0][i] = new Point(0, 0);
         viewer2.dataSet[0][i] = new Point(0, 0);
      }

      // calls a specific body program
      obj_3_SEM(viewer, iterations, viewer2);
   }

   /**
    * Given a viewer object, initializes two masses with orbital velocity By
    * default, the two masses are 1.0*10^11 kg seperated by 10m The initial
    * velocities are given by the derivation from Kepler's Laws, Sqrt[m g / 4 r]
    * The method uses an Euler approximation of step size DELTAT Calculates a
    * change in velocity with the acceleration calculated in the acceleration
    * from gravity, Ag, method Vi+1 = Vi + A*tstep Adjusts the position Xi+1 =
    * Xi + Vi+1*tstep
    *
    * @param viewer
    *           plots the points
    * @param iterations
    *           number of iterations for the Euler estimation to run through
    */
   public static void obj_2_orbit(GraphViewer viewer, int iterations)
   {
      // initializes two objects with the 7element array of
      // mass,x,y,z,xvelocity,yvelocity,zvelocity
      double[] object1 = new double[7];
      double[] object2 = new double[7];
      // configures the viewer to plot points
      viewer.mode[0] = "DISCRETE";

      // Sets the initial values of the objects; in this case, the objects are
      // 10m apart
      // with a mass of 1.0*10^11kg each
      // masses of the objects
      object1[0] = 100000000000.0;
      object2[0] = 100000000000.0;
      // object 1 xyz coordinates
      object1[1] = 5.0;
      object1[2] = 0.0;
      object1[3] = 0.0;
      // object 2 xyz coordinates
      object2[1] = -5.0;
      object2[2] = 0.0;
      object2[3] = 0.0;
      // object 1 xyz velocities
      object1[4] = 0.0;
      object1[5] = 0.0;
      object1[6] = 0.0;
      // object 2 xyz velocities
      object2[4] = 0.0;
      object2[5] = 0.0;
      object2[6] = 0.0;

      // adjusts starting velocities to Sqrt[mG/4r]
      // a velocity that should lead to orbital motion based from Kepler's Laws
      object1[5] = Math.sqrt(object2[0] * G / 20.0);
      object2[5] = -Math.sqrt(object1[0] * G / 20.0);

      // the loop calculates Euler estimations for the position of the objects
      // using the accleration
      for (int i = 0; i < iterations; i++) {
         // adjusts velocity
         object1[4] += Ag(object1, object2)[0] * DELTAT;
         object1[5] += Ag(object1, object2)[1] * DELTAT;
         object1[6] += Ag(object1, object2)[2] * DELTAT;
         object2[4] += Ag(object2, object1)[0] * DELTAT;
         object2[5] += Ag(object2, object1)[1] * DELTAT;
         object2[6] += Ag(object2, object1)[2] * DELTAT;

         // adjusts positions
         object1[1] += object1[4] * DELTAT;
         object1[2] += object1[5] * DELTAT;
         object1[3] += object1[6] * DELTAT;
         object2[1] += object2[4] * DELTAT;
         object2[2] += object2[5] * DELTAT;
         object2[3] += object2[6] * DELTAT;

         // prints only the x and y positions as the viewer is 2D only
         viewer.dataSet[0][0].x = object1[1];
         viewer.dataSet[0][0].y = object1[2];
         viewer.dataSet[0][1].x = object2[1];
         viewer.dataSet[0][1].y = object2[2];

         // prints every 100 * DELTAT seconds location for debugging
         // if(i%100==0)
         // System.out.println("("+object1[1]+","+object1[2]+")"+"   "+"("+object2[1]+","+object2[2]+")");

         // prints the time every hour (3600 seconds)
         // seeing as DELTAT is not always 1 second, the number of iterations
         // the loop is currently running is divided by 3600/DELTAT which
         // should be the time the simulation is running
         // if(i%(3600/DELTAT)==0)
         // {
         // System.out.println(i/3600 + " hours");
         // pauses the program for 5 ms for the objects' movements to be visible
         try {
            Thread.sleep(5);
         } catch (Exception e) {

         }
         // }

         viewer.repaint();
      }
   }

   /**
    * Given a viewer object, initializes a Sun, Earth and Moon system MUST
    * CHANGE NUMBER OF OBJECTS IN MAIN METHOD The values are given by using
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
   public static void obj_3_SEM(GraphViewer viewer, int iterations, GraphViewer viewer2)
   {

      // initializes two objects with the 7element array of
      // mass,x,y,z,xvelocity,yvelocity,zvelocity
      // Array contains Sun, Earth, and Moon along with respective elements
      double[][] objects = new double[3][];
      objects[0] = new double[7];
      objects[1] = new double[7];
      objects[2] = new double[7];
      // configures the viewer to plot points
      viewer.mode[0] = "DISCRETE";
      viewer2.mode[0] = "DISCRETE";

      // Sets the initial values of the objects
      // Uses the values given from WolframAlpha
      // masses of the objects
      // sun
      objects[0][0] = 1.988435E30;
      // earth
      objects[1][0] = 5.9721986E24;
      // moon
      objects[2][0] = 7.3459E22;
      // Sun xyz coordinates
      // lets center it around the sun
         objects[0][1] = 0.0;
         objects[0][2] = 0.0;
         objects[0][3] = 0.0;
         // Earth xyz coordinates
         objects[1][1] = 1.474E11;
         objects[1][2] = 0.0;
         objects[1][3] = 0.0;
         // Moon xyz coordinates
         objects[2][1] = 1.474E11 + 3.96E8;
         objects[2][2] = 0.0;
         objects[2][3] = 0.0;
         // Sun xyz velocities
         objects[0][4] = 0.0;
         objects[0][5] = 0.0;
         objects[0][6] = 0.0;
         // Earth xyz velocities
         objects[1][4] = 0.0;
         objects[1][5] = 29800.0;
         objects[1][6] = 0.0;
         // Moon xyz velocities
         objects[2][4] = 0.0;
         objects[2][5] = 29800.0 + 1020.0;
         objects[2][6] = 0.0;


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
            viewer2.dataSet[0][j].x = objects[j][1]-objects[1][1];
            viewer2.dataSet[0][j].y = objects[j][2]-objects[1][2];
         }

         if(i%(12*5)==0)
            System.out.println(i/(5*12) +" days "+i/(5*12*29) + " months " + i/(5*12*348) + " years");

         viewer.dataSet[0][0].x = 0.0;
         viewer.dataSet[0][0].y = 0.0;

         // pauses the program for 5 ms for the objects' movements to be visible
         try {
            Thread.sleep(1);
         } catch (Exception e) {

         }
         // }

         viewer.repaint();
         viewer2.repaint();
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
