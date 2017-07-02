package pl.vilya.bs.core;

import org.opencv.videoio.VideoCapture;

import java.io.IOException;

public class VideoFileStream extends VideoStream {
    private final String _source;

    /**
     * Loads video files or RTSP/HTTP stream.
     * @param source Video file name or RTSP/HTTP stream URL.
     */
    public VideoFileStream(String source) throws IOException {
        _source = source;
        reopen();
    }

    @Override
    public void reopen() throws IOException {
        _cap = new VideoCapture(_source);

        if(!_cap.isOpened()) {
            throw new IOException("Could not read from the source: " + _source);
        }
    }
}
