
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/*
 * /*Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template*/
 

/**
 *
 * @author Teng Ting Li
 * Date: 2-4-2023
 */
public class Projectile_Motion_2023 extends javax.swing.JFrame {

    /**
     * Creates new form Projectile_Motion_2023
     */
    public Projectile_Motion_2023() {
        initComponents();
    }

//provided by Dr Ahmad Tamuri
// <editor-fold defaultstate="collapsed" desc=" ${UrusanJavaPlot} ">


Graphics2D gg;
@Override 
public void paint(Graphics g){
gg = (Graphics2D) g;
super.paint(gg);
gg=(Graphics2D)PlotPanel.getGraphics();
}

double convfx, convfy;
int offsetj,offseti;
public void initPlot(double x1,double y1,double x2, double y2,
        int xoffset,int yoffset){

convfx=PlotPanel.getWidth()/(x2-x1);
convfy=PlotPanel.getHeight()/(y2-y1);
double dpx = xoffset/100.0;
double dpy = yoffset/100.0;
offseti=(int)(PlotPanel.getWidth()*dpx);
offsetj=(int)(PlotPanel.getHeight()*dpy);
double W = PlotPanel.getWidth();
double H = PlotPanel.getHeight();
gg.drawLine(0, offsetj ,PlotPanel.getWidth(),offsetj );
gg.drawLine(offseti, 0,offseti,PlotPanel.getHeight());
//PlotPanel.setBackground(Color.GREEN);
//draw scale x
for(int l=0;l<PlotPanel.getWidth();l=l+(PlotPanel.getWidth()/10)){
gg.drawLine(offseti+l, offsetj,offseti+l,offsetj+5);
gg.drawLine(offseti-l, offsetj,offseti-l,offsetj+5);
gg.drawString(""+String.format("%.1f",x2*(l/W)), offseti+l-5, offsetj+20);
gg.drawString(""+String.format("%.1f",x2*(-l/W)), offseti-l-5, offsetj+20);
}
//draw scale y
for(int p=0;p<H;p= p +(int)H/10){
gg.drawLine(offseti, offsetj-p,offseti-5,offsetj-p);
gg.drawLine(offseti, offsetj+p,offseti-5,offsetj+p);
gg.drawString(""+String.format("%.1f",y2*(p/H)), offseti-20, offsetj-p+5); 
gg.drawString(""+String.format("%.1f",y2*(-p/H)), offseti-25, offsetj+p+5);
}
}

public void plotPoint(double datax, double datay){
double sx,sy;
int i,j;
//calculate screen coordinates
sx=datax*convfx;
sy=datay*convfy;
//converst to integer plus axis offset
i=offseti+(int)sx;
j=offsetj-(int)sy;
gg.drawLine(i,j,i,j);

}
public void plotVector(double datax, double datay){
double sx,sy;
int i,j;
//calculate screen coordinates
sx=datax*convfx;
sy=datay*convfy;
//converst to integer plus axis offset
i=offseti+(int)sx;
j=offsetj-(int)sy;
gg.drawLine(offseti,offsetj,i,j);

}
public void plotVector1(double x0, double y0, double x, double y){
double sx0,sy0,sx,sy;
int i0,j0,i,j;
//calculate screen coordinates
sx0=x0*convfx;
sy0=y0*convfy;
sx=x*convfx;
sy=y*convfy;
//converst to integer plus axis offset
i0=offseti+(int)sx0;
j0=offsetj-(int)sy0;
i=offseti+(int)sx;
j=offsetj-(int)sy;
gg.drawLine(i0,j0,i,j);

}
private void setPenSize (int penSize) {
gg.setStroke(new BasicStroke(penSize));
gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

}   
 public void setPenColor(Color penColor){
        gg.setColor(penColor);
        
    }
 
 public void drawString(String ss, int x, int y){
     gg.drawString(ss, offseti+x, offsetj-y);
     
 }
 
 
 
 
//Urusan File
public PrintStream openfile (String filename){
PrintStream ps = null;
try{
ps = new PrintStream (new FileOutputStream(filename));
}catch (IOException e){
    System.err.printf("\nProblem creating file:%s\n\n",filename);}
return ps;
}

public void callGnuplot(javax.swing.JTextArea GnuplotCommand){
        PrintStream ps = openfile("gnuplotcmd.plt");
        String strCmd = GnuplotCommand.getText();
        ps.printf("%s\n", strCmd);
        ps.close();

        Runtime callgp = Runtime.getRuntime();
        try {
           // Process prcs = callgp.exec("C:\\gnuplot\\bin\\wgnuplot -persist gnuplotcmd.plt");
            Process prcs = callgp.exec("wgnuplot gnuplotcmd.plt");
        } catch (IOException e) {
           System.err.println ("Error calling gnuplot");
        }
    }

// </editor-fold>
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 0));
        jLabel1.setText("<html>\nProjectile Motion\n</html>\n");

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Projectile_Motion_2023.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Projectile_Motion_2023.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Projectile_Motion_2023.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Projectile_Motion_2023.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Projectile_Motion_2023().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
