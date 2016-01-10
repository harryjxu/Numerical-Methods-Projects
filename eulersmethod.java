package test;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Calculates x from a given a, vi, and xi
 * using Euler's method and a drag equation with physics equations
 * net F = m a
 * v = v0 + a t
 * x = x0 + v t
 * net F = Fg +/- Fairresistance
 * Faiiresistance= 1/2 c a p v^2
 *
 * @author harryxu
 * Created on 100914
 *
 */

public class eulersmethod
{
   //statics for the equation of air resistance
   //drag coefficient=0.04 bullet; 0.47 ball
   public static final double C=0.47;
   //cross area= radius=0.00885m 50 cal; 21diameter cm ball
   public static final double A=Math.PI*0.21*0.21/4.;
   //mass of the object 113kg
   public static final double MASS=113.00;
   //gravitational constant
   public static final double G=9.8;

   //mass density of air is 1.225 kg/m^3 at sea level and at 15c
   public static final double P0=1.225;
   //default altitude from the radius 6,371km
   public static final double Y0=6371000.0;
   //r for adiabatic
   public static final double R=6.5E-3;
   //a for adiabatic
   public static final double Alpha=2.5;
   //t0 temp 0 at sea level
   public static final double T0=298.15;


   //tests the position functions
   public static void main(String[] args) throws IOException
   {
      double v=10.0;
      double deltat=0.100;
      int iter=300;
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      GraphViewer viewer = new GraphViewer(25000,5000);

    //viewer.mode[0] = "DISCRETE";//dots
      viewer.colors[0] = Color.BLUE;
      viewer.colors[1] = Color.RED;
      viewer.colors[2] = Color.GREEN;
      viewer.colors[3] = Color.YELLOW;

      while(v!=0.0)//reader
      {

      try{
          System.out.print("Enter velocity:");
          v = Double.parseDouble(br.readLine());

          viewer.dataSet[0] = projectilemotion(v,deltat,iter,0);
          viewer.dataSet[1] = projectilemotion(v,deltat,iter,1);
          viewer.dataSet[2] = projectilemotion(v,deltat,iter,2);
          viewer.dataSet[3] = projectilemotion(v,deltat,iter,3);

//          for(int i=0; i<viewer.dataSet[0].length;i++)
//          {
//             System.out.println(viewer.dataSet[0][i].x+":"+
//                   viewer.dataSet[0][i].y+":"+
//          viewer.dataSet[1][i].x+":"+
//          viewer.dataSet[1][i].y+":"+
//          viewer.dataSet[2][i].x+":"+
//          viewer.dataSet[2][i].y+":"+
//          viewer.dataSet[3][i].x+":"+
//                   viewer.dataSet[3][i].y);
//          }

          BufferedWriter writer = null;
          try {

              writer = new BufferedWriter(new FileWriter("outputprojmo1103.txt"));

              for(int i=0; i<iter;i++)
               {
                  writer.write(viewer.dataSet[0][i].x+":"+
                        viewer.dataSet[0][i].y+":"+
               viewer.dataSet[1][i].x+":"+
               viewer.dataSet[1][i].y+":"+
               viewer.dataSet[2][i].x+":"+
               viewer.dataSet[2][i].y+":"+
               viewer.dataSet[3][i].x+":"+
                        viewer.dataSet[3][i].y+"\n");
               }

              System.out.println("output at outputprojmo1103.txt");
          } catch (Exception e) {
              e.printStackTrace();
          } finally {
              try {
                  // Close the writer regardless of what happens...
                  writer.close();
              } catch (Exception e) {
              }
          }


          viewer.repaint();
      }catch(IOException e){
          System.err.println("Invalid Format!");
      }

      }






   }

   /**
    * uses the drag model for air resistance to calculate projectile motino
    * uses the isothermic model of rho=rho0 * e ^ ( -y / y0 )
    * where rho0 is the initial drag coefficient and y is the distance from the earth
    * @param y the altitude from sea level
    * @param v the velocity of the object
    */
   public static double fAR_isothermic(double y, double v)
   {
      return C * A * v * v/2.0 * P0 * Math.exp( -(y + Y0) / Y0 );
   }

   /**
    * same as above except adiabatic
    * uses the adiabatic model of rho=ph0 * ( 1 - r*y/T0 ) ^ alpha
    * where
    */
   public static double fAR_adiabatic(double y, double v)
   {
      return C * A * v * v/2.0 * P0 * Math.pow(1.0 - R * y/T0 , Alpha);
   }

   /**
    * Finds acceleration with models of airresistance
    * fAR=1/2 * p v^2 * C A
    * 0) no airresistance
    * 1) constant airresistance (v)
    * p is p0 for sea level
    * 2) isothermic airresistance (y,v)
    * see fAR_isothermic
    * 3) adiabatic airresistance (y,v)
    * see fAR_adiabatic
    *
    * uses F=ma with the Fg in the downwards y direction
    * and the fAR in the counter direction of the velocity
    * @param y the altitude
    * @param v the Y VELOCITY
    */
   public static double acceleration(double y, double v, String x, int c)
   {
      if(x.equals("y"))
      {
         if(v>0)//air resistance goes against the direction of motion
         {
            if(c==3)
               return -(MASS*G + fAR_adiabatic(y,v))/MASS;
            else if(c==2)
               return -(MASS*G + fAR_isothermic(y,v))/MASS;
            else if(c==1)
               return -(MASS*G + C * A * v * v/2.0 * P0)/MASS;
            else
               return -G;
         }
         else //air resistance helps you stay up!
         {
            if(c==3)
               return -(MASS*G - fAR_adiabatic(y,v))/MASS;
            else if(c==2)
               return -(MASS*G - fAR_isothermic(y,v))/MASS;
            else if(c==1)
               return -(MASS*G - C * A * v * v/2.0 * P0)/MASS;
            else
               return -G;
         }
      }
      else
      {
         if(c==3)
            return -fAR_adiabatic(y,v)/MASS;
         else if(c==2)
            return -fAR_isothermic(y,v)/MASS;
         else if(c==1)
            return -C * A * v * v/2.0 * P0/MASS;
         else
            return 0.0;
      }
   }

   public static double velocity(double y, double vi, double deltat, String s, int c)
   {
      return vi + acceleration(y,vi,s,c)*deltat;
   }

   public static double position(double y, double vi, double deltat, String s, int c)
   {
      return velocity(y,vi,deltat,s,c)*deltat;
   }


   public static Point[] projectilemotion(double v, double deltat, int iter, int c)
   {
      Point[] ret=new Point[iter];
      boolean printed=false;

      double vy=Math.sin(45.*Math.PI/180.)*v;
      double vx=Math.cos(45.*Math.PI/180.)*v;
      double yi=1.0;//start height of 1
      double xi=0.0;

      for(int i=0;i<iter;i++)
      {
         yi+=position(yi,vy,deltat,"y", c);
         vy+=acceleration(yi,vy,"y",c)*deltat;

         xi+=position(yi,vx,deltat,"x",c);
         vx+=acceleration(yi,vx,"x",c)*deltat;

         if(yi<=0.21&&!printed)
         {
            System.out.println("y~0; x = "+xi);
            printed=true;
         }

         ret[i]=new Point(xi,yi);
      }

      return ret;
   }

   /**
    * The actual position function for an object with CONSTANTTT acceleration
    * @param xi the initial position
    * @param vi the initial velocity
    * @param a constant acceleration
    * @param t the total time elapsed
    */
   public static double position_actual(double xi,double vi, double a, double t)
   {
      return xi + vi * t + 1/2.0 * a * t * t;
   }
}
