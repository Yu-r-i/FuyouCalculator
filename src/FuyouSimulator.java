
/**
 * FuyouSimulator.java
 * 
 * GUI application to simulate Fuyou calculations.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FuyouSimulator extends JFrame {
    // GUI components
    private final JTextField[] monthFields = new JTextField[12];
    private final JTextArea resultArea = new JTextArea(6, 30);

    /*
     * Constructor to set up the GUI.
     */
    public FuyouSimulator() {
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
                sb.append(FuyouCalculator.wallStatus(total, 1_030_000, "103万円")).append("\n");
                sb.append(FuyouCalculator.wallStatus(total, 1_060_000, "106万円")).append("\n");
                sb.append(FuyouCalculator.wallStatus(total, 1_300_000, "130万円"));

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
