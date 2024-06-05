/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package golf_tengchuangeason;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Title: Golf Project
 * Date: 9/4/2023
 * @author Teng Ting Li, Chuang Le Tian, Chin Eason
 */

/*
Simulates trajectory of golf ball with backspin vector in z-direction.
theta = projectile angle, v0 = initial velocity
S0w/m = 0.25 for golf.
C = 0.5 for v<14, else 7.0/v.
*/
public class Golf extends javax.swing.JFrame {
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
        gg.setColor(Color.black);
        setPenSize(1);
        gg.drawLine(0, offsetj ,PlotPanel.getWidth(),offsetj );
        gg.drawLine(offseti, 0,offseti,PlotPanel.getHeight());
        //PlotPanel.setBackground(Color.GREEN);
        //draw scale x
        for(int l=0;l<PlotPanel.getWidth();l=l+(PlotPanel.getWidth()/10)){
            gg.drawLine(offseti+l, offsetj,offseti+l,offsetj+5);
            gg.drawLine(offseti-l, offsetj,offseti-l,offsetj+5);
            gg.drawString(""+String.format("%.0f",x2*(l/W)), offseti+l-5, offsetj+20);
            gg.drawString(""+String.format("%.0f",x2*(-l/W)), offseti-l-5, offsetj+20);
        }
        //draw scale y
        for(int p=0;p<H;p= p +(int)H/10){
            gg.drawLine(offseti, offsetj-p,offseti-5,offsetj-p);
            gg.drawLine(offseti, offsetj+p,offseti-5,offsetj+p);
            gg.drawString(""+String.format("%.1f",y2*(p/H)), offseti-35, offsetj-p+5);
            gg.drawString(""+String.format("%.1f",y2*(-p/H)), offseti-40, offsetj+p+5);
        }
    }

    public void plotPoint(double datax, double datay){
        double sx,sy;
        int i,j;
        //calculate screen coordinates
        sx=datax*convfx;
        sy=datay*convfy;
        //converts to integer plus axis offset
        i=offseti+(int)sx;
        j=offsetj-(int)sy;
        gg.drawLine(i,j,i,j);
    }
    public void Refresh(){
        PlotPanel.update(gg);
    }
    public void plotOval(double datax, double datay, int height, int width){
        double sx,sy;
        int i,j;
        //calculate screen coordinates
        sx=datax*convfx;
        sy=datay*convfy;
        //converts to integer plus axis offset
        i=offseti+(int)sx-width/2;
        j=offsetj-(int)sy-height/2;
        gg.drawOval(i,j,height,width);
    }
    
    public void drawString(String ss, int x, int y){
        gg.drawString(ss,offseti+x,offsetj-y);
    }
    //draw string in y-axis
    public void drawStringY(String ss, int x, int y){
        int n = ss.length();
        char huruf []=ss.toCharArray();
        for(int i=0;i<n;i++){
            gg.drawString(""+huruf[i], offseti+x, offsetj-(y-10*i));
        }
    }
    private void setPenSize (int penSize) {
    gg.setStroke(new BasicStroke(penSize));
    gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
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
    public void callGnuplot(javax.swing.JTextArea Command){
        PrintStream ps = openfile("gnuplotcmd.plt");
        String strCmd = Command.getText();
        ps.printf("%s\n", strCmd);
        ps.close();
        Runtime callgp = Runtime.getRuntime();
        try {
           // Process prcs = callgp.exec("omega:\\gnuplot\\bin\\wgnuplot -persist gnuplotcmd.plt");
            Process prcs = callgp.exec("C:\\Users\\ChinPC\\Documents\\cHIN eASON\\3comp\\wgnuplot.exe -persist gnuplotcmd.plt");
        } catch (IOException e) {
           System.err.println ("Error calling gnuplot");
        }
    }
// </editor-fold>
    /**
     * Creates new form Golf
     */
    public Golf() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PlotPanel = new javax.swing.JPanel();
        NormalButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cb_magnus = new javax.swing.JCheckBox();
        cb_air_r = new javax.swing.JCheckBox();
        cb_smooth = new javax.swing.JCheckBox();
        cb_density = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        a_box = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        PlotPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED)));

        javax.swing.GroupLayout PlotPanelLayout = new javax.swing.GroupLayout(PlotPanel);
        PlotPanel.setLayout(PlotPanelLayout);
        PlotPanelLayout.setHorizontalGroup(
            PlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );
        PlotPanelLayout.setVerticalGroup(
            PlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        NormalButton.setText("Simulate");
        NormalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NormalButtonActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/golf_tengchuangeason/Images/Images/form.jpg"))); // NOI18N
        jLabel2.setText("placeholder");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("<html>Golf simulation</html>");

        cb_magnus.setForeground(new java.awt.Color(0, 0, 0));
        cb_magnus.setSelected(true);
        cb_magnus.setText("Include magnus effect");
        cb_magnus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_magnusActionPerformed(evt);
            }
        });

        cb_air_r.setForeground(new java.awt.Color(0, 0, 0));
        cb_air_r.setSelected(true);
        cb_air_r.setText("Include air resistance");
        cb_air_r.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_air_rActionPerformed(evt);
            }
        });

        cb_smooth.setForeground(new java.awt.Color(0, 0, 0));
        cb_smooth.setText("smooth ball");
        cb_smooth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_smoothActionPerformed(evt);
            }
        });

        cb_density.setForeground(new java.awt.Color(0, 0, 0));
        cb_density.setSelected(true);
        cb_density.setText("density correction");
        cb_density.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_densityActionPerformed(evt);
            }
        });

        a_box.setColumns(20);
        a_box.setRows(5);
        jScrollPane1.setViewportView(a_box);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb_air_r)
                            .addComponent(cb_magnus)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(NormalButton))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_density)
                                    .addComponent(cb_smooth))))
                        .addGap(33, 33, 33)
                        .addComponent(PlotPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PlotPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cb_magnus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_air_r)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_smooth)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_density)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                        .addComponent(NormalButton)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NormalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NormalButtonActionPerformed
        Refresh();          //clear panel
        initPlot(0,0,400,100,15,90);
        setPenSize(3);
        gg.setColor(Color.getHSBColor((float)2.82, 1, (float)0.85));    //.0 red -> .83 violet
        CalcNorm(0,0,70,9,0.25,0.01,100);
    }//GEN-LAST:event_NormalButtonActionPerformed

    private void cb_magnusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_magnusActionPerformed

    }//GEN-LAST:event_cb_magnusActionPerformed

    private void cb_air_rActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_air_rActionPerformed
        if(!cb_air_r.isSelected()){
            cb_density.setEnabled(false);
            cb_smooth.setEnabled(false);
        }
        else{
            cb_density.setEnabled(true);
            cb_smooth.setEnabled(true);
        }
    }//GEN-LAST:event_cb_air_rActionPerformed

    private void cb_smoothActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_smoothActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_smoothActionPerformed

    private void cb_densityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_densityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_densityActionPerformed

    void CalcNorm(double x,double y,double v0,
            double theta,double S0w_m,double dt,double tmax){
        int nmax = (int)(Math.round(tmax/dt));  //calculate number of loop turns
        
        //declare variable
        double[] xPos = new double [nmax];
        double[] yPos = new double [nmax];
        double rho, v, C, vx, vy, F_d;
        xPos[0] = x;
        yPos[0] = y;
        theta = Math.toRadians(theta);
        vx = v0*Math.cos(theta);
        vy = v0*Math.sin(theta);
        double mass = 0.0459, area = 0.00143;
        
        //simulation loop
        for(int i=0;i<xPos.length-1;i++){
            xPos[i+1] = xPos[i] + vx*dt;
            yPos[i+1] = yPos[i] + vy*dt;
            v = Math.sqrt(vx*vx + vy*vy);
            
            //non smooth ball or smooth ball
            //if(v <= 14 || cb_smooth.isSelected()) C = 0.5;
            //else C = 7.0/v;
            C = v <= 14 || cb_smooth.isSelected() ? 0.5 : 7.0 / v;
            
            //density correction
            rho = cb_density.isSelected() ? Math.exp(-yPos[i]/10000) : 1;    
            
            //air resistance calculation
            F_d = cb_air_r.isSelected() ? C * rho * area * v / mass : 0;
            
            //with magnus effect
            if(cb_magnus.isSelected() == true){
                vx = vx  -F_d*vx*dt -S0w_m*vy*dt;
                vy = vy  -9.81*dt  -F_d*vy*dt  +S0w_m*vx*dt;
            }
            //without magnus effect
            else{
                vx = vx - F_d * vx * dt;
                vy = vy - 9.81 * dt - F_d * vy * dt; 
            }
            
            //debug, can remove
            System.out.printf("%.2f %.2f | %.1f %.1f | %.2f\n",xPos[i],yPos[i],F_d*vx,F_d*vy,S0w_m*vx);
            
            plotPoint(xPos[i],yPos[i]);
            if(yPos[i+1]<0)break; //break loop when the ball hit the floor
        }
    }
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
            java.util.logging.Logger.getLogger(Golf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Golf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Golf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Golf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Golf().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton NormalButton;
    private javax.swing.JPanel PlotPanel;
    private javax.swing.JTextArea a_box;
    private javax.swing.JCheckBox cb_air_r;
    private javax.swing.JCheckBox cb_density;
    private javax.swing.JCheckBox cb_magnus;
    private javax.swing.JCheckBox cb_smooth;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
