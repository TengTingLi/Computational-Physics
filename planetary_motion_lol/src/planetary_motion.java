
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
 * Title: Planetary motion
 * @author Teng
 * @Date 23-4-2023
 */
public class planetary_motion extends javax.swing.JFrame {

    /**
     * Creates new form billiard_problem
     */
    public planetary_motion() {
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
        tf_x = new javax.swing.JTextField();
        tf_y = new javax.swing.JTextField();
        tf_vx = new javax.swing.JTextField();
        tf_vy = new javax.swing.JTextField();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 102, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 0));
        jLabel1.setText("Planatary Motion with Eular Cromer Method");

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
            .addGap(0, 500, Short.MAX_VALUE)
        );

        jToggleButton1.setText("Circular");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        tf_x.setText("1.0");
        tf_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_xActionPerformed(evt);
            }
        });

        tf_y.setText("0");

        tf_vx.setText("0");

        tf_vy.setText("6.284");
        tf_vy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_vyActionPerformed(evt);
            }
        });

        jToggleButton2.setText("Elliptical");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jToggleButton3.setText("Simulate");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jLabel1)
                .addContainerGap(246, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tf_x, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_y, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_vx, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_vy, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panel_plot_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(258, 258, 258))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(tf_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_vx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_vy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jToggleButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel_plot_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    public void calculate(double xi, double yi, double vxi, double vyi, double t, double dt){
        double[] x = new double[(int)(t/dt)];
        double[] y = new double[(int)(t/dt)];
        double[] vx = new double[(int)(t/dt)];
        double[] vy = new double[(int)(t/dt)];
        x[0] = xi;
        y[0] = yi;
        vx[0] = vxi;
        vy[0] = vyi;
        
        for(int i = 0; i < x.length-1; i++){
            double r = Math.sqrt(x[i]*x[i]+y[i]*y[i]);
            vx[i+1] = vx[i] - 4 * Math.PI * Math.PI * x[i] * dt / (r * r * r);
            x[i+1] = x[i] + vx[i+1] * dt;
            vy[i+1] = vy[i] - 4 * Math.PI * Math.PI * y[i] * dt / (r * r * r);
            y[i+1] = y[i] + vy[i+1] * dt;
            
            if(i % 500 == 0){    
            try{Thread.sleep(10);}catch(Exception ee){}
            }
            plot_point(x[i], y[i]);
        }
        gg.drawString("finish", 0 ,10);
    }
    public void calculate_1(double xi, double yi, double vxi, double vyi, double t, double dt){
        double[] x = new double[(int)(t/dt)];
        double[] y = new double[(int)(t/dt)];
        double[] vx = new double[(int)(t/dt)];
        double[] vy = new double[(int)(t/dt)];
        x[0] = xi;
        y[0] = 0;
        vx[0] = 0;
        vy[0] = 2*Math.PI/Math.sqrt(xi);
        
        for(int i = 0; i < x.length-1; i++){
            double r = Math.sqrt(x[i]*x[i]+y[i]*y[i]);
            vx[i+1] = vx[i] - 4 * Math.PI * Math.PI * x[i] * dt / (r * r * r);
            x[i+1] = x[i] + vx[i+1] * dt;
            vy[i+1] = vy[i] - 4 * Math.PI * Math.PI * y[i] * dt / (r * r * r);
            y[i+1] = y[i] + vy[i+1] * dt;
            
            //if(i % 500 == 0){    
            //try{Thread.sleep(10);}catch(Exception ee){}
            //}
            plot_point(x[i], y[i]);
            if(i > Math.pow(xi, 3.0/2.0)/dt){
                break;
            }
        }
        gg.drawString("finish", 0 ,10);
    }
    
    public void calculate_2(double xi, double yi, double vxi, double vyi, double t, double dt, double e){
        double[] x = new double[(int)(t/dt)];
        double[] y = new double[(int)(t/dt)];
        double[] vx = new double[(int)(t/dt)];
        double[] vy = new double[(int)(t/dt)];
        x[0] = xi;
        y[0] = 0;
        vx[0] = 0;
        vy[0] = 2*Math.PI*xi;
        double a = xi;
        double b = a * Math.sqrt(1-e*e);
        for(int i = 0; i < x.length-1; i++){
            double r = Math.sqrt((x[i]*x[i])/(a*a)+(y[i]*y[i])/(b*b));
            vx[i+1] = vx[i] - 4 * Math.PI * Math.PI * x[i] * dt / (r * r * r);
            x[i+1] = x[i] + vx[i+1] * dt;
            vy[i+1] = vy[i] - 4 * Math.PI * Math.PI * y[i] * dt / (r * r * r);
            y[i+1] = y[i] + vy[i+1] * dt;
            
            /*if(i % 50 == 0){    
            try{Thread.sleep(10);}catch(Exception ee){}
            }*/
            plot_point(x[i], y[i]);
            if(i > Math.pow(xi, 1.5)/dt){
                System.out.println(Math.pow(xi, 3.0/2.0)*365);
                break;
            }
        }
        gg.drawString("finish", 0 ,10);
    }
    
    

    
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        panel_plot_1.update(gg);
        double xx = Double.parseDouble(tf_x.getText());
        double yy = Double.parseDouble(tf_y.getText());
        double vxx = Double.parseDouble(tf_vx.getText());
        double vyy = Double.parseDouble(tf_vy.getText());
        init_plot(0, 0, 10, 10, 50, 50, panel_plot_1);
        set_pen_size(2);
        set_pen_color(Color.LIGHT_GRAY);
        calculate_1(0.39, yy, vxx, vyy, 500, 0.001);
        draw_string("Mercury", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-10);
        set_pen_color(Color.ORANGE);
        calculate_1(0.72, yy, vxx, vyy, 500, 0.001);
        draw_string("Venus", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-20);
        set_pen_color(Color.GREEN);
        calculate_1(1.00, yy, vxx, vyy, 500, 0.001);
        draw_string("Earth", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-30);
        set_pen_color(Color.RED);
        calculate_1(1.52, yy, vxx, vyy, 500, 0.001);
        draw_string("Mars", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-40);
        set_pen_color(Color.DARK_GRAY);
        calculate_1(5.20, yy, vxx, vyy, 500, 0.001);
        draw_string("Jupiter", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-50);
        set_pen_color(Color.YELLOW);
        calculate_1(9.54, yy, vxx, vyy, 500, 0.001);
        draw_string("Saturn", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-60);
        set_pen_color(Color.CYAN);
        calculate_1(19.19, yy, vxx, vyy, 500, 0.001);
        draw_string("Uranus", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-70);
        set_pen_color(Color.BLUE);
        calculate_1(30.06, yy, vxx, vyy, 500, 0.001);
        draw_string("Naptune", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-80);
        set_pen_color(Color.BLACK);
        calculate_1(39.53, yy, vxx, vyy, 500, 0.001);
        draw_string("Pluto", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-90);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void tf_xActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_xActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_xActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        panel_plot_1.update(gg);
        double xx = Double.parseDouble(tf_x.getText());
        double yy = Double.parseDouble(tf_y.getText());
        double vxx = Double.parseDouble(tf_vx.getText());
        double vyy = Double.parseDouble(tf_vy.getText());
        init_plot(0, 0, 10, 10, 50, 50, panel_plot_1);
        set_pen_size(2);
        set_pen_color(Color.LIGHT_GRAY);
        calculate_2(0.39, yy, vxx, vyy, 500, 0.0001, 0.206);
        draw_string("Mercury", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-10);
        set_pen_color(Color.ORANGE);
        calculate_2(0.72, yy, vxx, vyy, 500, 0.0001, 0.007);
        draw_string("Venus", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-20);
        set_pen_color(Color.GREEN);
        calculate_2(1.00, yy, vxx, vyy, 500, 0.001, 0.017);
        draw_string("Earth", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-30);
        set_pen_color(Color.RED);
        calculate_2(1.52, yy, vxx, vyy, 500, 0.001, 0.093);
        draw_string("Mars", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-40);
        set_pen_color(Color.DARK_GRAY);
        calculate_2(5.20, yy, vxx, vyy, 500, 0.001, 0.048);
        draw_string("Jupiter", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-50);
        set_pen_color(Color.YELLOW);
        calculate_2(9.54, yy, vxx, vyy, 500, 0.001, 0.056);
        draw_string("Saturn", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-60);
        set_pen_color(Color.CYAN);
        calculate_2(19.19, yy, vxx, vyy, 500, 0.001, 0.046);
        draw_string("Uranus", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-70);
        set_pen_color(Color.BLUE);
        calculate_2(30.06, yy, vxx, vyy, 500, 0.001, 0.010);
        draw_string("Naptune", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-80);
        set_pen_color(Color.BLACK);
        calculate_2(39.53, yy, vxx, vyy, 500, 0.001, 0.248);
        draw_string("Pluto", panel_plot_1.getWidth()/2-50, panel_plot_1.getHeight()/2-90);
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void tf_vyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_vyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_vyActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        panel_plot_1.update(gg);
        double xx = Double.parseDouble(tf_x.getText());
        double yy = Double.parseDouble(tf_y.getText());
        double vxx = Double.parseDouble(tf_vx.getText());
        double vyy = Double.parseDouble(tf_vy.getText());
        init_plot(0, 0, 80, 80, 50, 50, panel_plot_1);
        set_pen_color(new Color(155, 0, 155));
        set_pen_size(2);
        calculate(xx, yy, vxx, vyy, 50, 0.001);
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    
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
            java.util.logging.Logger.getLogger(planetary_motion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(planetary_motion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(planetary_motion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(planetary_motion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new planetary_motion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JPanel panel_plot_1;
    private javax.swing.JTextField tf_vx;
    private javax.swing.JTextField tf_vy;
    private javax.swing.JTextField tf_x;
    private javax.swing.JTextField tf_y;
    // End of variables declaration//GEN-END:variables
}
