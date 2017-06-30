package pl.vilya.bs.views;

import pl.vilya.bs.presenters.MainPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainWindow extends JFrame {
    private JPanel _mainPanel;
    private JLabel _videoLabel;
    private MainPresenter _presenter;

    public MainWindow(String title) {
        super(title);

        _presenter = new MainPresenter(this);

        initializeMenu();

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

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    public File getVideoFile(File currentDirectory) {
        return new VideoFileChooser().open(_mainPanel, currentDirectory);
    }

    public void displayVideoFrame(Image frame) {
        _videoLabel.setIcon(new ImageIcon(frame));
        pack();
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
