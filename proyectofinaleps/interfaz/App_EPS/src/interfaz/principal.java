


package interfaz;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;

 
public class principal extends javax.swing.JFrame {
    
   
 private String dato;
    public principal() {
        initComponents();
        
      
    }
    
    public void setDatos(String username, int userId) {
        
        usuario.setText("Usuario: " + username);
        Idusuario.setText("ID: " + userId);
    
    }
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        fondo1 = new javax.swing.JDesktopPane();
        pantalla = new javax.swing.JDesktopPane();
        coso1 = new javax.swing.JPanel();
        Btnhistorial = new javax.swing.JButton();
        Btnmedicamentos = new javax.swing.JButton();
        Botoncitas = new javax.swing.JButton();
        usuario = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Idusuario = new javax.swing.JLabel();

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

        Btnhistorial.setText("Historial medico");
        Btnhistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnhistorialActionPerformed(evt);
            }
        });
        coso1.add(Btnhistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        Btnmedicamentos.setText("Medicamentos");
        Btnmedicamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnmedicamentosActionPerformed(evt);
            }
        });
        coso1.add(Btnmedicamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 130, -1));

        Botoncitas.setText("Citas Medicas");
        Botoncitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotoncitasActionPerformed(evt);
            }
        });
        coso1.add(Botoncitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 20, -1, -1));

        usuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        usuario.setForeground(new java.awt.Color(255, 255, 255));
        usuario.setText("jLabel1");
        coso1.add(usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, -1));
        coso1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, -1));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        coso1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));

        Idusuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Idusuario.setForeground(new java.awt.Color(255, 255, 255));
        Idusuario.setText("jLabel3");
        coso1.add(Idusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));

        getContentPane().add(coso1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotoncitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotoncitasActionPerformed
      
        Citas_medicas pc= new Citas_medicas();
        pc.setSize(670, 480);
        pc.setLocation(0, 0);
        pantalla.removeAll();
        pantalla.add(pc,BorderLayout.CENTER);
        pantalla.revalidate();
        pantalla.repaint();
    }//GEN-LAST:event_BotoncitasActionPerformed

    private void BtnhistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnhistorialActionPerformed
        Historial hm= new Historial();
        hm.setSize(670, 480);
        hm.setLocation(0, 0);
        pantalla.removeAll();
        pantalla.add(hm,BorderLayout.CENTER);
        pantalla.revalidate();
        pantalla.repaint();
    }//GEN-LAST:event_BtnhistorialActionPerformed

    private void BtnmedicamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnmedicamentosActionPerformed
        Medicamentos md= new Medicamentos();
        md.setSize(670, 480);
        md.setLocation(0, 0);
        pantalla.removeAll();
        pantalla.add(md,BorderLayout.CENTER);
        pantalla.revalidate();
        pantalla.repaint();
    }//GEN-LAST:event_BtnmedicamentosActionPerformed

    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Botoncitas;
    private javax.swing.JButton Btnhistorial;
    private javax.swing.JButton Btnmedicamentos;
    private javax.swing.JLabel Idusuario;
    private javax.swing.JPanel coso1;
    private javax.swing.JDesktopPane fondo1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JDesktopPane pantalla;
    private javax.swing.JLabel usuario;
    // End of variables declaration//GEN-END:variables
}
