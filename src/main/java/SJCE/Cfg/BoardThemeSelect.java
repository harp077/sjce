package SJCE.Cfg;

import static SJCE.XChessFrame.aktion;
import static SJCE.XChessFrame.boardUI;
import static SJCE.XChessFrame.borderPanel;
import static SJCE.XChessFrame.frame;

public class BoardThemeSelect extends javax.swing.JDialog {

    public BoardThemeSelect(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(frame);        
        comboFig.setModel(new javax.swing.DefaultComboBoxModel<>(frame.selectBoardThemeFig));
        comboFon.setModel(new javax.swing.DefaultComboBoxModel<>(frame.selectBoardThemeFon));        
        comboFig.setSelectedItem(aktion.BoardThemeFig);
        comboFon.setSelectedItem(aktion.BoardThemeFon);
        lwhiteFig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fig/"+comboFig.getSelectedItem().toString()+"/wg.png")));  
        lblackFig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fig/"+comboFig.getSelectedItem().toString()+"/bg.png"))); 
        lwhiteFon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fon/"+comboFon.getSelectedItem().toString()+"/ws.png")));
        lblackFon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fon/"+comboFon.getSelectedItem().toString()+"/bs.png")));        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jToolBar2 = new javax.swing.JToolBar();
        lwhiteFig = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lblackFig = new javax.swing.JLabel();
        comboFig = new javax.swing.JComboBox<>();
        jToolBar3 = new javax.swing.JToolBar();
        jToolBar4 = new javax.swing.JToolBar();
        lwhiteFon = new javax.swing.JLabel();
        lblackFon = new javax.swing.JLabel();
        comboFon = new javax.swing.JComboBox<>();
        bOk = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Board Theme ");

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Piece"));
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jToolBar2.setBorder(javax.swing.BorderFactory.createTitledBorder("Example"));
        jToolBar2.setFloatable(false);

        lwhiteFig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fig/alpha/wg.png"))); // NOI18N
        jToolBar2.add(lwhiteFig);
        jToolBar2.add(jSeparator1);

        lblackFig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fig/alpha/bg.png"))); // NOI18N
        jToolBar2.add(lblackFig);

        jToolBar1.add(jToolBar2);

        comboFig.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "white-black", "white-yellow", "white-green", "white-red", "yellow-green", "yellow-red", "cyan-red" }));
        comboFig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFigActionPerformed(evt);
            }
        });
        jToolBar1.add(comboFig);

        jToolBar3.setBorder(javax.swing.BorderFactory.createTitledBorder("Board"));
        jToolBar3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jToolBar4.setBorder(javax.swing.BorderFactory.createTitledBorder("Example"));
        jToolBar4.setFloatable(false);

        lwhiteFon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fon/sea-green/ws.png"))); // NOI18N
        jToolBar4.add(lwhiteFon);

        lblackFon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fon/sea-green/bs.png"))); // NOI18N
        jToolBar4.add(lblackFon);

        jToolBar3.add(jToolBar4);

        comboFon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "white-green", "white-magenta", "white-cyan", "yellow-red", "yellow-green", "cyan-magenta", "bisque-orange", "forest", "tree" }));
        comboFon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFonActionPerformed(evt);
            }
        });
        jToolBar3.add(comboFon);

        bOk.setText("Apply");
        bOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOkActionPerformed(evt);
            }
        });

        bCancel.setText("Cancel");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bOk, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(bCancel))
                    .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bOk)
                    .addComponent(bCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_bCancelActionPerformed

    private void comboFigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFigActionPerformed
        lwhiteFig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fig/"+comboFig.getSelectedItem().toString()+"/wg.png")));  
        lblackFig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fig/"+comboFig.getSelectedItem().toString()+"/bg.png")));          
    }//GEN-LAST:event_comboFigActionPerformed

    private void comboFonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFonActionPerformed
        lwhiteFon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fon/"+comboFon.getSelectedItem().toString()+"/ws.png")));
        lblackFon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/themes/fon/"+comboFon.getSelectedItem().toString()+"/bs.png")));
    }//GEN-LAST:event_comboFonActionPerformed

    private void bOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOkActionPerformed
        aktion.BoardThemeFig=comboFig.getSelectedItem().toString();
        aktion.BoardThemeFon=comboFon.getSelectedItem().toString();
        frame.loadBoardTheme();
        System.out.println("boardUI = "+boardUI.getSize());
        System.out.println("borderPanel = "+borderPanel.getSize());
    }//GEN-LAST:event_bOkActionPerformed

    public static void goBTS() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BoardThemeSelect dialog = new BoardThemeSelect(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bOk;
    public static javax.swing.JComboBox<String> comboFig;
    public static javax.swing.JComboBox<String> comboFon;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    public static javax.swing.JLabel lblackFig;
    public static javax.swing.JLabel lblackFon;
    public static javax.swing.JLabel lwhiteFig;
    public static javax.swing.JLabel lwhiteFon;
    // End of variables declaration//GEN-END:variables
}
