import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainWindow {

    private JFrame frame;
    private JPanel panel;
    private JTextField textField;
    private JLabel label;
    private JButton button;

    public MainWindow() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame();
        frame.setTitle("IEEE-754 Floating Point Conversion");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        label = new JLabel("Enter a floating point number:");
        textField = new JTextField(20);
        button = new JButton("Convert");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(button);

        frame.add(panel, BorderLayout.CENTER);


        show();
    }

    public void show() {
        frame.setVisible(true);
    }
}
