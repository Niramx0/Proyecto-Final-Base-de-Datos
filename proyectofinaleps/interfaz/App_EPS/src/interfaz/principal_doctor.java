
package interfaz;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;

 
public class principal_doctor extends javax.swing.JFrame {

    /**
     * Creates new form proceso
     */
    public principal_doctor() {
        initComponents();
        Citas_medico pc= new Citas_medico();
        pc.setSize(670, 480);
        pc.setLocation(0, 0);
        pantalla.removeAll();
        pantalla.add(pc,BorderLayout.CENTER);
        pantalla.revalidate();
        pantalla.repaint();
        
    }
    public void setDatos(String username, int doctorId) {
        usuariodoc.setText("Usuario: " + username);
        iddoc.setText("ID: " + doctorId);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        fondo1 = new javax.swing.JDesktopPane();
        pantalla = new javax.swing.JDesktopPane();
        coso1 = new javax.swing.JPanel();
        Btnhistorial = new javax.swing.JButton();
        Botoncitas = new javax.swing.JButton();
        usuariodoc = new javax.swing.JLabel();
        iddoc = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));

        fondo1.setBackground(new java.awt.Color(153, 153, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(fondo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondo1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pantalla.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(pantalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 670, 480));

        coso1.setBackground(new java.awt.Color(0, 51, 102));
        coso1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Btnhistorial.setText("Diagnosticos");
        Btnhistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnhistorialActionPerformed(evt);
            }
        });
        coso1.add(Btnhistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        Botoncitas.setText("Citas Medicas");
        Botoncitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotoncitasActionPerformed(evt);
            }
        });
        coso1.add(Botoncitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 20, -1, -1));

        usuariodoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        usuariodoc.setForeground(new java.awt.Color(255, 255, 255));
        usuariodoc.setText("jLabel1");
        coso1.add(usuariodoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, -1));

        iddoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        iddoc.setForeground(new java.awt.Color(255, 255, 255));
        iddoc.setText("jLabel2");
        coso1.add(iddoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, -1, -1));

        getContentPane().add(coso1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotoncitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotoncitasActionPerformed
       Citas_medico pc= new Citas_medico();
        pc.setSize(670, 480);
        pc.setLocation(0, 0);
        pantalla.removeAll();
        pantalla.add(pc,BorderLayout.CENTER);
        pantalla.revalidate();
        pantalla.repaint();
    }//GEN-LAST:event_BotoncitasActionPerformed

    private void BtnhistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnhistorialActionPerformed
        Historial_Medico hm= new Historial_Medico();
        hm.setSize(670, 480);
        hm.setLocation(0, 0);
        pantalla.removeAll();
        pantalla.add(hm,BorderLayout.CENTER);
        pantalla.revalidate();
        pantalla.repaint();
    }//GEN-LAST:event_BtnhistorialActionPerformed

    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal_doctor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Botoncitas;
    private javax.swing.JButton Btnhistorial;
    private javax.swing.JPanel coso1;
    private javax.swing.JDesktopPane fondo1;
    private javax.swing.JLabel iddoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JDesktopPane pantalla;
    private javax.swing.JLabel usuariodoc;
    // End of variables declaration//GEN-END:variables
}
