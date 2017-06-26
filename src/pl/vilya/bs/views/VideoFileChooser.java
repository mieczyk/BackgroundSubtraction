package pl.vilya.bs.views;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class VideoFileChooser {
    private final JFileChooser _fileChooser;

    public VideoFileChooser() {
        FileFilter filter = new FileNameExtensionFilter(
                "Video files (*.avi, *.mp4, *.mov, *.3gp, *.rmvb, *.rm, *.wmv, *.mkv ,*.flv)",
                "avi", "mp4", "mov", "3gp", "rmvb", "rm", "wmv", "mkv", "flv"
        );

        _fileChooser = new JFileChooser();
        _fileChooser.addChoosableFileFilter(filter);
        _fileChooser.setFileFilter(filter);
    }

    public File open(Component parent) {
        return open(parent, null);
    }

    public File open(Component parent, String currentDirPath) {
        if(currentDirPath != null) {
            _fileChooser.setCurrentDirectory(new File(currentDirPath));
        }

        if(_fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return _fileChooser.getSelectedFile();
        }

        return null;
    }
}