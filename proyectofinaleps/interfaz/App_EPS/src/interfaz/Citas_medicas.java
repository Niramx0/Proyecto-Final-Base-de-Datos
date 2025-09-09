/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package interfaz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;// Para el JDateChooser (Calendario)
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import parte1.conexion;





/**
 *
 * @author USUARIO
 */
public final class Citas_medicas extends javax.swing.JPanel {
    
    

    conexion cx = new conexion("bases_proyecto");
    
    
    public Citas_medicas() {
       
        initComponents();
        cargarDoctores();
       
}
        
     
    

    // Constructor de Citas_medicas que recibe los datos
    
        

        // Crear los JLabel y establecer el texto
       
        // Añadir los labels al panel (asegúrate de tener un layout apropiado)
        

        // Aquí puedes agregar el código adicional para colocar los labels en un layout específico.
    
    
        
        
     
    
    public void cargarDoctores() {
    String query = "SELECT nombre FROM medicos"; // Suponiendo que la tabla se llama 'medicos' y el campo es 'nombre'
    if (comboDoctores.getItemCount() == 0) {
    
    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(query);
         ResultSet rs = pst.executeQuery()) {
        
        while (rs.next()) {
            comboDoctores.addItem(rs.getString("nombre")); // Agregar cada nombre al JComboBox
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    }
    // Método para asignar la cita
public void asignarCita() {
    // Obtener los valores de los campos
    String idUsuarioText = txtid.getText(); // Obtener el ID del usuario del JTextField
    String medico = comboDoctores.getSelectedItem().toString(); // Obtener el nombre del médico seleccionado
    Date fecha = dateChooser.getDate(); // Obtener la fecha de la cita desde el JDateChooser

    // Verificar si los campos están completos
    if (idUsuarioText.isEmpty() || medico.isEmpty() || fecha == null) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        return;
    }

    // Convertir el ID de usuario a un entero
    int idUsuario = Integer.parseInt(idUsuarioText);

    // Estado de la cita
    String estado = "1"; // Estado de la cita

    String query = "INSERT INTO citas_medicas (id_usuario, id_medico, fecha, estado_cita) VALUES (?, ?, ?, ?)";

    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(query)) {

        // Obtener el ID del médico desde la base de datos basado en el nombre
        String idMedicoQuery = "SELECT id FROM medicos WHERE nombre = ?";
        try (PreparedStatement pst2 = conn.prepareStatement(idMedicoQuery)) {
            pst2.setString(1, medico);
            ResultSet rs = pst2.executeQuery();
            if (rs.next()) {
                int idMedico = rs.getInt("id");

                // Establecer los parámetros de la consulta
                pst.setInt(1, idUsuario);
                pst.setInt(2, idMedico);
                pst.setDate(3, new java.sql.Date(fecha.getTime())); // Convertir Date a java.sql.Date
                pst.setString(4, estado);

                // Ejecutar la consulta
                pst.executeUpdate();

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(this, "Cita asignada correctamente.");

                // Ahora que la cita se ha asignado, actualizar la tabla
                cargarCitas(idUsuario);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el médico seleccionado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public void cargarCitas(int idUsuario) {
       // Consulta SQL para obtener las citas solo para el usuario especificado y donde estado_cita = 1
    String query = "SELECT citas_medicas.id, citas_medicas.fecha, medicos.nombre AS medico, citas_medicas.estado_cita " +
                   "FROM citas_medicas " +
                   "JOIN medicos ON citas_medicas.id_medico = medicos.id " +  // Relacionando la columna id_medico con id
                   "WHERE citas_medicas.id_usuario = ? AND citas_medicas.estado_cita = 1"; // Filtramos solo las citas con estado_cita = 1

    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(query)) {

        // Establecer el parámetro de usuario
        pst.setInt(1, idUsuario); // Usar el ID del usuario que se pasa al método

        try (ResultSet rs = pst.executeQuery()) {
            // Obtener el modelo de la tabla existente
            javax.swing.table.TableModel modelo = tablacitas.getModel();
            
            // Si el modelo es un DefaultTableModel, podemos actualizar las filas directamente
            if (modelo instanceof DefaultTableModel) {
                DefaultTableModel tableModel = (DefaultTableModel) modelo;
                tableModel.setRowCount(0); // Limpiar los datos anteriores

                while (rs.next()) {
                    // Obtener los datos de cada cita
                    int idCita = rs.getInt("id"); // Obtener el ID de la cita
                    String fecha = rs.getString("fecha");
                    String medico = rs.getString("medico");
                    String estado = rs.getString("estado_cita");

                    // Agregar los datos a la tabla, incluyendo el ID de la cita
                    tableModel.addRow(new Object[]{idCita, fecha, medico, estado});
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar las citas.");
    }
}

   private void cancelarCita(int idUsuario, int idCita) {
    String updateCitaQuery = "UPDATE citas_medicas SET estado_cita = 2 WHERE id = ?"; // Actualizamos el estado a "cancelada"
    String insertHistorialQuery = "INSERT INTO historial_medico (id_cita, id_usuario,  diagnostico, medicamentos) VALUES (?, ?, ?, ?)"; // Insertamos en historial

    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(updateCitaQuery)) {

        // Establecer el ID de la cita para actualizar el estado
        pst.setInt(1, idCita);

        // Ejecutar la actualización para cambiar el estado de la cita
        pst.executeUpdate();

        // Ahora insertamos en la tabla historial_medico
        try (PreparedStatement pstHistorial = conn.prepareStatement(insertHistorialQuery)) {
            pstHistorial.setInt(1, idCita); // ID del usuario
            pstHistorial.setInt(2, idUsuario); // ID de la cita
            pstHistorial.setString(3, "Cancelada"); // Observación
            pstHistorial.setInt(4, 0); // Medicamentos = 0 (asumiendo que es 0 cuando se cancela)

            // Ejecutar la inserción en historial_medico
            pstHistorial.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "Cita cancelada correctamente y registrada en historial médico.");

        // Actualizar la tabla con las citas del usuario (actualizar visualización)
        cargarCitas(idUsuario);  // Llamamos a cargarCitas para actualizar la tabla con los datos actualizados

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cancelar la cita.");
    }
}

public void cargarCitasFiltradas() {
    String idUsuarioText = txtid.getText(); // Obtener el ID del usuario desde el JTextField

    if (idUsuarioText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID del usuario.");
        return;
    }

    int idUsuario = Integer.parseInt(idUsuarioText); // Convertir a entero

    // Consulta SQL para obtener solo las citas del usuario que están en estado "pendiente" (estado_cita = 1)
    String query = "SELECT citas_medicas.id, citas_medicas.fecha, medicos.nombre AS medico, citas_medicas.estado_cita " +
                   "FROM citas_medicas " +
                   "JOIN medicos ON citas_medicas.id_medico = medicos.id " +
                   "WHERE citas_medicas.id_usuario = ? AND citas_medicas.estado_cita = 1"; // Filtrar por ID de usuario y estado = 1

    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(query)) {

        pst.setInt(1, idUsuario); // Pasar el ID del usuario como parámetro

        try (ResultSet rs = pst.executeQuery()) {
            // Obtener el modelo de la tabla existente
            javax.swing.table.TableModel modelo = tablacitas.getModel();

            // Si el modelo es un DefaultTableModel, podemos actualizar las filas directamente
            if (modelo instanceof DefaultTableModel) {
                DefaultTableModel tableModel = (DefaultTableModel) modelo;
                tableModel.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

                while (rs.next()) {
                    // Obtener los datos de cada cita
                    int idCita = rs.getInt("id");
                    String fecha = rs.getString("fecha");
                    String medico = rs.getString("medico");
                    String estado = rs.getString("estado_cita");

                    // Agregar los datos a la tabla solo si el estado es "1" (pendiente)
                    tableModel.addRow(new Object[]{idCita, fecha, medico, estado});
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar las citas filtradas.");
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
        tablacitas = new javax.swing.JTable();
        btncancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        comboDoctores = new javax.swing.JComboBox<>();
        btnasignar = new javax.swing.JButton();
        txtid = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnfiltrar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(670, 480));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablacitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Fecha", "Medico", "Estado cita"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablacitas);
        if (tablacitas.getColumnModel().getColumnCount() > 0) {
            tablacitas.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 206, 430, 252));

        btncancelar.setText("Cancelar");
        btncancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btncancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, -1, 20));

        jLabel1.setText("Citas asignadas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 184, -1, -1));
        jPanel1.add(dateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 110, -1));

        jPanel1.add(comboDoctores, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 90, -1));

        btnasignar.setText("Asignar");
        btnasignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnasignarActionPerformed(evt);
            }
        });
        jPanel1.add(btnasignar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, -1, -1));
        jPanel1.add(txtid, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 80, -1));

        jLabel3.setText("Id:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, -1));

        jLabel2.setText("1.pendiente");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, -1, -1));

        btnfiltrar.setText("Filtrar");
        btnfiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfiltrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnfiltrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        jLabel6.setText("Doctor:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, -1));

        jLabel7.setText("Fecha:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, -1, -1));

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

    private void btnasignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnasignarActionPerformed
            asignarCita();
           
         

    }//GEN-LAST:event_btnasignarActionPerformed

    private void btncancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarActionPerformed

  int filaSeleccionada = tablacitas.getSelectedRow(); // Obtener la fila seleccionada

    if (filaSeleccionada == -1) {
        // Si no se selecciona ninguna fila
        JOptionPane.showMessageDialog(this, "Por favor, seleccione una cita para cancelar.");
        return;
    }

    // Obtener el ID de la cita desde la tabla (la primera columna es el ID de la cita)
    int idCita = Integer.parseInt(tablacitas.getValueAt(filaSeleccionada, 0).toString());

    // Obtener el ID del usuario desde el campo correspondiente
    String idUsuarioText = txtid.getText(); // Suponiendo que el ID del usuario está en un JTextField
    int idUsuario = Integer.parseInt(idUsuarioText); // Convertirlo a entero

    // Llamar al método para cancelar la cita
    cancelarCita(idUsuario, idCita);
    }//GEN-LAST:event_btncancelarActionPerformed

    private void btnfiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfiltrarActionPerformed
    cargarCitasFiltradas();
    }//GEN-LAST:event_btnfiltrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnasignar;
    private javax.swing.JButton btncancelar;
    private javax.swing.JButton btnfiltrar;
    private javax.swing.JComboBox<String> comboDoctores;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablacitas;
    private javax.swing.JTextField txtid;
    // End of variables declaration//GEN-END:variables
}
