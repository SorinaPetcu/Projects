package UIWindow;

import Simulation.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuPanel extends JPanel {
    private JButton startSim;


    MenuPanel() {
        startSim = new JButton("Start");
        startSim.setBounds(30, 80, 80, 25);
        this.setBounds(0, 0, 180, 600);        //setare dimensiuni
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.add(startSim);
        action();
    }

    private void action() {                  //metoda pentru setarea atributilor butonului de start si combo box-ului
        startSim.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                    SimulationManager.frame.setRunning(true);           //pornire timer
                    SimulationManager.frame.setInvalidData(false);
            }
        });


    }
}
