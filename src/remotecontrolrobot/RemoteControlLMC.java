/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remotecontrolrobot;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author Laptop
 */
public class RemoteControlLMC extends javax.swing.JFrame {

    /**
     * Creates new form RemoteControlLMC
     */
    public RemoteControlLMC() {
        initComponents();
    }

    //INISIALISASI++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void inisialisasi() {
        // Create a sample listener and controller
        LeapMotion listener = new LeapMotion();
        Controller controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        // Keep this process running until Enter is pressed
        System.out.println("Leap Motion Tidak Connect, Tekan Enter Untuk Keluar...");

        //Coba Message
        // String message = "Selamat Datang";
        //JOptionPane.showMessageDialog (null, message);
        try {
            System.in.read();
        } catch (IOException e) {
        }

        // Remove the sample listener when done
        controller.removeListener(listener);
    }

    //LEAP MOTION +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    class LeapMotion extends Listener {

        //private GUI gui;
        @Override
        public void onInit(Controller controller) {
            System.out.println("Initialized");
        }

        @Override
        public void onConnect(Controller controller) {
            System.out.println("Connected");
            controller.enableGesture(Gesture.Type.TYPE_SWIPE);
            controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
            controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
            controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
        }

        @Override
        public void onDisconnect(Controller controller) {
            //Note: not dispatched when running in a debugger.
            System.out.println("Disconnected");
        }

        @Override
        public void onExit(Controller controller) {
            System.out.println("Exited");
        }

        @Override
        public void onFrame(Controller controller) {
            // Get the most recent frame and report some basic information
            Frame frame = controller.frame();
            /*        System.out.println("Frame id: " + frame.id()
             + ", timestamp: " + frame.timestamp()
             + ", hands: " + frame.hands().count()
             + ", fingers: " + frame.fingers().count()
             + ", tools: " + frame.tools().count()
             + ", gestures " + frame.gestures().count());
             */
            //Get hands

            for (Hand hand : frame.hands()) {
                String handType = hand.isLeft() ? "Left hand" : "Right hand";
//            System.out.println("  " + handType + ", id: " + hand.id()
//                             + ", palm position: " + hand.palmPosition());

                // Get the hand's normal vector and direction
                Vector normal = hand.palmNormal();
                Vector direction = hand.direction();

                // Mendapatkan Setiap Jari Pada Tangan
                float[] allDistance = new float[5];
                float Sum = 0;
                float Rata = 0;
                int k = 0;
                for (Finger finger : hand.fingers()) {
//                System.out.println("    " + finger.type() + ", id: " + finger.id()
//                                 + ", length: " + finger.length()
//                                 + "mm, width: " + finger.width() + "mm");

                    //Mendapatkan Nilai Vector Setiap Bone
                    float[] distance = new float[4];
                    int i = 0;
                    for (Bone.Type boneType : Bone.Type.values()) {
                        Bone bone = finger.bone(boneType);
//                    System.out.println("        " + bone.type()
//                                     + " bone, start: " + bone.prevJoint());
                        //+ ", end: " + bone.nextJoint()
                        // + ", direction: " + bone.direction())                
                        //}

                        //Posisi Palm
//                System.out.println("        Palm position: " + hand.palmPosition());
                        //Menghitung Jarak Antara Posisi Palm dengan Posisi Bone
                        Vector aPoint = new Vector(hand.palmPosition());
                        Vector bPoint = new Vector(bone.prevJoint());
                        float jarak = bPoint.distanceTo(aPoint); // distance = 10
//                System.out.println("        Jarak dari Palm: " + jarak);               
                        distance[i] = jarak;
                        i++;
                        //------------------------------------------------------------------------------------------------------------------
                    }

                    allDistance[k] = distance[3];
                    Sum = Sum + allDistance[k];
                    System.out.println("AD : " + allDistance[k] + " D : " + distance[3]);
                    k++;

                    //System.out.println("i = "+i);
                    //System.out.println("k = "+k);
                    System.out.println("JML = " + Sum);
                }
                Rata = Sum / 5;
                System.out.println("Rata = " + Rata);
                if (Rata < 48) {
                    System.out.println("kanan");
                    btkananMouseEntered(null);
                    btstopMouseExited(null);
                    btmajuMouseExited(null);
                    btmundurMouseExited(null);
                    btkiriMouseExited(null);
                } else if (Rata > 48 && Rata < 52) {
                    System.out.println("kiri");
                    btkiriMouseEntered(null);
                    btstopMouseExited(null);
                    btmajuMouseExited(null);
                    btmundurMouseExited(null);
                    btkananMouseExited(null);
                } else if (Rata > 52 && Rata < 56) {
                    System.out.println("mundur");
                    btmundurMouseEntered(null);
                    btstopMouseExited(null);
                    btmajuMouseExited(null);
                    btkananMouseExited(null);
                    btkiriMouseExited(null);
                } else if (Rata > 56 && Rata < 70) {
                    System.out.println("maju");
                    btmajuMouseEntered(null);
                    btstopMouseExited(null);
                    btmundurMouseExited(null);
                    btkananMouseExited(null);
                    btkiriMouseExited(null);
                } else if (Rata > 70) {
                    System.out.println("STOP");
                    btstopMouseEntered(null);
                    btmajuMouseExited(null);
                    btmundurMouseExited(null);
                    btkananMouseExited(null);
                    btkiriMouseExited(null);
                } else {
                    btstopMouseExited(null);
                    btmajuMouseExited(null);
                    btmundurMouseExited(null);
                    btkananMouseExited(null);
                    btkiriMouseExited(null);
                    //Bluetooth5.go("1");
                }
                //System.out.println("k = "+k);
                System.out.print("\n\n-------------------------------------------------------------------------------------------------------------------------------\n");
            }
            System.out.print("\n \n \n");
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Bluetooth
    public class Bluetooth5 {

        //public char DataKelas = 0;

        boolean scanFinished = false;
        String hc05Url
                //= "btspp://201707252380:1;authenticate=false;encrypt=false;master=false"; //bluetooth lamp kampus
                = "btspp://98D331207B18:1;authenticate=false;encrypt=false;master=false"; //bluetooth robot rumah
        
        //= "btspp://201610094738:1;authenticate=false;encrypt=false;master=false"; //Replace this with your bluetooth URL

//        public static void main(String[] args) {
//            try {
//                new Bluetooth5().go();
//            } catch (Exception ex) {
//                Logger.getLogger(Bluetooth5.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        public void go(String DataKelas) throws Exception {
            StreamConnection streamConnection = (StreamConnection) Connector.open(hc05Url);
            OutputStream os = streamConnection.openOutputStream();
            InputStream is = streamConnection.openInputStream();
            //os.write("1".getBytes()); //'1' means ON and '0' means OFF
            os.write(DataKelas.getBytes()); //'1' means ON and '0' means OFF
            os.close();
            byte[] b = new byte[200];
            //Thread.sleep(200);
            is.read(b);
            is.close();
            streamConnection.close();
            System.out.println("received " + new String(b));
        }
    }
