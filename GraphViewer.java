/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

/**
 *
 * @author Vivek Bharadwaj
 */
public class GraphViewer extends javax.swing.JFrame {
   public Point origin;
   public Point[][] dataSet;
   public Color[] colors;
   public static String[] mode;
   double xRange;
   double yRange;
   /**
   * Creates new form GraphViewer
   */
   public GraphViewer(double xR, double yR) {
      origin = new Point(0,0);
    mode = new String[50];
    for(int i = 0; i < 50; i++) {
      mode[i] = "PATH";
    }
    xRange = xR;
    yRange = yR;
      dataSet = new Point[50][0];
      colors = new Color[50];
      for(int i = 0; i < 50; i++) {
         colors[i] = Color.RED;
      }
      initComponents();
      setVisible(true);
      setEnabled(true);
   }
   //@Override
   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      AffineTransform at = g2.getTransform();
      g2.translate(325, 190);
      g2.scale(1, -1);
      g2.clearRect(-350, -200, 700, 400);
      paintAxes(g2);
    for(int i = 0; i < 50; i++) {
      if(mode[i] != null) {
       if(mode[i].equals("PATH")) {
         paintData(g2, i);
       }
       else if(mode[i].equals("DISCRETE")) {
         paintDiscretePoints(g2, i);
       }
      }
    }
   }
   public void paintAxes(Graphics2D g2) {
      g2.setColor(Color.BLACK);
      //Draw the axes
      g2.draw(new Line2D.Double(-300,0, 300, 0));
      g2.draw(new Line2D.Double(0, 150, 0, -150));
      g2.draw(new Line2D.Double(-300, 10, -300, -10));
      g2.draw(new Line2D.Double(300, 10, 300, -10));

   }
   public void paintData(Graphics2D g, int dataIndex) {
    Point[] currentSet = dataSet[dataIndex];
    if(currentSet.length > 0) {
      GeneralPath gp = new GeneralPath();
      double xCalculation = (currentSet[0].x-origin.x)/xRange*300;
      double yCalculation = (currentSet[0].y-origin.y)/yRange*150;
      gp.moveTo(xCalculation, yCalculation);
      for(int j = 0; j < currentSet.length; j++) {
       if(currentSet[j] != null) {
         xCalculation = (currentSet[j].x-origin.x)/xRange*300;
         yCalculation = (currentSet[j].y-origin.y)/yRange*150;
         gp.lineTo(xCalculation, yCalculation);
       }
      }
      g.setColor(colors[dataIndex]);
      g.draw(gp);
    }
   }
   public void paintDiscretePoints(Graphics2D g, int dataIndex) {
    Point[] currentSet = dataSet[dataIndex];
    if(currentSet.length > 0) {
      g.setColor(colors[dataIndex]);
      if(currentSet[0] != null) {
       double xCalculation;
       double yCalculation;
       for(int j = 0; j < currentSet.length; j++) {
         if(currentSet[j] != null) {
          xCalculation = (currentSet[j].x-origin.x)/xRange*300;
          yCalculation = (currentSet[j].y-origin.y)/yRange*150;
          g.draw(new Ellipse2D.Double(xCalculation - 4, yCalculation - 4, 8, 8));
         }
       }
      }
    }
   }
   /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
   //@SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">
   private void initComponents() {

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      setTitle("Say \"no\" to Riffle, Stack, Flatten, Combine Mathematica Syntax");

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGap(0, 638, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGap(0, 320, Short.MAX_VALUE)
      );

      pack();
   }// </editor-fold>


   // Variables declaration - do not modify
   // End of variables declaration
}





//package test;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.geom.AffineTransform;
//import java.awt.geom.Ellipse2D;
//import java.awt.geom.GeneralPath;
//import java.awt.geom.Line2D;
//
//import javax.swing.JFrame;
//
///**
// *
// * @author Vivek
// */
//public class GraphViewer extends JFrame {
//    public Point[][] dataSet;
//    public Color[] colors;
//    public static String[] mode;
//    int xRange;
//    int yRange;
//    /**
//     * Creates new form GraphViewer
//     */
//    public GraphViewer(int xR, int yR) {
//    mode = new String[50];
//    for(int i = 0; i < 50; i++) {
//        mode[i] = "PATH";
//    }
//    xRange = xR;
//    yRange = yR;
//        dataSet = new Point[50][0];
//        colors = new Color[50];
//        for(int i = 0; i < 50; i++) {
//            colors[i] = Color.RED;
//        }
//        initComponents();
//        setVisible(true);
//        setEnabled(true);
//    }
//    //@Override
//    public void paint(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;
//        AffineTransform at = g2.getTransform();
//        g2.translate(325, 190);
//        g2.scale(1, -1);
//        g2.clearRect(-350, -200, 700, 400);
//        paintAxes(g2);
//    for(int i = 0; i < 50; i++) {
//        if(mode[i] != null) {
//        if(mode[i].equals("PATH")) {
//            paintData(g2, i);
//        }
//        else if(mode[i].equals("DISCRETE")) {
//            paintDiscretePoints(g2, i);
//        }
//        }
//    }
//    }
//    public void paintAxes(Graphics2D g2) {
//        g2.setColor(Color.BLACK);
//        //Draw the axes
//        g2.draw(new Line2D.Double(-300,0, 300, 0));
//        g2.draw(new Line2D.Double(0, 150, 0, -150));
//        g2.draw(new Line2D.Double(-300, 10, -300, -10));
//        g2.draw(new Line2D.Double(300, 10, 300, -10));
//
//    }
//    public void paintData(Graphics2D g, int dataIndex) {
//    Point[] currentSet = dataSet[dataIndex];
//    if(currentSet.length > 0) {
//        GeneralPath gp = new GeneralPath();
//        double xCalculation = currentSet[0].x/xRange*300;
//        double yCalculation = currentSet[0].y/yRange*150;
//        gp.moveTo(xCalculation, yCalculation);
//        for(int j = 0; j < currentSet.length; j++) {
//        if(currentSet[j] != null) {
//            xCalculation = currentSet[j].x/xRange*300;
//            yCalculation = currentSet[j].y/yRange*150;
//            gp.lineTo(xCalculation, yCalculation);
//        }
//        }
//        g.setColor(colors[dataIndex]);
//        g.draw(gp);
//    }
//    }
//    public void paintDiscretePoints(Graphics2D g, int dataIndex) {
//    Point[] currentSet = dataSet[dataIndex];
//    if(currentSet.length > 0) {
//        g.setColor(colors[dataIndex]);
//        if(currentSet[0] != null) {
//        double xCalculation = currentSet[0].x/xRange*300;
//        double yCalculation = currentSet[0].y/yRange*150;
//        for(int j = 0; j < currentSet.length; j++) {
//            if(currentSet[j] != null) {
//            xCalculation = currentSet[j].x/xRange*300;
//            yCalculation = currentSet[j].y/yRange*150;
//            g.draw(new Ellipse2D.Double(xCalculation - 4, yCalculation - 4, 8, 8));
//            }
//        }
//        }
//    }
//    }
//    /**
//     * This method is called from within the constructor to initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is always
//     * regenerated by the Form Editor.
//     */
//    //@SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">
//    private void initComponents() {
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        setTitle("Say \"no\" to Riffle, Stack, Flatten, Combine Mathematica Syntax");
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 638, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 320, Short.MAX_VALUE)
//        );
//
//        pack();
//    }// </editor-fold>
//
//
//    // Variables declaration - do not modify
//    // End of variables declaration
//}