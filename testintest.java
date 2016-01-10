/**
 * Harry Xu
 * Created on: 0826
 * This class contains methods that allows the determination of
 * the min, max and EPS values for floats and doubles. After the calculation,
 * the methods print the result.
**/

package test;

public class testintest{

public static void main(String[] args)
{
    minposd();
	minposf();
	epsf();
	epsd();
	maxf();
	maxd();
}

/**
 * Finds the smallest possible float and prints the result.
 */
public static void minposf()
{
   float min=1;
   float lastmin=min;
   while(min!=0)
   {
      lastmin=min;
      min=min/2;
   }
   System.out.println("min float: "+lastmin);
   return;
}

/**
 * Finds the smallest possible double and prints the result
 */
public static void minposd()
{
	double min=1;
	double lastmin=min;
	while(min!=0)
	{
	   lastmin=min;
	   min=min/2;
	}
	System.out.println("min double: "+lastmin);
	return;
}

/**
 * Finds and prints EPS so that the two conditions are met: 1+e=1 AND e!=0
 * (for floats)
 */
public static void epsf()
{
   float e=1;
   float laste=e;
   while(1+e!=1)
   {
	   laste=e;
	   e=e/10;
   }
   System.out.println("eps float: "+laste);
   return;
}

/**
 * Finds and prints EPS so that the two conditions are met: 1+e=1 AND e!=0
 * (for doubles)
 */
public static void epsd()
{
   double e=1;
   double laste=e;
   while(1+e!=1)
   {
	   laste=e;
	   e=e/10;
   }
   System.out.println("eps double: "+laste);
   return;
}

/**
 * Finds the largest possible float and prints the result.
 */
public static void maxf()
{
	float max=2;
	float lastmax=max;
	while(max<Float.POSITIVE_INFINITY)
	{
	   lastmax=max;
	   max=max*2;
	}
	System.out.println("max float: "+lastmax);
	return;
}
/**
 * Finds the largest possible double and prints the result.
 */
public static void maxd()
{
	double max=2;
	double lastmax=max;
	while(max<Double.POSITIVE_INFINITY)
	{
	   lastmax=max;
	   max=max*2;
	}
	System.out.println("max double: "+lastmax);
	return;
}

}
