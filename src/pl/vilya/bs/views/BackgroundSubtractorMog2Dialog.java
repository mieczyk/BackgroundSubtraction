package pl.vilya.bs.views;

import pl.vilya.bs.core.subtractors.BackgroundSubtractorMog2Config;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BackgroundSubtractorMog2Dialog extends JDialog {
    private JPanel _mainPanel;
    private JButton _okButton;
    private JButton _cancelButton;
    private JTextField _historyTextField;
    private JTextField _thresholdTextField;
    private JTextField _mixturesTextField;
    private JTextField _backgroundRatioTextField;
    private JTextField _initVarianceTextField;
    private JTextField _maxVarianceTextField;
    private JTextField _minVarianceTextField;
    private JTextField _complexityReductionTextField;
    private JTextField _learningRateTextField;
    private JTextField _shadowValueTextField;
    private JTextField _shadowThresholdTextField;
    private JCheckBox _detectShadowCheckBox;

    private final BackgroundSubtractorMog2Config _config;
    private boolean _configSaved;

    public BackgroundSubtractorMog2Dialog(BackgroundSubtractorMog2Config config) {
        prepareWindow();
        initializeListeners();

        _config = new BackgroundSubtractorMog2Config(config);
        _configSaved = false;

        showSettings();
    }

    private void prepareWindow() {
        setTitle("BackgroundSubtractorMOG2 Settings");
        setModal(true);
        setContentPane(_mainPanel);
        getRootPane().setDefaultButton(_okButton);
        pack();
    }

    private void initializeListeners() {
        _okButton.addActionListener(e -> {
            if(updateSettings()) {
                exit(true);
            } else {
                showSettings();
            }
        });
        _cancelButton.addActionListener(e -> exit(false));

        // Cross clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit(false);
            }
        });

        // ESC pressed
        _mainPanel.registerKeyboardAction(
                e -> exit(false),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    private void showSettings() {
        _historyTextField.setText(String.valueOf(_config.getHistory()));
        _mixturesTextField.setText(String.valueOf(_config.getMixtures()));
        _shadowValueTextField.setText(String.valueOf(_config.getShadowValue()));
        _detectShadowCheckBox.setSelected(_config.getDetectShadow());

        DecimalFormat formatter = new DecimalFormat("0.00##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

        _thresholdTextField.setText(formatter.format(_config.getThreshold()));
        _backgroundRatioTextField.setText(formatter.format(_config.getBackgroundRatio()));
        _initVarianceTextField.setText(formatter.format(_config.getInitialVariance()));
        _maxVarianceTextField.setText(formatter.format(_config.getMaxVariance()));
        _minVarianceTextField.setText(formatter.format(_config.getMinVariance()));
        _complexityReductionTextField.setText(formatter.format(_config.getComplexityReduction()));
        _learningRateTextField.setText(formatter.format(_config.getLearningRate()));
        _shadowThresholdTextField.setText(formatter.format(_config.getShadowThreshold()));
    }

    private boolean updateSettings() {
        try {
            _config.setHistory(Integer.parseInt(_historyTextField.getText()));
            _config.setMixtures(Integer.parseInt(_mixturesTextField.getText()));
            _config.setShadowValue(Integer.parseInt(_shadowValueTextField.getText()));
            _config.setDetectShadow(_detectShadowCheckBox.isSelected());
            _config.setThreshold(Double.parseDouble(_thresholdTextField.getText()));
            _config.setBackgroundRatio(Double.parseDouble(_backgroundRatioTextField.getText()));
            _config.setInitialVariance(Double.parseDouble(_initVarianceTextField.getText()));
            _config.setMaxVariance(Double.parseDouble(_maxVarianceTextField.getText()));
            _config.setMinVariance(Double.parseDouble(_minVarianceTextField.getText()));
            _config.setComplexityReduction(Double.parseDouble(_complexityReductionTextField.getText()));
            _config.setLearningRate(Double.parseDouble(_learningRateTextField.getText()));
            _config.setShadowThreshold(Double.parseDouble(_shadowThresholdTextField.getText()));
        } catch(NumberFormatException ex) {
            String message = "Invalid value. " + ex.getMessage();
            JOptionPane.showMessageDialog(_mainPanel, message, "Invalid value error", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        return true;
    }

    private void exit(boolean configSaved) {
        _configSaved = configSaved;
        dispose();
    }

    /**
     * Shows the configuration dialog.
     * @return Boolean value indicating whether changes have been saved.
     */
    public boolean showDialog() {
        setVisible(true);
        return _configSaved;
    }

    public BackgroundSubtractorMog2Config getSettings() {
        return _config;
    }
}
