package SJCE.more.Mixer;
import static SJCE.XChessFrame.frame;
import SJCE.more.Actions;
import SJCE.more.Mixer.ControlSound;
//import SJmp3.SJmp3gui;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
public class MixerFrame extends javax.swing.JDialog {
   public static final int RET_CANCEL = 0;
   public static final int RET_OK = 1;
   public MixerFrame(java.awt.Frame parent, boolean modal) 
      {
        super(parent, modal);
        initComponents();
        //ImageIcon icone = new ImageIcon(getClass().getResource("/SJmp3/img/USSR-16.png"));
        this.setIconImage(frame.FrameIcon.getImage());        
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        this.MixerFrameSlider.setMajorTickSpacing(20);
        this.MixerFrameSlider.setMinorTickSpacing(10);
        this.MixerFrameSlider.setPaintTicks(true);
        this.MixerFrameSlider.setPaintLabels(true);        
        //MixerFrameSlider.setValue(Actions.currentMixer);
        try 
        {
        this.MixerFrameSlider.setValue(Math.round(100*ControlSound.getMasterOutputVolume()));
        if (Actions.currentMute.equals("true")||ControlSound.getMasterOutputMute())
         {
            ControlSound.setMasterOutputMute(true); 
            this.bMute.setSelected(true);
            System.out.println("Volume set to ZERO = 0");
            this.MixerFrameSlider.setEnabled(false);
         }
        else
         {
            ControlSound.setMasterOutputMute(false); 
            this.bMute.setSelected(false);
            ControlSound.setMasterOutputVolume((float)MixerFrameSlider.getValue()/100); 
            Actions.currentMixer=MixerFrameSlider.getValue();  
            this.MixerFrameSlider.setEnabled(true);
         }
        } 
        catch (NullPointerException nn)
        {
            System.out.println("Master output port not found"); 
            MixerFrameSlider.setEnabled(false);
            bMute.setEnabled(false);
            this.doClose(RET_CANCEL);
        } 
        catch (RuntimeException rr){
            System.out.println("Master output port not found"); 
            MixerFrameSlider.setEnabled(false);
            bMute.setEnabled(false);            
            this.doClose(RET_CANCEL);  
        }
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

        MixerFrameSlider = new javax.swing.JSlider();
        bMute = new javax.swing.JCheckBox();

        setLocation(new java.awt.Point(100, 100));
        setName("VolumeSlider"); // NOI18N
        setResizable(false);

        MixerFrameSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        MixerFrameSlider.setToolTipText("Sound Volume");
        MixerFrameSlider.setValue(100);
        MixerFrameSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                MixerFrameSliderStateChanged(evt);
            }
        });

        bMute.setText("Mute");
        bMute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMuteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bMute)
                    .addComponent(MixerFrameSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MixerFrameSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bMute)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void MixerFrameSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_MixerFrameSliderStateChanged
        ControlSound.setMasterOutputVolume((float)MixerFrameSlider.getValue()/100); 
        Actions.currentMixer=MixerFrameSlider.getValue();        
        MixerIconSet();
    }//GEN-LAST:event_MixerFrameSliderStateChanged
    private void bMuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMuteActionPerformed
        if (Actions.currentMute.equals("false"))
         {
            Actions.currentMute="true"; 
            ControlSound.setMasterOutputMute(true); 
            bMute.setSelected(true);
            System.out.println("Volume set to ZERO = 0");
            MixerFrameSlider.setEnabled(false);
            frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_muted.png")));
         }
        else
         {
            Actions.currentMute="false"; 
            ControlSound.setMasterOutputMute(false); 
            bMute.setSelected(false);
            ControlSound.setMasterOutputVolume((float)MixerFrameSlider.getValue()/100); 
            Actions.currentMixer=MixerFrameSlider.getValue();  
            MixerFrameSlider.setEnabled(true);
            MixerIconSet();
        }         
    }//GEN-LAST:event_bMuteActionPerformed
    public void MixerIconSet () {
        if ((Actions.currentMixer>0)&(Actions.currentMixer<100/3))
          frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_low.png")));   
        if ((Actions.currentMixer>100/3)&(Actions.currentMixer<200/3))
          frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_medium.png")));
        if (Actions.currentMixer>200/3)
          frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_high.png")));        
        if (Actions.currentMixer==0)
            frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_muted.png")));
        if (Actions.currentMute.equals("true")) 
         {
            ControlSound.setMasterOutputMute(true);
            System.out.println("Volume set to ZERO = 0");
            frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_muted.png")));   
         } 
        //ControlSound.setMasterOutputVolume((float)Actions.currentMixer/100);
    }
    
    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    
    public static void gogo() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MixerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MixerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MixerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MixerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MixerFrame dialog = new MixerFrame(new javax.swing.JFrame(), true);
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
    public static javax.swing.JSlider MixerFrameSlider;
    public static javax.swing.JCheckBox bMute;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
