import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.*;

public class MainWindow {

    private JFrame frame;
    private JPanel mainPanel;
    private JTextField inputField;
    private JTextField outputField;
    private JTextField trueFloatValField;
    private JTextField conversionErrorField;
    private JLabel inputPrompt;
    private JLabel outputPrompt;
    private JLabel trueFloatValPrompt;
    private JLabel conversionErrorPrompt;
    private JButton button;

    public MainWindow() {
        initialize();
    }

    public void initialize() {
        frame = new JFrame();
        frame.setTitle("IEEE-754 Floating Point Conversion");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        inputPrompt = new JLabel("Enter a floating point number:");
        outputPrompt = new JLabel("IEEE-754 binary representation:");
        trueFloatValPrompt = new JLabel("True value of float:");
        conversionErrorPrompt = new JLabel("Error due to conversion:");

        inputField = new JTextField(20);
        outputField = new JTextField(34);
        trueFloatValField = new JTextField(20);
        conversionErrorField = new JTextField(20);

        outputField.setEditable(false);
        trueFloatValField.setEditable(false);
        conversionErrorField.setEditable(false);

        button = new JButton("Convert");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String floatString = inputField.getText();
                    float floatVal = Float.parseFloat(floatString);
                    BigDecimal trueFloatVal = new BigDecimal(Float.toString(floatVal));
                    BigDecimal inputVal = new BigDecimal(floatString);
                    BigDecimal error = trueFloatVal.subtract(inputVal);
                    
                    String IEEE754BinaryString = FloatToIEEE754.convertFloatToIEEE754(floatString);
                    outputField.setText(IEEE754BinaryString);
                    trueFloatValField.setText(trueFloatVal.toPlainString());
                    conversionErrorField.setText(error.toPlainString());
                } catch(NumberFormatException ex) {
                    outputField.setText("not a valid decimal string");
                }
            }
        });

        mainPanel.add(inputPrompt);
        mainPanel.add(inputField);
        mainPanel.add(button);
        mainPanel.add(outputPrompt);
        mainPanel.add(outputField);
        mainPanel.add(trueFloatValPrompt);
        mainPanel.add(trueFloatValField);
        mainPanel.add(conversionErrorPrompt);
        mainPanel.add(conversionErrorField);

        frame.add(mainPanel);
    }

    public void show() {
        frame.setVisible(true);
    }
}
