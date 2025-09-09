/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package interfaz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import parte1.conexion; // Importa la clase de conexión a la base de datos


/**
 *
 * @author USUARIO
 */
public class Citas_medico extends javax.swing.JPanel {
        conexion cx = new conexion("bases_proyecto");


    /**
     * Creates new form Citas_medicas
     */
    public Citas_medico() {
        initComponents();
    }
    public void cargarCitasDoctor() {
    String idDoctorText = txtdoctorid.getText(); // Obtener el ID del doctor desde el JTextField

    if (idDoctorText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID del doctor.");
        return;
    }

    int idDoctor = Integer.parseInt(idDoctorText); // Convertir a entero

    // Consulta SQL para obtener las citas del doctor con estado "pendiente" (estado_cita = 1) y mostrar el id_cita
    String query = "SELECT citas_medicas.id AS id_cita, citas_medicas.fecha, paciente.nombre AS paciente, citas_medicas.estado_cita " +
                   "FROM citas_medicas " +
                   "JOIN paciente ON citas_medicas.id_usuario = paciente.id " + // Relacionando el ID del paciente
                   "WHERE citas_medicas.id_medico = ? AND citas_medicas.estado_cita = 1"; // Filtrar por ID del doctor y estado = 1 (pendiente)

    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(query)) {

        pst.setInt(1, idDoctor); // Pasar el ID del doctor como parámetro

        try (ResultSet rs = pst.executeQuery()) {
            // Obtener el modelo de la tabla existente
            javax.swing.table.TableModel modelo = tablacitasdoctor.getModel();

            // Si el modelo es un DefaultTableModel, podemos actualizar las filas directamente
            if (modelo instanceof DefaultTableModel) {
                DefaultTableModel tableModel = (DefaultTableModel) modelo;
                tableModel.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

                while (rs.next()) {
                    // Obtener los datos de cada cita
                    int idCita = rs.getInt("id_cita"); // ID de la cita
                    String fecha = rs.getString("fecha");
                    String paciente = rs.getString("paciente");
                    String estado = rs.getString("estado_cita");

                    // Agregar los datos a la tabla, incluyendo el ID de la cita
                    tableModel.addRow(new Object[]{idCita, fecha, paciente, estado});
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar las citas del doctor.");
    }
}

    private int obtenerIdPacienteDesdeCita(int idCita) {
    String query = "SELECT id_usuario FROM citas_medicas WHERE id = ?";
    int idPaciente = -1;

    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(query)) {

        pst.setInt(1, idCita);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            idPaciente = rs.getInt("id_usuario");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener el ID del paciente.");
    }

    return idPaciente;
}

 private void cancelarCitaDoctor() {
    int filaSeleccionada = tablacitasdoctor.getSelectedRow(); // Obtener la fila seleccionada

    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione una cita para cancelar.");
        return;
    }

    // Obtener el ID de la cita desde la tabla
    int idCita = Integer.parseInt(tablacitasdoctor.getValueAt(filaSeleccionada, 0).toString());

    // Obtener el ID del paciente desde la base de datos
    int idPaciente = obtenerIdPacienteDesdeCita(idCita); // ⚠ Aquí es donde puede dar el error

    if (idPaciente == -1) {
        JOptionPane.showMessageDialog(this, "No se pudo obtener el ID del paciente.");
        return;
    }

    // Consulta para insertar en historial_medico
    String insertHistorialQuery = "INSERT INTO historial_medico (id_cita, id_usuario, diagnostico, medicamentos, cantidad_med) " +
                                  "VALUES (?, ?, ?, ?, ?)";

    // Consulta para eliminar la cita de la tabla citas_medicas
    String deleteCitaQuery = "DELETE FROM citas_medicas WHERE id = ?";

    try (Connection conn = cx.conectar();
         PreparedStatement pstHistorial = conn.prepareStatement(insertHistorialQuery);
         PreparedStatement pstDeleteCita = conn.prepareStatement(deleteCitaQuery)) {

        // Insertar en historial_medico
        pstHistorial.setInt(1, idCita);
        pstHistorial.setInt(2, idPaciente); // Se usa el ID del paciente
        pstHistorial.setString(3, "Cancelada por doctor"); // Diagnóstico
        pstHistorial.setInt(4, 0); // Medicamentos = 0
        pstHistorial.setInt(5, 0); // Cantidad_med = 0
        pstHistorial.executeUpdate();

        // Eliminar la cita de citas_medicas
        pstDeleteCita.setInt(1, idCita);
        pstDeleteCita.executeUpdate();

        JOptionPane.showMessageDialog(this, "Cita cancelada y registrada en historial médico.");

        // Actualizar la tabla de citas del doctor
        cargarCitasDoctor();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cancelar la cita.");
    }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablacitasdoctor = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtdoctorid = new javax.swing.JTextField();
        btnfiltrar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(670, 480));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablacitasdoctor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id cita", "Fecha", "Paciente", "Estado cita"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablacitasdoctor);
        if (tablacitasdoctor.getColumnModel().getColumnCount() > 0) {
            tablacitasdoctor.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 141, 352, 252));

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 411, -1, -1));

        jLabel1.setText("Citas asignadas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(288, 28, -1, -1));

        txtdoctorid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdoctoridActionPerformed(evt);
            }
        });
        jPanel1.add(txtdoctorid, new org.netbeans.lib.awtextra.AbsoluteConstraints(242, 85, 110, -1));

        btnfiltrar.setText("filtrar");
        btnfiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfiltrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnfiltrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 86, -1, 22));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtdoctoridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdoctoridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdoctoridActionPerformed

    private void btnfiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfiltrarActionPerformed
         cargarCitasDoctor(); 
    }//GEN-LAST:event_btnfiltrarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    
    cancelarCitaDoctor(); // Llamar al método para cancelar la cita


    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnfiltrar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablacitasdoctor;
    private javax.swing.JTextField txtdoctorid;
    // End of variables declaration//GEN-END:variables
}