// End of Bluetooth +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btstop = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btkanan = new javax.swing.JButton();
        btkiri = new javax.swing.JButton();
        btmundur = new javax.swing.JButton();
        btmaju = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btstop.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btstop.setText("S");
        btstop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btstopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btstopMouseExited(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Remote Control Robot Beroda Menggunakan Leap Motion");

        btkanan.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btkanan.setText("R");
        btkanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btkananMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btkananMouseExited(evt);
            }
        });

        btkiri.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btkiri.setText("L");
        btkiri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btkiriMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btkiriMouseExited(evt);
            }
        });

        btmundur.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btmundur.setText("B");
        btmundur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btmundurMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btmundurMouseExited(evt);
            }
        });

        btmaju.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        btmaju.setText("F");
        btmaju.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btmajuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btmajuMouseExited(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btmaju, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btmundur, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btkiri, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btstop, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btkanan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btmaju, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btstop, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btkanan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btkiri, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btmundur, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btstopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btstopMouseEntered
        // TODO add your handling code here:
        btstop.setBackground(new java.awt.Color(255, 0, 0));
        Bluetooth5 bt = new Bluetooth5();
        try {
            bt.go("5");
        } catch (Exception ex) {
            Logger.getLogger(RemoteControlLMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btstopMouseEntered

    private void btstopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btstopMouseExited
        // TODO add your handling code here:
        btstop.setBackground(new java.awt.Color(240, 240, 240));
        Bluetooth5 bt = new Bluetooth5();
        try {
            bt.go("6");
        } catch (Exception ex) {
            Logger.getLogger(RemoteControlLMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btstopMouseExited

    private void btmajuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btmajuMouseEntered
        // TODO add your handling code here:
        btmaju.setBackground(new java.awt.Color(0, 204, 0));
        Bluetooth5 bt = new Bluetooth5();
        try {
            bt.go("1");
        } catch (Exception ex) {
            Logger.getLogger(RemoteControlLMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btmajuMouseEntered

    private void btmajuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btmajuMouseExited
        // TODO add your handling code here:
        btmaju.setBackground(new java.awt.Color(240, 240, 240));
        Bluetooth5 bt = new Bluetooth5();
        try {
            bt.go("6");
        } catch (Exception ex) {
            Logger.getLogger(RemoteControlLMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btmajuMouseExited

    private void btmundurMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btmundurMouseEntered
        // TODO add your handling code here:
        btmundur.setBackground(new java.awt.Color(0, 0, 255));
        Bluetooth5 bt = new Bluetooth5();
        try {
            bt.go("2");
        } catch (Exception ex) {
            Logger.getLogger(RemoteControlLMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btmundurMouseEntered

    private void btmundurMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btmundurMouseExited
        // TODO add your handling code here:
        btmundur.setBackground(new java.awt.Color(240, 240, 240));
        Bluetooth5 bt = new Bluetooth5();
        try {
            bt.go("6");
        } catch (Exception ex) {
            Logger.getLogger(RemoteControlLMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btmundurMouseExited

    private void btkananMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btkananMouseEntered
        // TODO add your handling code here:
        btkanan.setBackground(new java.awt.Color(255, 255, 0));
        Bluetooth5 bt = new Bluetooth5();
        try {
            bt.go("3");
        } catch (Exception ex) {
            Logger.getLogger(RemoteControlLMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btkananMouseEntered

    private void btkananMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btkananMouseExited
        // TODO add your handling code here:
        btkanan.setBackground(new java.awt.Color(240, 240, 240));
        Bluetooth5 bt = new Bluetooth5();
        try {
            bt.go("6");
        } catch (Exception ex) {
            Logger.getLogger(RemoteControlLMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btkananMouseExited

    private void btkiriMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btkiriMouseEntered
        // TODO add your handling code here:
        btkiri.setBackground(new java.awt.Color(255, 255, 0));
        Bluetooth5 bt = new Bluetooth5();
        try {
            bt.go("4");
        } catch (Exception ex) {
            Logger.getLogger(RemoteControlLMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btkiriMouseEntered

    private void btkiriMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btkiriMouseExited
        // TODO add your handling code here:
        btkiri.setBackground(new java.awt.Color(240, 240, 240));
        Bluetooth5 bt = new Bluetooth5();
        try {
            bt.go("6");
        } catch (Exception ex) {
            Logger.getLogger(RemoteControlLMC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btkiriMouseExited

    /**
     * invokeLater
     *
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
            java.util.logging.Logger.getLogger(RemoteControlLMC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RemoteControlLMC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RemoteControlLMC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RemoteControlLMC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        RemoteControlLMC RC = new RemoteControlLMC();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new RemoteControlLMC().setVisible(true);
                RC.setVisible(true);
            }
        });
        RC.inisialisasi();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btkanan;
    private javax.swing.JButton btkiri;
    private javax.swing.JButton btmaju;
    private javax.swing.JButton btmundur;
    private javax.swing.JButton btstop;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
