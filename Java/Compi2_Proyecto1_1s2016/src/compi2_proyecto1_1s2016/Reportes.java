/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi2_proyecto1_1s2016;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mac
 */
public class Reportes extends javax.swing.JFrame {

    /**
     * Creates new form Reportes
     */
    public Reportes() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        getContentPane().setLayout(null);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 110, 610, 260);

        jButton2.setFont(new java.awt.Font("SF Atarian System", 0, 24)); // NOI18N
        jButton2.setText("Analizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(20, 60, 170, 40);

        setSize(new java.awt.Dimension(671, 456));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!Compi2_Proyecto1_1s2016.TablaTransiciones.isEmpty()) {
            ArrayList<String> nombresColumnas = new ArrayList<>();
            DefaultTableModel model = new DefaultTableModel();
            JTable tabla = jTable1;
            tabla.setModel(model);
            model.addColumn("# Estado");
            for (Terminal Terminal : Compi2_Proyecto1_1s2016.Terminales) {
                model.addColumn("" + Terminal.nombre);
                nombresColumnas.add("" + Terminal.nombre);
            }
            int size = Compi2_Proyecto1_1s2016.NoTerminales.size();
            for (int i = 0; i < size - 1; i++) {
                model.addColumn("" + Compi2_Proyecto1_1s2016.NoTerminales.get(i).nombre);
                nombresColumnas.add("" + Compi2_Proyecto1_1s2016.NoTerminales.get(i).nombre);
            }
            for (Conjunto Conjunto : Compi2_Proyecto1_1s2016.Conjuntos) {
                model.addRow(new Object[]{Conjunto.numeroConjunto});
            }

            for (itemTabla Transicion : Compi2_Proyecto1_1s2016.TablaTransiciones) {
                try {
                    model.setValueAt("" + Transicion.DestinoReduccionDesplazamiento, Transicion.numeroEstado, 1 + nombresColumnas.indexOf(((NoTerminal) Transicion.simboloConElQueSeDesplaza).nombre));
                } catch (Exception e) {
                    if (Transicion.tipo == 2) {
                        if (null == model.getValueAt(Transicion.numeroEstado, 1 + nombresColumnas.indexOf(((Terminal) Transicion.simboloConElQueSeDesplaza).nombre))) {
                            model.setValueAt("R" + Transicion.DestinoReduccionDesplazamiento, Transicion.numeroEstado, 1 + nombresColumnas.indexOf(((Terminal) Transicion.simboloConElQueSeDesplaza).nombre));
                        } else {
                            model.setValueAt(model.getValueAt(Transicion.numeroEstado, 1 + nombresColumnas.indexOf(((Terminal) Transicion.simboloConElQueSeDesplaza).nombre)) + "/R" + Transicion.DestinoReduccionDesplazamiento, Transicion.numeroEstado, 1 + nombresColumnas.indexOf(((Terminal) Transicion.simboloConElQueSeDesplaza).nombre));
                        }
                    } else {
                        if (null == model.getValueAt(Transicion.numeroEstado, 1 + nombresColumnas.indexOf(((Terminal) Transicion.simboloConElQueSeDesplaza).nombre))) {
                            model.setValueAt("D" + Transicion.DestinoReduccionDesplazamiento, Transicion.numeroEstado, 1 + nombresColumnas.indexOf(((Terminal) Transicion.simboloConElQueSeDesplaza).nombre));
                        } else {
                            model.setValueAt(model.getValueAt(Transicion.numeroEstado, 1 + nombresColumnas.indexOf(((Terminal) Transicion.simboloConElQueSeDesplaza).nombre)) + "/D" + Transicion.DestinoReduccionDesplazamiento, Transicion.numeroEstado, 1 + nombresColumnas.indexOf(((Terminal) Transicion.simboloConElQueSeDesplaza).nombre));
                        }
                    }
                }

            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void start() {
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
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reportes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
