package test;

/**
 *
 * @author harryxu
 * Created on 090514
 *
 * Contains a point structure and  methods to find the derivative
 * of a point using parabolic estimation.
 *
 */

public class derv {

   /**
    * A structure with an x and y value both initialized to 0 by default
    * Variables are public for faster access and use
    */
   public static class point {

      public double x=0;
      public double y=0;

   }

   /**
    * Uses 3 points to find a parabola, then takes the derivative of the parabola
    * (in the form of y=a(y-h)^2+y0)
    * to estimate the derivative at a point x
    * through the known equation that dy=2a(y-h)
    * a and h formulas are precalculated using WolframAlpha software
    * @param p1 One of the points of the parabola
    * @param p2 One of the points of the parabola
    * @param p3 One of the points of the parabola
    * @param x The point to estimate the derivative
    * @return a double that is the derivative at a point
    */

   public static double threepointderv2(point p1, point p2, point p3, double x)
   {
      //calculates the a and h values of the derivative of a parabola
      double a=( -p1.x*p2.y+p1.x*p3.y+p2.x*p1.y-p2.x*p3.y-p3.x*p1.y+p3.x*p2.y )
            /( ( p2.x-p1.x)*(p3.x-p1.x)*(p2.x-p3.x) );
      double h=(p1.x*p1.x*p2.y-p1.x*p1.x*p3.y-p2.x*p2.x*p1.y+p2.x*p2.x*p3.y+p3.x*p3.x*p1.y-p3.x*p3.x*p2.y )
            /( 2*(p1.x*p2.y-p1.x*p3.y-p2.x*p1.y+p2.x*p3.y+p3.x*p1.y-p3.x*p2.y));

      return 2*a*(x-h);

   }

   /**
    * Uses 3 points to find a parabola, then takes the derivative of the parabola
    * (in the form of y=ax^2+bx+c)
    * to estimate the derivative at a point x
    * through the known equation that dy=2ax+b
    * a and b formulas are precalculated using WolframAlpha software
    * @param p1 One of the points of the parabola
    * @param p2 One of the points of the parabola
    * @param p3 One of the points of the parabola
    * @param x The point to estimate the derivative
    * @return a double that is the derivative at a point
    */

   public static double threepointderv(point p1, point p2, point p3, double x)
   {
      double x1=p1.x;
      double y1=p1.y;
      double x2=p2.x;
      double y2=p2.y;
      double x3=p3.x;
      double y3=p3.y;

      //calculates the a and b values of the derivative of a parabola
//      double a=(p3.x * (-p1.y + p2.y) + p2.x * (p1.y - p3.y) +
//            p1.x * (-p2.y + p3.y))/
//            ((p1.x*1 - p2.x) * (p1.x - p3.x) * (p2.x - p3.x));
//      double b=(p3.x*p3.x * (p1.y - p2.y) + p1.x*p1.x * (p2.y - p3.y) +
//            p2.x*p2.x * (-p1.y + p3.y))/
//            ((p1.x - p2.x) * (p1.x - p3.x) * (p2.x - p3.x));
      //OLD CALCULATION WITH MATHEMATICA

      double a=(x3 * (-y1 + y2) + x2 * (y1 - y3) + x1 * (-y2 + y3))/
            (x1*x1* (x2 - x3) + x2 * x3 * (x2 - x3*x3) + x1 * (-x2*x2 + x3*x3*x3));
      double b=(x3*x3*x3* (y1 - y2) + x1*x1*(y2 - y3) + x2*x2*(-y1 + y3))/
      ((x1 - x2)* (x1* (x2 - x3) - x2 *x3 + x3*x3*x3));

      return 2.0*a*x+b;

   }

   /**
    * Given an array of points containing x and y coordinates,
    * return an array of points containing x and dy/dx coordinates
    * The ends of the array have to be calcualted outside of the
    * loop for individual derivative calculations due to the parabolic
    * method used and thus are less accurate.
    * @param pxy pxy.length has to be larger than 2
    * @return return dpxy an array of appropriate x values with matching derivative values
    */
   public static point[] derv1(point[] pxy)
   {

      point[] dpxy=new point[pxy.length];
      int l=pxy.length;

      for(int i=l-1;i>=0;i--)
      {
         dpxy[i]=new point();
         dpxy[i].x=pxy[i].x;
      }

      //first point of the array
      dpxy[0].y=threepointderv(pxy[0],pxy[1],pxy[2],pxy[0].x);

      for(int i=l-2;i>0;i--)
      {
         dpxy[i].y=threepointderv(pxy[i-1],pxy[i],pxy[i+1],pxy[i].x);
      }

      //last point of the array
      dpxy[l-1].y=threepointderv(pxy[l-3],pxy[l-2],pxy[l-1],pxy[l-1].x);

      return dpxy;
   }
}
