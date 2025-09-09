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
public class Historial_Medico extends javax.swing.JPanel {

    /**
     * Creates new form Historial_medico
     */
    public Historial_Medico() {
        initComponents();
        cargarMedicamentos();
    }
            conexion cx = new conexion("bases_proyecto");

    public void cargarHistorialMedicoDoctor() {
    String idDoctorText = txtdoctorid.getText(); // Obtener el ID del doctor desde el JTextField

    if (idDoctorText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID del doctor.");
        return;
    }

    int idDoctor = Integer.parseInt(idDoctorText); // Convertir a entero

    // Consulta SQL para obtener solo las citas en estado 1 (pendiente)
    String query = "SELECT citas_medicas.id AS id_cita, citas_medicas.fecha, paciente.nombre AS paciente " +
                   "FROM citas_medicas " +
                   "JOIN paciente ON citas_medicas.id_usuario = paciente.id " + 
                   "WHERE citas_medicas.id_medico = ? " +  // Filtrar por ID del doctor
                   "AND citas_medicas.estado_cita = 1"; // Solo citas en estado "1" (pendiente)

    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(query)) {

        pst.setInt(1, idDoctor); // Pasar el ID del doctor como parámetro

        try (ResultSet rs = pst.executeQuery()) {
            // Obtener el modelo de la tabla existente
            javax.swing.table.TableModel modelo = tablahistorialmedicoo.getModel();

            // Si el modelo es un DefaultTableModel, podemos actualizar las filas directamente
            if (modelo instanceof DefaultTableModel) {
                DefaultTableModel tableModel = (DefaultTableModel) modelo;
                tableModel.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

                while (rs.next()) {
                    // Obtener los datos de cada cita
                    int idCita = rs.getInt("id_cita");
                    String fecha = rs.getString("fecha");
                    String paciente = rs.getString("paciente");

                    // Agregar solo citas en estado 1
                    tableModel.addRow(new Object[]{idCita, fecha, paciente});
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar el historial médico.");
    }
}

    public void cargarMedicamentos() {
    // Consulta SQL para obtener los nombres de los medicamentos
    String query = "SELECT nombre FROM medicamentos";

    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(query);
         ResultSet rs = pst.executeQuery()) {

        combomedicamentos.removeAllItems(); // Limpiar el JComboBox antes de cargar los datos

        while (rs.next()) {
            String nombreMedicamento = rs.getString("nombre"); // Obtener el nombre del medicamento
            combomedicamentos.addItem(nombreMedicamento); // Agregar al JComboBox
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar los medicamentos.");
    }
}
    public boolean validarStock() {
    String medicamentoSeleccionado = combomedicamentos.getSelectedItem().toString(); // Obtener medicamento seleccionado
    String cantidadText = txtcantidad.getText(); // Obtener la cantidad ingresada en el JTextField

    // Validar que el campo no esté vacío
    if (cantidadText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese una cantidad.");
        return false;
    }

    int cantidadRecetada;
    try {
        cantidadRecetada = Integer.parseInt(cantidadText); // Convertir cantidad ingresada a entero
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Ingrese un número válido en cantidad.");
        return false;
    }

    // Consulta para obtener el stock del medicamento seleccionado
    String query = "SELECT stock FROM medicamentos WHERE nombre = ?";

    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(query)) {

        pst.setString(1, medicamentoSeleccionado); // Establecer el medicamento como parámetro
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            int stockDisponible = rs.getInt("stock"); // Obtener el stock del medicamento

            // Validar si la cantidad ingresada supera el stock disponible
            if (cantidadRecetada > stockDisponible) {
                JOptionPane.showMessageDialog(this, "No hay suficiente stock disponible. Stock actual: " + stockDisponible);
                txtcantidad.requestFocus(); // Regresar al campo de cantidad
                txtcantidad.selectAll(); // Seleccionar el texto para facilitar la corrección
                return false;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener el stock del medicamento.");
        return false;
    }

    return true; // Si pasa todas las validaciones, retorna true
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
    private int obtenerIdMedicamentoDesdeNombre(String nombreMedicamento) {
    String query = "SELECT id FROM medicamentos WHERE nombre = ?";
    int idMedicamento = -1;

    try (Connection conn = cx.conectar();
         PreparedStatement pst = conn.prepareStatement(query)) {

        pst.setString(1, nombreMedicamento);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            idMedicamento = rs.getInt("id"); // Obtener el ID del medicamento
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al obtener el ID del medicamento.");
    }

    return idMedicamento;
}







    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablahistorialmedicoo = new javax.swing.JTable();
        btnfiltrar = new javax.swing.JButton();
        btnfinalizar = new javax.swing.JButton();
        txtdoctorid = new javax.swing.JTextField();
        txtobservaciones = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        combomedicamentos = new javax.swing.JComboBox<>();
        txtcantidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Fecha", "Medico", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jButton1.setText("Cancelar");

        jButton2.setText("Asignar");

        jLabel1.setText("Citas asignadas");

        jLabel2.setText("Citas Disponibles");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha", "Medico", "Estado cita", "Tipo"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton3.setText("Cancelar");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Id medico:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        tablahistorialmedicoo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id cita", "Fecha", "Paciente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tablahistorialmedicoo);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 188, 493, 252));

        btnfiltrar.setText("Filtrar");
        btnfiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfiltrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnfiltrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, -1, -1));

        btnfinalizar.setText("Finalizar");
        btnfinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfinalizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnfinalizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 307, -1, -1));
        jPanel1.add(txtdoctorid, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 100, -1));
        jPanel1.add(txtobservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 390, 50));

        jLabel5.setText("Diagnostico:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        combomedicamentos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(combomedicamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, -1, -1));
        jPanel1.add(txtcantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 70, -1));

        jLabel3.setText("Cantidad:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, -1, -1));

        jLabel6.setText("Medicamento:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, -1, -1));

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

    private void btnfiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfiltrarActionPerformed
        cargarHistorialMedicoDoctor(); 
    }//GEN-LAST:event_btnfiltrarActionPerformed

    private void btnfinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfinalizarActionPerformed
      
   
   
    // 1️⃣ Validar que se haya seleccionado una fila en la tabla
    int filaSeleccionada = tablahistorialmedicoo.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione una cita de la tabla.");
        return;
    }

    // 2️⃣ Validar que el campo de observaciones no esté vacío
    String diagnostico = txtobservaciones.getText().trim();
    if (diagnostico.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese un diagnóstico en el campo de observaciones.");
        return;
    }

    // 3️⃣ Validar el stock antes de continuar
    if (!validarStock()) {
        return; // Si la validación del stock falla, no continuar
    }

    // 4️⃣ Obtener los valores seleccionados de la tabla
    int idCita = Integer.parseInt(tablahistorialmedicoo.getValueAt(filaSeleccionada, 0).toString());

    // 5️⃣ Obtener el ID del usuario desde la base de datos (basado en id_cita)
    int idUsuario = obtenerIdPacienteDesdeCita(idCita);
    if (idUsuario == -1) {
        JOptionPane.showMessageDialog(this, "Error al obtener el ID del paciente.");
        return;
    }

    // 6️⃣ Obtener el ID del medicamento seleccionado
    int idMedicamento = obtenerIdMedicamentoDesdeNombre(combomedicamentos.getSelectedItem().toString());
    if (idMedicamento == -1) {
        JOptionPane.showMessageDialog(this, "Error al obtener el ID del medicamento.");
        return;
    }

    // 7️⃣ Obtener la cantidad ingresada
    int cantidadRecetada = Integer.parseInt(txtcantidad.getText());

    // 8️⃣ Insertar en historial_medico
    String insertHistorialQuery = "INSERT INTO historial_medico (id_cita, id_usuario, diagnostico, medicamentos, cantidad_med) " +
                                  "VALUES (?, ?, ?, ?, ?)";

    // 9️⃣ Eliminar la cita de la tabla citas_medicas
    String deleteCitaQuery = "DELETE FROM citas_medicas WHERE id = ?";

    try (Connection conn = cx.conectar();
         PreparedStatement pstHistorial = conn.prepareStatement(insertHistorialQuery);
         PreparedStatement pstDeleteCita = conn.prepareStatement(deleteCitaQuery)) {

        // Insertar en historial_medico
        pstHistorial.setInt(1, idCita);
        pstHistorial.setInt(2, idUsuario);
        pstHistorial.setString(3, diagnostico);
        pstHistorial.setInt(4, idMedicamento); // Guardamos el ID del medicamento en lugar del nombre
        pstHistorial.setInt(5, cantidadRecetada);
        pstHistorial.executeUpdate();

        // Eliminar la cita de citas_medicas
        pstDeleteCita.setInt(1, idCita);
        pstDeleteCita.executeUpdate();

        // 🔟 Eliminar la fila de la tabla visual
        DefaultTableModel model = (DefaultTableModel) tablahistorialmedicoo.getModel();
        model.removeRow(filaSeleccionada);

        JOptionPane.showMessageDialog(this, "Cita finalizada y registrada en historial médico.");

        // 🔟 Actualizar la tabla de historial médico y limpiar los campos
        cargarHistorialMedicoDoctor(); // Recargar la tabla de citas
       
        txtobservaciones.setText(""); // Limpiar el campo de observaciones
        txtcantidad.setText(""); // Limpiar el campo de cantidad

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al finalizar la cita.");
    }





    }//GEN-LAST:event_btnfinalizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnfiltrar;
    private javax.swing.JButton btnfinalizar;
    private javax.swing.JComboBox<String> combomedicamentos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable tablahistorialmedicoo;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtdoctorid;
    private javax.swing.JTextField txtobservaciones;
    // End of variables declaration//GEN-END:variables
}
