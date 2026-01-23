package com.securephone.client.video;

/**
 * Video player stub. Reads frames from a VideoBuffer; no rendering provided.
 * Integrate with a UI component or native renderer later.
 */
public class VideoPlayer {

    private final VideoBuffer buffer;
    private volatile boolean running = false;
    private Thread playThread;

    public VideoPlayer(VideoBuffer buffer) {
        this.buffer = buffer;
    }

    public void start() {
        if (running) {
            return;
        }
        running = true;
        playThread = new Thread(() -> {
            while (running) {
                try {
                    byte[] frame = buffer.poll(200);
                    if (frame != null) {
                        // TODO: deliver frame to a renderer
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }, "VideoPlayer-Thread");
        playThread.setDaemon(true);
        playThread.start();
    }

    public void stop() {
        running = false;
        if (playThread != null) {
            try {
                playThread.join(100);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
