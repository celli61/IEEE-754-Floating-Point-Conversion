import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.*;

public class MainWindow {

    private JFrame frame;
    private JPanel inputPanel;
    private JPanel outputPanel;
    private JTextField inputField;
    private JTextField outputField;
    private JLabel inputPrompt;
    private JLabel outputPrompt;
    private JButton button;

    public MainWindow() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame();
        frame.setTitle("IEEE-754 Floating Point Conversion");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        inputPanel = new JPanel();
        outputPanel = new JPanel();
        inputPrompt = new JLabel("Enter a floating point number:");
        outputPrompt = new JLabel("IEEE-754 binary representation:");
        inputField = new JTextField(20);
        outputField = new JTextField(34);
        button = new JButton("Convert");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String floatString = inputField.getText();
                    new BigDecimal(floatString);
                    String IEEE754BinaryString = FloatToIEEE754.convertFloatToIEEE754(floatString);
                    outputField.setText(IEEE754BinaryString);
                } catch(NumberFormatException ex) {
                    outputField.setText("not a valid decimal string");
                }
            }
        });

        inputPanel.add(inputPrompt);
        inputPanel.add(inputField);
        inputPanel.add(button);

        outputPanel.add(outputPrompt);
        outputPanel.add(outputField);

        frame.add(inputPanel);
        frame.add(outputPanel);
    }

    public void show() {
        frame.setVisible(true);
    }
}
