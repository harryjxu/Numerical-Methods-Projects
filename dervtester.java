package test;

import test.derv.point;

public class dervtester {

   public static int size=1000;

   public static void main(String[] args)
   {
      point[] base=new point[size];
      point[] dervtest=new point[size];
      point[] dervreal=new point[size];

      for(int i=size-1;i>=0;i--)
      {
         base[i]=new point();
         base[i].x=i;

         base[i].y=(i+2)*(i+2)*(i+2)-4;

         dervreal[i]=new point();

         dervreal[i].x=i;
         dervreal[i].y=3*(i+2)*(i+2);
      }

      dervtest=derv.derv1(base);

      for(int i=0;i<size;i++)
      {
         System.out.println(base[i].x+" : "+dervreal[i].y+" : "+dervtest[i].y);
      }
   }
}
