package pl.vilya.bs.views;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainWindow extends JFrame {
    private JPanel _mainPanel;
    private JLabel _videoFileName;

    public MainWindow(String title) {
        super(title);

        initializeMenu();

        setContentPane(_mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem fileOpenItem = new JMenuItem("Open", KeyEvent.VK_O);
        fileOpenItem.addActionListener(e -> openVideoFile());
        fileMenu.add(fileOpenItem);

        JMenuItem fileCloseItem = new JMenuItem("Close", KeyEvent.VK_C);
        fileCloseItem.addActionListener(e -> close());
        fileMenu.add(fileCloseItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    private void openVideoFile() {
        File selectedFile = new VideoFileChooser().open(_mainPanel);
        _videoFileName.setText(selectedFile.getName());
    }

    private void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
