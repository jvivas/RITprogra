/*
 * Instituto Tecnológico de Costa Rica
 * 2da Tarea Programada de RIT 2do Semestre 2013
 * Profesor: Jose Enrique Araya Monge
 * Estudiantes:
 * Jorge Vivas
 * Emanuel Avendaño
 */

package Interface;
import Business.BusinessLogic;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author eavendano
 */
public class FrmFile extends javax.swing.JFrame {

    /**
     * Creates new form FrmFile
     */
    
    private JFileChooser _FileChooser;
    private File SelectFile;
    private BusinessLogic _BusinessLogic;
    Executor executor = java.util.concurrent.Executors.newSingleThreadExecutor();
    int _PrefijoConsulta = 0;
    

    public FrmFile() {
        initComponents();
        _FilePath.setText("/home/eavendano/Escritorio/man");
        _FileChooser = new JFileChooser();
        _FileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        _BusinessLogic = new BusinessLogic();
        this.lblImg.setVisible(false);
        //this.pnlImageIcon.add(picLabel);
        //this.pnlImageIcon.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnAbrir = new javax.swing.JButton();
        _FilePath = new javax.swing.JTextField();
        btnPatronSimple = new javax.swing.JButton();
        lblPalabra = new javax.swing.JLabel();
        txfPalabra = new javax.swing.JTextField();
        lblImg = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnPatronOpciones = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnAllPatterns = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnAbrir.setText("Abrir");
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        _FilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _FilePathActionPerformed(evt);
            }
        });

        btnPatronSimple.setText("Patrón Simple");
        btnPatronSimple.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPatronSimpleActionPerformed(evt);
            }
        });

        lblPalabra.setText("Palabra");

        lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ajax-loader.gif"))); // NOI18N

        jButton1.setText("Programación Dinámica");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnPatronOpciones.setText("Patrón Opciones");
        btnPatronOpciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPatronOpcionesActionPerformed(evt);
            }
        });

        jButton2.setText("Autómata");

        btnAllPatterns.setText("Patrones");
        btnAllPatterns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllPatternsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblPalabra)
                        .addComponent(btnAbrir)
                        .addComponent(_FilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                        .addComponent(txfPalabra)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPatronSimple, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPatronOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblImg)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(btnAllPatterns, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnAbrir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_FilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPalabra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txfPalabra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(lblImg)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPatronSimple)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPatronOpciones)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(btnAllPatterns))
                        .addGap(0, 20, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        int returnValue = _FileChooser.showOpenDialog(FrmFile.this);
        if (returnValue == _FileChooser.APPROVE_OPTION) {
            SelectFile = _FileChooser.getSelectedFile();
            _FilePath.setText(SelectFile.toString());
        }
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void _FilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__FilePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event__FilePathActionPerformed

    private void btnPatronSimpleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPatronSimpleActionPerformed
        this.btnPatronSimple.setEnabled(false);
        lblImg.setVisible(true);
        
        if(!_FilePath.getText().equals("")){
            if(!txfPalabra.getText().equals("")){
                _BusinessLogic.setDirectoryPath(_FilePath.getText());
                _BusinessLogic.setPatronUsuario(txfPalabra.getText());
                try {
                    String executionResult = _BusinessLogic.EjecutarPatronSimple();
                    if(this._BusinessLogic.getProcessOperationState() == 1){
                        JOptionPane.showMessageDialog(rootPane, executionResult, "Finalizacion de la busqueda.", 1);
                        FrmSearchResult frmSearchResult = new FrmSearchResult();
                        frmSearchResult.setMatchesInFileLIne(this._BusinessLogic.getMatchesInFileLIne());
                        frmSearchResult.InsertResult(this._BusinessLogic.getMatchLineInfo());
                        frmSearchResult.setVisible(true);                        
                    } else {
                        JOptionPane.showMessageDialog(rootPane, executionResult, "Finalizacion de la busqueda.", 1);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FrmFile.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }else {
                JOptionPane.showMessageDialog(rootPane, "Digite la palabra o patron que desea buscar.");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Debe seleccionar el campo del directorio.");
        }
        this.btnPatronSimple.setEnabled(true);
        lblImg.setVisible(false);
    }//GEN-LAST:event_btnPatronSimpleActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.jButton1.setEnabled(false);
        lblImg.setVisible(true);
        
        if(!_FilePath.getText().equals("")){
            if(!txfPalabra.getText().equals("")){
                _BusinessLogic.setDirectoryPath(_FilePath.getText());
                _BusinessLogic.setPatronUsuario(txfPalabra.getText());
                try {
                    //String executionResult = _BusinessLogic.EjecutarProgDinamica();
                    String executionResult = _BusinessLogic.EjecutarProgDinamica();
                    if(this._BusinessLogic.getProcessOperationState() == 1){
                        JOptionPane.showMessageDialog(rootPane, executionResult, "Finalizacion de la busqueda.", 1);
                        FrmSearchResult frmSearchResult = new FrmSearchResult();
                        frmSearchResult.setMatchesInFileLIne(this._BusinessLogic.getMatchesInFileLIne());
                        frmSearchResult.InsertResult(this._BusinessLogic.getMatchLineInfo());
                        frmSearchResult.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, executionResult, "Finalizacion de la busqueda.", 1);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FrmFile.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }else {
                JOptionPane.showMessageDialog(rootPane, "Digite la palabra o patron que desea buscar.");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Debe seleccionar el campo del directorio.");
        }
        this.jButton1.setEnabled(true);
        lblImg.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnPatronOpcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPatronOpcionesActionPerformed
        this.btnPatronOpciones.setEnabled(false);
        lblImg.setVisible(true);
        
        if(!_FilePath.getText().equals("")){
            if(!txfPalabra.getText().equals("")){
                _BusinessLogic.setDirectoryPath(_FilePath.getText());
                _BusinessLogic.setPatronUsuario(txfPalabra.getText());
                try {
                    String executionResult = _BusinessLogic.EjecutarPatronOpciones();
                    if(this._BusinessLogic.getProcessOperationState() == 1){
                        JOptionPane.showMessageDialog(rootPane, executionResult, "Finalizacion de la busqueda.", 1);
                        FrmSearchResult frmSearchResult = new FrmSearchResult();
                        frmSearchResult.setMatchesInFileLIne(this._BusinessLogic.getMatchesInFileLIne());
                        frmSearchResult.InsertResult(this._BusinessLogic.getMatchLineInfo());
                        frmSearchResult.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, executionResult, "Finalizacion de la busqueda.", 1);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FrmFile.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }else {
                JOptionPane.showMessageDialog(rootPane, "Digite la palabra o patron que desea buscar.");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Debe seleccionar el campo del directorio.");
        }
        this.btnPatronOpciones.setEnabled(true);
        lblImg.setVisible(false);
    }//GEN-LAST:event_btnPatronOpcionesActionPerformed

    private void btnAllPatternsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllPatternsActionPerformed
        // TODO add your handling code here:
        this.btnAllPatterns.setEnabled(false);
        lblImg.setVisible(true);
        
        if(!_FilePath.getText().equals("")){
            if(!txfPalabra.getText().equals("")){
                System.out.println("Palbra = " + txfPalabra.getText());
                String[] tokenPattern = txfPalabra.getText().split(" ");                
                _BusinessLogic.setDirectoryPath(_FilePath.getText());
                _PrefijoConsulta++;
                for(int i = 0; i < tokenPattern.length;i++){               
                    _BusinessLogic.setPatronUsuario(tokenPattern[i]);
                try {
                    String executionResult = _BusinessLogic.EjecutarPatrones(_PrefijoConsulta);
                    if(this._BusinessLogic.getProcessOperationState() == 1){
                        JOptionPane.showMessageDialog(rootPane, executionResult, "Finalizacion de la busqueda.", 1);
                        FrmSearchResult frmSearchResult = new FrmSearchResult();
                        frmSearchResult.setMatchesInFileLIne(this._BusinessLogic.getMatchesInFileLIne());
                        frmSearchResult.InsertResult(this._BusinessLogic.getMatchLineInfo());
                        frmSearchResult.setVisible(true);                        
                    } else {
                        JOptionPane.showMessageDialog(rootPane, executionResult, "Finalizacion de la busqueda.", 1);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FrmFile.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
                }
            }else {
                JOptionPane.showMessageDialog(rootPane, "Digite la palabra o patron que desea buscar.");
                _PrefijoConsulta--;
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Debe seleccionar el campo del directorio.");
            _PrefijoConsulta--;
        }
        this.btnAllPatterns.setEnabled(true);
        lblImg.setVisible(false);
        
    }//GEN-LAST:event_btnAllPatternsActionPerformed

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
            java.util.logging.Logger.getLogger(FrmFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmFile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField _FilePath;
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnAllPatterns;
    private javax.swing.JButton btnPatronOpciones;
    private javax.swing.JButton btnPatronSimple;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblPalabra;
    private javax.swing.JTextField txfPalabra;
    // End of variables declaration//GEN-END:variables
}
