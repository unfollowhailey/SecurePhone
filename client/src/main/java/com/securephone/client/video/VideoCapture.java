package com.securephone.client.video;

/**
 * Video capture stub. No external dependencies — produces/consumes raw frames
 * as byte[]. Implement platform-specific capture (OpenCV, JavaCV, or native)
 * later.
 */
public class VideoCapture {

    private final VideoBuffer buffer;
    private volatile boolean running = false;
    private Thread captureThread;

    public VideoCapture(VideoBuffer buffer) {
        this.buffer = buffer;
    }

    public void start() {
        if (running) {
            return;
        }
        running = true;
        captureThread = new Thread(() -> {
            // Stub: no real camera access. Users should push frames manually or
            // replace this with a real capture implementation.
            while (running) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        }, "VideoCapture-Thread");
        captureThread.setDaemon(true);
        captureThread.start();
    }

    public void stop() {
        running = false;
        if (captureThread != null) {
            try {
                captureThread.join(100);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
