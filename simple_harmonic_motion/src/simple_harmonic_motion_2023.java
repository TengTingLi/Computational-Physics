
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
 * @date 8-4-2023
 */
public class simple_harmonic_motion_2023 extends javax.swing.JFrame {

    /**
     * Creates new form simple_harmonic_motion_2023
     */
    public simple_harmonic_motion_2023() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panel_plot = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        panel_plot.setBackground(new java.awt.Color(0, 255, 204));

        javax.swing.GroupLayout panel_plotLayout = new javax.swing.GroupLayout(panel_plot);
        panel_plot.setLayout(panel_plotLayout);
        panel_plotLayout.setHorizontalGroup(
            panel_plotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );
        panel_plotLayout.setVerticalGroup(
            panel_plotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 347, Short.MAX_VALUE)
        );

        jLabel1.setBackground(new java.awt.Color(0, 0, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("<html>Simple Harmonic Motion</html>\n");

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\my ting 2 university boogaloo\\sem 6\\SSCP 3333 COMPUTATIONAL PHYSICS\\simple_harmonic_motion\\small_angle_pendulum_formular.PNG")); // NOI18N
        jLabel2.setText("jLabel2");

        jToggleButton1.setBackground(new java.awt.Color(255, 153, 255));
        jToggleButton1.setForeground(new java.awt.Color(0, 0, 0));
        jToggleButton1.setText("jToggleButton1");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_plot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(98, 98, 98)
                        .addComponent(jToggleButton1))
                    .addComponent(panel_plot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    Motion of simple pendulum
    theta() = pendulum angle
    omega() = pendulum anglelar velocity
    t() = time, length() =
    dt = timestep
    */
    
    public void Calculate(double theta[], double omega[], double t[], double length, double dt){
        double g = 9.81;
        double period = 2 * Math.PI / Math.sqrt(g / length);
        t[0] = 0; 
        theta[0] = Math.toRadians(5);
        omega[0] = 1;
        for(int i = 0; i < 9999; i++){
            t[i+1] = t[i] + dt;
            omega[i+1] = omega[i] - (g / length) * theta[i] * dt;
            theta[i+1] = theta[i] + omega[i+1] * dt;
            plotPoint(t[i], theta[i]);
        }
    }


    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        initPlot(0, 0, 20, 2, 10, 50);
        setPenSize(2);
        double t[] = new double[10000];
        double theta[] = new double[10000];
        double omega[] = new double[10000];
        double length = 1;
        double dt = 0.01;
        Calculate(theta, omega, t, length, dt);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    //provided by Assosiate Prof ABD Rahman Tamuri
    //edited by Teng Ting Li
    //version 1.1.1
    // <editor-fold defaultstate="collapsed" desc=" ${Changelog} ">
    /*
    1.1.0 - initPlot will clear panel with background colour
          - defult draw format is size 1 and black to draw axis more nicely
          - rename PlotPanel to panel_plot
    1.1.1 - change clear background code to use .update method
    */
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" ${UrusanJavaPlot} ">


Graphics2D gg;
@Override 
public void paint(Graphics g){
gg = (Graphics2D) g;
super.paint(gg);
gg=(Graphics2D)panel_plot.getGraphics();
}

double convfx, convfy;
int offsetj,offseti;
public void initPlot(double x1,double y1,double x2, double y2,int xoffset,int yoffset){
    //clear panel but keep bg colour
    panel_plot.update(gg);  //because gg.clearRect is bs and gg.fillRect need one more code
    
    //set axis drawing format
    setPenSize(1);
    setPenColor(Color.black);
    
    //magic
    convfx=panel_plot.getWidth()/(x2-x1);
    convfy=panel_plot.getHeight()/(y2-y1);
    double dpx = xoffset/100.0;
    double dpy = yoffset/100.0;
    offseti=(int)(panel_plot.getWidth()*dpx);
    offsetj=(int)(panel_plot.getHeight()*dpy);
    double W = panel_plot.getWidth();
    double H = panel_plot.getHeight();
    gg.drawLine(0, offsetj ,panel_plot.getWidth(),offsetj );
    gg.drawLine(offseti, 0,offseti,panel_plot.getHeight());
    //panel_plot.setBackground(Color.GREEN);
    //draw scale x
    for(int l=0;l<panel_plot.getWidth();l=l+(panel_plot.getWidth()/10)){
    gg.drawLine(offseti+l, offsetj,offseti+l,offsetj+5);
    gg.drawLine(offseti-l, offsetj,offseti-l,offsetj+5);
    gg.drawString(""+String.format("%.0f",x2*(l/W)), offseti+l-5, offsetj+20);
    gg.drawString(""+String.format("%.0f",x2*(-l/W)), offseti-l-5, offsetj+20);
}
//draw scale y
for(int p=0;p<H;p= p +(int)H/10){
gg.drawLine(offseti, offsetj-p,offseti-5,offsetj-p);
gg.drawLine(offseti, offsetj+p,offseti-5,offsetj+p);
gg.drawString(""+String.format("%.0f",y2*(p/H)), offseti-20, offsetj-p+5); 
gg.drawString(""+String.format("%.0f",y2*(-p/H)), offseti-25, offsetj+p+5);
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
            java.util.logging.Logger.getLogger(simple_harmonic_motion_2023.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(simple_harmonic_motion_2023.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(simple_harmonic_motion_2023.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(simple_harmonic_motion_2023.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new simple_harmonic_motion_2023().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JPanel panel_plot;
    // End of variables declaration//GEN-END:variables
}