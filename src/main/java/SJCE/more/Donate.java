package SJCE.more;

import static SJCE.XChessFrame.frame;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

// PayPal USD: https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=harp07%40mail%2eru&lc=US&item_name=SJmp3%20support&no_note=0&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHostedGuest
// PayPal EUR: https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=harp07%40mail%2eru&lc=US&item_name=SJmp3%20support&no_note=0&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHostedGuest
// PayPal RUB: https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=harp07%40mail%2eru&lc=US&item_name=SJmp3%20support&no_note=0&currency_code=RUB&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHostedGuest
// Button money.yandex.ru RUB: <iframe frameborder="0" allowtransparency="true" scrolling="no" src="https://money.yandex.ru/embed/small.xml?account=410011879140678&quickpay=small&yamoney-payment-type=on&button-text=06&button-size=s&button-color=orange&targets=SJmp3+support&default-sum=500&successURL=" width="147" height="31"></iframe>
// Forma  money.yandex.ru RUB: <iframe frameborder="0" allowtransparency="true" scrolling="no" src="https://money.yandex.ru/embed/donate.xml?account=410011879140678&quickpay=donate&payment-type-choice=on&default-sum=&targets=SJmp3+support&target-visibility=on&project-name=SJmp3&project-site=http%3A%2F%2Fsjmp3.sourceforge.net&button-text=05&successURL=" width="510" height="132"></iframe>
// YM N = 410011879140678;
// FormaExt  money.yandex RUB: <iframe frameborder="0" allowtransparency="true" scrolling="no" src="https://money.yandex.ru/embed/donate.xml?account=410011879140678&quickpay=donate&payment-type-choice=on&default-sum=&targets=SJmp3+support&target-visibility=on&project-name=SJmp3&project-site=http%3A%2F%2Fsjmp3.sourceforge.net&button-text=05&comment=on&hint=Enter+Comment+Please&successURL=" width="510" height="203"></iframe>

public class Donate extends javax.swing.JDialog {

    public static final String paypalUSD="https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=harp07%40mail%2eru&lc=US&item_name=SJCE%20support&no_note=0&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHostedGuest";
    public static final String paypalEUR="https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=harp07%40mail%2eru&lc=GB&item_name=SJCE%20support&no_note=0&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHostedGuest";    
    public static final String paypalRUB="https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=harp07%40mail%2eru&lc=RU&item_name=SJCE%20support&no_note=0&currency_code=RUB&bn=PP%2dDonationsBF%3abtn_donate_SM%2egif%3aNonHostedGuest";
    //public static final String ymRUB="https://money.yandex.ru/embed/donate.xml?account=410011879140678&quickpay=donate&payment-type-choice=on&default-sum=&targets=SJmp3+support&target-visibility=on&project-name=SJmp3&project-site=http%3A%2F%2Fsjmp3.sourceforge.net&button-text=05&successURL=";
    public static final String ymRUB="https://money.yandex.ru/embed/donate.xml?account=410011879140678&quickpay=donate&payment-type-choice=on&default-sum=&targets=SJCE+support&target-visibility=on&project-name=SJCE&project-site=http%3A%2F%2Fsjce.sourceforge.net&button-text=05&comment=on&hint=Enter+Comment+Please&successURL=";

    public Donate(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(frame);
        this.JTA.setText("\n   If you like SJCE\n"+
                         "   - make at least a small donation for SJCE\n"+ 
                         "   development and support.\n");        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTA = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        lPayPal = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        moneySelect = new javax.swing.JComboBox<>();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        bPPdonate = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        bYMdonate = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Donation to SJCE");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/donate-smile.png"))); // NOI18N

        JTA.setEditable(false);
        JTA.setColumns(20);
        JTA.setRows(5);
        jScrollPane1.setViewportView(JTA);

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("PayPal Donate"));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        lPayPal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/paypal-27.png"))); // NOI18N
        jToolBar1.add(lPayPal);
        jToolBar1.add(jSeparator6);

        moneySelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RUB", "EUR", "USD" }));
        jToolBar1.add(moneySelect);
        jToolBar1.add(jSeparator5);

        bPPdonate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/btn_donate_SM.gif"))); // NOI18N
        bPPdonate.setToolTipText("Make a Donation");
        bPPdonate.setFocusable(false);
        bPPdonate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bPPdonate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bPPdonate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPPdonateActionPerformed(evt);
            }
        });
        jToolBar1.add(bPPdonate);
        jToolBar1.add(jSeparator4);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/clipboard_plus.png"))); // NOI18N
        jButton1.setToolTipText("Copy selected URL to ClipBoard");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jToolBar2.setBorder(javax.swing.BorderFactory.createTitledBorder("Yandex Money Donate"));
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/ym-30.png"))); // NOI18N
        jToolBar2.add(jLabel3);
        jToolBar2.add(jSeparator1);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/ym-eng-33.png"))); // NOI18N
        jToolBar2.add(jLabel2);
        jToolBar2.add(jSeparator2);

        bYMdonate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/btn_donate_SM.gif"))); // NOI18N
        bYMdonate.setToolTipText("Make a Donation");
        bYMdonate.setFocusable(false);
        bYMdonate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bYMdonate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bYMdonate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bYMdonateActionPerformed(evt);
            }
        });
        jToolBar2.add(bYMdonate);
        jToolBar2.add(jSeparator3);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/clipboard_plus.png"))); // NOI18N
        jButton2.setToolTipText("Copy selected URL to ClipBoard");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton2);

        jButton3.setText("OK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (moneySelect.getSelectedItem().equals("USD")) Actions.CopyToClipBoard(paypalUSD);
        if (moneySelect.getSelectedItem().equals("EUR")) Actions.CopyToClipBoard(paypalEUR);  
        if (moneySelect.getSelectedItem().equals("RUB")) Actions.CopyToClipBoard(paypalRUB);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bPPdonateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPPdonateActionPerformed
        if (moneySelect.getSelectedItem().equals("USD")) Mail_Url.goURL(paypalUSD);
        if (moneySelect.getSelectedItem().equals("EUR")) Mail_Url.goURL(paypalEUR);  
        if (moneySelect.getSelectedItem().equals("RUB")) Mail_Url.goURL(paypalRUB);        
    }//GEN-LAST:event_bPPdonateActionPerformed

    private void bYMdonateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bYMdonateActionPerformed
        Mail_Url.goURL(ymRUB);
    }//GEN-LAST:event_bYMdonateActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Actions.CopyToClipBoard(ymRUB);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.setVisible(false); 
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed
    public static void donateGo() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Donate dialog = new Donate(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextArea JTA;
    private javax.swing.JButton bPPdonate;
    private javax.swing.JButton bYMdonate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lPayPal;
    private javax.swing.JComboBox<String> moneySelect;
    // End of variables declaration//GEN-END:variables
}
