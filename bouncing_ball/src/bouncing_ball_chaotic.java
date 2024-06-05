
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
 * title chaotic mechanical system bouncing ball
 * @author Teng Ting
 * @date 12-4-2023
 */
public class bouncing_ball_chaotic extends javax.swing.JFrame {

    public bouncing_ball_chaotic() {
        initComponents();
    }
    
    //provided by Assosiate Prof ABD Rahman Tamuri
    //edited by Teng Ting Li
    //version 1.2.1
    // <editor-fold defaultstate="collapsed" desc=" ${Changelog} ">
    /*
    1.1.0 - defult draw format is size 1 and black to draw axis more nicely
          - rename PlotPanel to panel_plot
    1.1.1 - change clear background code to use .update method
    1.1.2 - rename initPlot to init_plot
          - rename plotPoint to plot_point
          - rename setPenSize to set_pen_size
          - rename setPenColor to set_pen_color
          - rename drawString to draw_string
    1.2.0 - disabled paint method
          - can tell gg to draw on specific panel using drawing(panel_name)
            - special thanks to bryan chong
          - modified spacing to code for readability
    1.2.1 - re-enable paint method, seem like it is needed to use plot.update(gg)
    */
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" ${UrusanJavaPlot Modded} ">


    Graphics2D gg;
    javax.swing.JPanel p_panel;  //placeholder panel variable, acts like pointer to panel to draw
    
    //no idea what any of this do, seems like java legacy stuff
    
    @Override 
    public void paint(Graphics g){
    gg = (Graphics2D) g;
    super.paint(gg);
    }
    
    
    double convfx, convfy;
    int offsetj,offseti;
    public void init_plot(double x1,double y1,double x2, double y2,int xoffset,int yoffset, 
                          javax.swing.JPanel plot){
        //set what panel to draw on
        p_panel = plot;
        gg = (Graphics2D)plot.getGraphics();
        
        //set axis drawing format
        set_pen_size(1);
        set_pen_color(Color.black);
    
        //magic
        convfx=p_panel.getWidth()/(x2-x1);
        convfy=p_panel.getHeight()/(y2-y1);
        double dpx = xoffset/100.0;
        double dpy = yoffset/100.0;
        offseti=(int)(p_panel.getWidth()*dpx);
        offsetj=(int)(p_panel.getHeight()*dpy);
        double W = p_panel.getWidth();
        double H = p_panel.getHeight();
        gg.drawLine(0, offsetj ,p_panel.getWidth(),offsetj );
        gg.drawLine(offseti, 0,offseti,p_panel.getHeight());
        //panel_plot.setBackground(Color.GREEN);
        //draw scale x
        for(int l=0;l<p_panel.getWidth();l=l+(p_panel.getWidth()/10)){
            gg.drawLine(offseti+l, offsetj,offseti+l,offsetj+5);
            gg.drawLine(offseti-l, offsetj,offseti-l,offsetj+5);
            gg.drawString(""+String.format("%.0f",x2*(l/W)), offseti+l-5, offsetj+20);
            gg.drawString(""+String.format("%.0f",x2*(-l/W)), offseti-l-5, offsetj+20);
        }
        //draw scale y
        for(int p=0;p<H;p= p +(int)H/10){
        gg.drawLine(offseti, offsetj-p,offseti-5,offsetj-p);
        gg.drawLine(offseti, offsetj+p,offseti-5,offsetj+p);
        gg.drawString(""+String.format("%.01f",y2*(p/H)), offseti-20, offsetj-p+5); 
        gg.drawString(""+String.format("%.1f",y2*(-p/H)), offseti-25, offsetj+p+5);
        }
    }

    public void plot_point(double datax, double datay){
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
    
    private void set_pen_size (int penSize) {
        gg.setStroke(new BasicStroke(penSize));
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    }
    
    public void set_pen_color(Color penColor){
        gg.setColor(penColor);  
    }
 
    public void draw_string(String ss, int x, int y){
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
        panel_plot_1 = new javax.swing.JPanel();
        button_simulate = new javax.swing.JButton();
        panel_plot_2 = new javax.swing.JPanel();
        panel_plot_3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 102, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 204));
        jLabel1.setText("Chaotic Mechanical Problem : Bouncing Ball");

