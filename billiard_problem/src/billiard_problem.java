
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
 * Title: Billiard Problem
 * @author Teng
 * @Date 23-4-2023
 */
public class billiard_problem extends javax.swing.JFrame {

    /**
     * Creates new form billiard_problem
     */
    public billiard_problem() {
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
    // <editor-fold defaultstate="collapsed" desc=" ${UrusanJavaPlot} ">


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
        gg.drawString(""+String.format("%.0f",y2*(p/H)), offseti-20, offsetj-p+5); 
        gg.drawString(""+String.format("%.0f",y2*(-p/H)), offseti-25, offsetj+p+5);
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
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        text_output = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 0));
        jLabel1.setText("Billiard Problem");

        panel_plot_1.setBackground(new java.awt.Color(255, 255, 102));
        panel_plot_1.setForeground(new java.awt.Color(255, 255, 0));
        panel_plot_1.setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout panel_plot_1Layout = new javax.swing.GroupLayout(panel_plot_1);
        panel_plot_1.setLayout(panel_plot_1Layout);
        panel_plot_1Layout.setHorizontalGroup(
            panel_plot_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        panel_plot_1Layout.setVerticalGroup(
            panel_plot_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jToggleButton1.setText("Square");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jButton1.setText("Circle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        text_output.setColumns(20);
        text_output.setRows(5);
        jScrollPane1.setViewportView(text_output);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panel_plot_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                            .addComponent(panel_plot_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jToggleButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void calculate(double xp, double yp, double vx, double vy, double t, double dt){
        double length_of_array = t / dt;
        double x[] = new double[(int)length_of_array];
        double y[] = new double[(int)length_of_array];
        x[0] = xp;
        y[0] = yp;
        for(int i = 0; i < x.length - 1; i++){
            //simulation euler method
            x[i+1] = x[i] + vx * dt;
            y[i+1] = y[i] + vy * dt;
            //reflection
            vx = x[i+1] > 50 || x[i+1] < -50 ? -vx : vx;
            vy = y[i+1] > 50 || y[i+1] < -50 ? -vy : vy;
            plot_point(x[i], y[i]);
            if(i % 50 == 0){
            //run each iteration every 1 ms
            try{Thread.sleep(1);}
            catch(Exception e){}
            }
        }
    }
    
    public void calculate_c(double xp, double yp, double vx, double vy, double t, double dt){
        double length_of_array = t / dt;
        double x[] = new double[(int)length_of_array];
        double y[] = new double[(int)length_of_array];

        
        double r = 1.0;
        //draw the wall
        for(double th = 0; th < 360.0; th += 0.1){
        double p = r * Math.cos(th);
        double q = r * Math.sin(th);
        plot_point(p,q);
        }
        
        x[0] = xp;
        y[0] = yp;
        for(int i = 0; i < x.length - 1; i++){
            //simulation euler method
            x[i+1] = x[i] + vx * dt;
            y[i+1] = y[i] + vy * dt;
            double r0 = Math.hypot(x[i+1],y[i+1]);
            double theta = Math.atan(y[i+1]/x[i+1]);
            double vx_perpandicular, vy_perpandicular;
            double vx_parallel, vy_parallel;
            //reflection
            if(r < r0){

                
          
                
                /*this way of implementation caused too much build up of error
                //when ball hit the wall, its position is basically n vector
                //find n hat
                nx = x[i] / r;
                ny = y[i] / r;
                //v_perpandicular = (v*n_hat) n_hat
                vx_perpandicular = (vx * nx + vy * ny) * nx;
                vy_perpandicular = (vx * nx + vy * ny) * ny;
                //v_parallel = v - v_perpandicular
                vx_parallel = vx - vx_perpandicular;
                vy_parallel = vy - vy_perpandicular;
                //reflect
                vx_perpandicular *= -1;
                vy_perpandicular *= -1;
                vx = vx_perpandicular + vx_parallel;
                vy = vy_perpandicular + vy_parallel;
                x[i+1] = x[i] + vx * dt;
                y[i+1] = y[i] + vy * dt;
                text_output.append(String.format("%.6f %.6f\n", vx, vy));
                */
                
                //rotational matric method
                vx_perpandicular = vx * Math.cos(theta) + vy * Math.sin(theta);
                vy_parallel = vx * -Math.sin(theta) + vy * Math.cos(theta);
                vx_perpandicular = -vx_perpandicular;
                vx = vx_perpandicular * Math.cos(theta) + vy_parallel * -Math.sin(theta);
                vy = vx_perpandicular * Math.sin(theta) + vy_parallel * Math.cos(theta);
                //edge correction
                if(x[i+1]>0&&y[i+1]>0){
                    x[i+1]= r*Math.cos(theta); y[i+1]= r*Math.sin(theta);}
                if(x[i+1]<0&&y[i+1]<0){
                    x[i+1]=-r*Math.cos(theta); y[i+1]=-r*Math.sin(theta);}
                text_output.append(String.format("hit\n"));
            }
            text_output.append(String.format("%.6f %.6f\n", x[i], y[i]));
            text_output.append(String.format("%.6f\n", theta/Math.PI*180));
           
            
            plot_point(x[i+1], y[i+1]);
            
            if(i % 10 == 0){
            //run each iteration every 1 ms
            try{Thread.sleep(1);}
            catch(Exception e){}
            }
        }
    }
    
    public void CalcRadial(double x0, double y0, double vx0, double vy0, double r, double dt, double tmax){
        int nmax = (int)Math.round(tmax/dt);
        double[] x = new double[nmax];
        double[] y = new double[nmax];
        double vx,vy,vNorm,vParl,r0,theta;
        x[0]=x0;y[0]=y0;vx=vx0;vy=vy0;
        
        
        for(int i=0; i<nmax-1;i++){
            x[i+1] = x[i] + vx*dt;
            y[i+1] = y[i] + vy*dt;
            r0 = Math.hypot(x[i+1],y[i+1]);
            theta=Math.atan(y[i+1]/x[i+1]);
            
            if(r0>r){
                //transform to radial components
                vNorm = vx*Math.cos(theta) + vy*Math.sin(theta); 
                vParl = vy*Math.cos(theta) - vx*Math.sin(theta);
                //invert direction
                vNorm = -vNorm;
                //edge position correction
                if(x[i+1]>0&&y[i+1]>0){
                    x[i+1]= r*Math.cos(theta); y[i+1]= r*Math.sin(theta);}
                if(x[i+1]<0&&y[i+1]<0){
                    x[i+1]=-r*Math.cos(theta); y[i+1]=-r*Math.sin(theta);}
                //convert back to cartesian
                vx = vNorm*Math.cos(theta) - vParl*Math.sin(theta);
                vy = vParl*Math.cos(theta) + vNorm*Math.sin(theta);
            }
//            try {Thread.sleep(1); }catch(Exception ee){}
            plot_point(x[i],y[i]);
        }
    }
    
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        panel_plot_1.update(gg);
        init_plot(0,0,100,100,50,50,panel_plot_1);
        set_pen_color(new Color(128, 128, 0));
        calculate(10, 10, 2.5, 9.5, 500, 0.01);
        draw_string("finish", 10, 10);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        panel_plot_1.update(gg);
        init_plot(0,0,2,2,50,50,panel_plot_1);
        set_pen_color(new Color(128, 128, 0));
        calculate_c(.4, 1.0/Math.sqrt(2), -1.2, 4.0, 50, 0.001);
        //CalcRadial(.0, 1.0/Math.sqrt(2), -1.0, 0.0, 1, 0.001, 100);
        draw_string("finish", 10, 10);
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
            java.util.logging.Logger.getLogger(billiard_problem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(billiard_problem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(billiard_problem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(billiard_problem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new billiard_problem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JPanel panel_plot_1;
    private javax.swing.JTextArea text_output;
    // End of variables declaration//GEN-END:variables
}
