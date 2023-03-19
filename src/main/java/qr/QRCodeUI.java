package qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeUI extends JFrame implements ActionListener {
    private final JTextField inputTextField;
    private final JTextField sizeTextField;
    private final JButton generateButton;
    private final JComboBox<String> formatComboBox;

    public QRCodeUI() {
        setTitle("QR Code Generator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel inputLabel = new JLabel("Input:");
        inputTextField = new JTextField(20);

        JLabel sizeLabel = new JLabel("Size:");
        sizeTextField = new JTextField(10);

        JLabel formatLabel = new JLabel("Format:");
        String[] formats = {"PNG", "JPEG"};
        formatComboBox = new JComboBox<>(formats);

        generateButton = new JButton("Generate QR Code");
        generateButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(inputLabel);
        panel.add(inputTextField);
        panel.add(sizeLabel);
        panel.add(sizeTextField);
        panel.add(formatLabel);
        panel.add(formatComboBox);
        panel.add(generateButton);

        getContentPane().add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            String input = inputTextField.getText();
            int size = Integer.parseInt(sizeTextField.getText());
            String format = (String) formatComboBox.getSelectedItem();
            BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;
            if (format.equalsIgnoreCase("PNG")) {
                format = "png";
            } else if (format.equalsIgnoreCase("JPEG")) {
                format = "jpeg";
                barcodeFormat = BarcodeFormat.PDF_417;
            }

            try {
                QRCodeGenerator.generateQRCode(input, size, size, "qrcode." + format, format);
                JOptionPane.showMessageDialog(this, "QR code generated successfully!");
            } catch (WriterException | IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



}