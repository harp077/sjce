package SJCE.more;

import static SJCE.XChessFrame.frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class About extends javax.swing.JDialog {

    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;

    public About(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setTitle(frame.sjceTitle);
        this.setLocationRelativeTo(frame);
        TAabout.setText(
            "SJCE - Strong Java Chess Engines, free portable cross-platform game,\n"+
            "100%-pure Java. Support many best free java xboard/uci chess ehgines.\n"+
            "It is possible to play Human-Human, Human-Engine,\n"+
            "Engine-Engine, both White and Black.\n"+                    
            "Tested on Windows/Linux. Need jre1.8.\n"+
            "Special thanks for Norbert Raimund Leisner, who has\n"+ 
            "given me a lot of information about engines. See:\n"+
            "https://chessprogramming.wikispaces.com/Norbert+Raimund+Leisner\n"+
            "Special thanks for Dr. Roland Stuckardt (http://www.stuckardt.de/)\n"+ 
            "- developer of Fischerle engine and his remark about sound support.\n"+ 
            "See also: http://computer-chess.org/\n"+
            "Roman Koldaev, Saratov city, Russia");
 
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    public int getReturnStatus() {
        return returnStatus;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TAabout = new javax.swing.JTextArea();
        jToolBar4 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jToolBar2 = new javax.swing.JToolBar();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        bMail = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jToolBar3 = new javax.swing.JToolBar();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        bHomepage = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About Strong Java Chess Engines");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        TAabout.setEditable(false);
        TAabout.setColumns(20);
        TAabout.setRows(5);
        TAabout.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jScrollPane1.setViewportView(TAabout);

        jToolBar4.setFloatable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/sjce-130x87.png"))); // NOI18N
        jToolBar4.add(jLabel1);

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("FeedBack"));
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jToolBar2.setFloatable(false);
        jToolBar2.add(jSeparator8);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/open_mail_sh.png"))); // NOI18N
        jLabel3.setText("E-Mail:  ");
        jToolBar2.add(jLabel3);
        jToolBar2.add(jSeparator4);

        bMail.setText("<html><a href=\"mailto:harp07@mail.ru\">  harp07@mail.ru  </a></html>");
        bMail.setActionCommand("<html><a href=\"mailto:harp07@mail.ru\">mailto:harp07@mail.ru</a></html>");
        bMail.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bMail.setFocusable(false);
        bMail.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bMail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bMail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMailActionPerformed(evt);
            }
        });
        jToolBar2.add(bMail);
        jToolBar2.add(jSeparator2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/clipboard_plus.png"))); // NOI18N
        jButton3.setToolTipText("Copy E-Mail address to ClipBoard");
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton3);
        jToolBar2.add(jSeparator6);

        jToolBar1.add(jToolBar2);
        jToolBar1.add(jSeparator1);

        jToolBar3.setFloatable(false);
        jToolBar3.add(jSeparator9);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/home.png"))); // NOI18N
        jLabel2.setText("Home:   ");
        jToolBar3.add(jLabel2);
        jToolBar3.add(jSeparator5);

        bHomepage.setText("<html><a href=\"http://sjce.sourceforge.net\">  http://sjce.sourceforge.net  </a></html>");
        bHomepage.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bHomepage.setFocusable(false);
        bHomepage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bHomepage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bHomepage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bHomepage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHomepageActionPerformed(evt);
            }
        });
        jToolBar3.add(bHomepage);
        jToolBar3.add(jSeparator3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/clipboard_plus.png"))); // NOI18N
        jButton4.setToolTipText("Copy URL to ClipBoard");
        jButton4.setFocusPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton4);
        jToolBar3.add(jSeparator7);

        jToolBar1.add(jToolBar3);

        jToolBar4.add(jToolBar1);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(okButton)
                .addContainerGap())
        );

        getRootPane().setDefaultButton(okButton);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void bMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMailActionPerformed
        Mail_Url.goMAIL("mailto:harp07@mail.ru");
    }//GEN-LAST:event_bMailActionPerformed

    private void bHomepageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHomepageActionPerformed
        Mail_Url.goURL("http://sjce.sourceforge.net");
    }//GEN-LAST:event_bHomepageActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Actions.CopyToClipBoard("harp07@mail.ru");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Actions.CopyToClipBoard("http://sjce.sourceforge.net");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed
    
    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    public static void aboutRun() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                About dialog = new About(new javax.swing.JFrame(), true);
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
    public static javax.swing.JTextArea TAabout;
    private javax.swing.JButton bHomepage;
    private javax.swing.JButton bMail;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}