        javax.swing.GroupLayout panel_plot_1Layout = new javax.swing.GroupLayout(panel_plot_1);
        panel_plot_1.setLayout(panel_plot_1Layout);
        panel_plot_1Layout.setHorizontalGroup(
            panel_plot_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 246, Short.MAX_VALUE)
        );
        panel_plot_1Layout.setVerticalGroup(
            panel_plot_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 292, Short.MAX_VALUE)
        );

        button_simulate.setText("Simulate");
        button_simulate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_simulateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_plot_2Layout = new javax.swing.GroupLayout(panel_plot_2);
        panel_plot_2.setLayout(panel_plot_2Layout);
        panel_plot_2Layout.setHorizontalGroup(
            panel_plot_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );
        panel_plot_2Layout.setVerticalGroup(
            panel_plot_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 292, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_plot_3Layout = new javax.swing.GroupLayout(panel_plot_3);
        panel_plot_3.setLayout(panel_plot_3Layout);
        panel_plot_3Layout.setHorizontalGroup(
            panel_plot_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        panel_plot_3Layout.setVerticalGroup(
            panel_plot_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 292, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(347, 347, 347)
                        .addComponent(button_simulate))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(panel_plot_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_plot_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_plot_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_plot_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_plot_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(124, 124, 124)
                        .addComponent(button_simulate))
                    .addComponent(panel_plot_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 347, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 85, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void simulate(double m1, double m2){
        //double sec_to_sim = 2;
        double x1[] = new double[100000];
        double x2[] = new double[100000];
        double v1[] = new double[100000];
        double v2[] = new double[100000];
        double t[] = new double[100000];
        double g = 9.81;
        double dt = 0.005;
        x1[0] = 1.0; v1[0] = 0.0;
        x2[0] = 3.0; v2[0] = 0.0;
        for(int i = 0; i < 9999; i++){
            v1[i+1] = v1[i] - g * dt;
            x1[i+1] = x1[i] + (v1[i+1] + v1[i]) / 2 * dt;
            v2[i+1] = v2[i] - g * dt;
            x2[i+1] = x2[i] + (v2[i+1] + v2[i]) / 2 * dt;
            t[i+1] = t[i] + dt;
            if(x1[i+1] < 0){v1[i+1] = -v1[i+1];}
            if(x2[i+1] < x1[i+1]){
                v1[i+1] = ((m1 - m2) / (m1 + m2)) * v1[i] + ((2 * m2) / (m1 + m2)) * v2[i];
                v2[i+1] = ((m2 - m1) / (m1 + m2)) * v2[i] + ((2 * m1) / (m1 + m2)) * v1[i];
            }
            set_pen_color(Color.blue);
            //if(x2[i] <= 0){
            //plot_point(x1[i], v1[i]);
            set_pen_color(Color.red);
            plot_point(x2[i], v2[i]);
            //}
        }
    }
    
    private void button_simulateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_simulateActionPerformed
        init_plot(0, 0, 4, 20, 7, 50, panel_plot_1);
        set_pen_size(2);
        simulate(1.0, 1.0);
        draw_string("m1 = m2", 125, 135);
        set_pen_color(new Color(255,255,255));
        init_plot(0, 0, 4, 20, 7, 50, panel_plot_2);
        set_pen_size(2);
        simulate(1.0, 2.0);
        draw_string("m1 = 2 * m2", 125, 135);
        set_pen_color(new Color(255,255,255));
        init_plot(0, 0, 4, 20, 7, 50, panel_plot_3);
        set_pen_size(2);
        simulate(1.0, 3.0);
        draw_string("m1 = 9 * m2", 125, 135);
        set_pen_color(new Color(255,255,255));
    }//GEN-LAST:event_button_simulateActionPerformed

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
            java.util.logging.Logger.getLogger(bouncing_ball_chaotic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bouncing_ball_chaotic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bouncing_ball_chaotic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bouncing_ball_chaotic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new bouncing_ball_chaotic().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_simulate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panel_plot_1;
    private javax.swing.JPanel panel_plot_2;
    private javax.swing.JPanel panel_plot_3;
    // End of variables declaration//GEN-END:variables
}
