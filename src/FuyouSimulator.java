/**
 * FuyouSimulator.java
 * 
 * GUI application to simulate Fuyou calculations.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.TreeSet;
import javax.swing.*;

public class FuyouSimulator extends JFrame {
    // GUI components
    private final JTextField[] monthFields = new JTextField[12];
    private final JTextArea resultArea = new JTextArea(6, 30);
    private final Properties config = new Properties();

    /*
     * Constructor to set up the GUI.
     */
    public FuyouSimulator() {
        // Load configuration properties
        try (FileInputStream fis = new FileInputStream("src/config.properties")) {
            config.load(fis);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "設定ファイルの読み込みに失敗しました。",
                    "設定エラー", JOptionPane.ERROR_MESSAGE);
        }

        // Frame settings
        setTitle("扶養控除シミュレーター（課税給与入力）");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(13, 2, 5, 5));
        for (int i = 0; i < 12; i++) {
            inputPanel.add(new JLabel((i + 1) + "月:"));
            monthFields[i] = new JTextField();
            inputPanel.add(monthFields[i]);
        }

        // Calculate button and result area
        JButton calcButton = new JButton("計算する");
        calcButton.addActionListener(new CalcListener());

        resultArea.setEditable(false);
        resultArea.setFont(new Font("Meiryo", Font.PLAIN, 14));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(calcButton, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(new JLabel("課税対象給与を入力してください（円）", SwingConstants.CENTER), BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
     * Listener for the calculate button.
     */
    private class CalcListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double[] monthly = new double[12];

            try {
                for (int i = 0; i < 12; i++) {
                    String text = monthFields[i].getText().trim();
                    monthly[i] = text.isEmpty() ? 0 : Double.parseDouble(text);
                }

                double total = FuyouCalculator.calcTotal(monthly);

                StringBuilder sb = new StringBuilder();
                sb.append(String.format("年間課税給与合計：%,.0f 円%n%n", total));
                sb.append("＜扶養控除の壁＞\n");

                // Collect wall keys
                TreeSet<String> keys = new TreeSet<>();
                for (Object keyObj : config.keySet()) {
                    String key = (String) keyObj;
                    if (key.startsWith("wall.") && key.endsWith(".label")) {
                        String wallKey = key.substring(5, key.length() - 6);
                        keys.add(wallKey);
                    }
                }

                for (String wallKey : keys) {
                    String label = config.getProperty("wall." + wallKey + ".label");
                    String valueStr = config.getProperty("wall." + wallKey + ".value");
                    if (label != null && valueStr != null) {
                        try {
                            double value = Double.parseDouble(valueStr);
                            sb.append(FuyouCalculator.wallStatus(total, value, label)).append("\n");
                        } catch (NumberFormatException ex) {
                            // skip invalid value
                        }
                    }
                }

                resultArea.setText(sb.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(FuyouSimulator.this,
                        "入力に誤りがあります。数値のみを入力してください。",
                        "入力エラー", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FuyouSimulator::new);
    }
}
