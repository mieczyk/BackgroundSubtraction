package pl.vilya.bs.views;

import pl.vilya.bs.presenters.MainPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainWindow extends JFrame {
    private JPanel _mainPanel;
    private JButton _startButton;
    private JButton _stopButton;
    private VideoViewer _inputVideoViewer;
    private VideoViewer _maskVideoViewer;
    private MainPresenter _presenter;

    public MainWindow(String title) {
        super(title);

        _presenter = new MainPresenter(this);

        initializeMenu();

        _startButton.addActionListener(e -> _presenter.startVideoProcessing());
        _stopButton.addActionListener(e -> _presenter.stopVideoProcessing());

        setContentPane(_mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 200));
        setVisible(true);
    }

    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem fileOpenItem = new JMenuItem("Open", KeyEvent.VK_O);
        fileOpenItem.addActionListener(e -> _presenter.openVideoFile());
        fileMenu.add(fileOpenItem);

        JMenuItem fileCloseItem = new JMenuItem("Close", KeyEvent.VK_C);
        fileCloseItem.addActionListener(e -> close());
        fileMenu.add(fileCloseItem);

        // Background Subtraction Methods menu
        JMenu bgSubtractionMethodsMenu = new JMenu("Background Subtraction Methods");
        bgSubtractionMethodsMenu.setMnemonic(KeyEvent.VK_B);

        menuBar.add(fileMenu);
        menuBar.add(bgSubtractionMethodsMenu);

        setJMenuBar(menuBar);
    }

    public File getVideoFile(File currentDirectory) {
        return new VideoFileChooser().open(_mainPanel, currentDirectory);
    }

    public void displayOutput(VideoImages output) {
        _inputVideoViewer.setFrameImage(output.getInputFrameImage());
    }

    public void enableStartButton(boolean enabled) {
        _startButton.setEnabled(enabled);
    }

    public void enableStopButton(boolean enabled) {
        _stopButton.setEnabled(enabled);
    }

    public void showErrorMessage(String message) {
        showMessage(message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(_mainPanel, message, title, messageType);
    }

    private void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
