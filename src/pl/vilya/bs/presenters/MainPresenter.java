package pl.vilya.bs.presenters;

import pl.vilya.bs.core.VideoStream;
import pl.vilya.bs.views.MainWindow;

import java.io.File;
import java.io.IOException;

public class MainPresenter {
    private final MainWindow _view;
    private File _lastSelectedDirectory = null;
    private VideoStream _video = null;

    public MainPresenter(MainWindow view) {
        _view = view;
    }

    public void openVideoFile() {
        File videoFile = _view.getVideoFile(_lastSelectedDirectory);
        _lastSelectedDirectory = videoFile.getParentFile();

        try {
            _video = new VideoStream(videoFile.getAbsolutePath());
        } catch (IOException e) {
            _view.showErrorMessage(e.getMessage());
        }

        _view.displayVideoFrame(_video.getFrame().toImage());
    }
}
