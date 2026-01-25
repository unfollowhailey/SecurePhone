package com.securephone.client.audio;

import java.util.Objects;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Simple audio player that reads frames from an AudioBuffer and plays them.
 */
public class AudioPlayer {

    private final AudioFormat format;
    private SourceDataLine line;
    private final AudioBuffer buffer;
    private volatile boolean running = false;
    private Thread playThread;

    public AudioPlayer(AudioBuffer buffer) {
        this.format = new AudioFormat(48000f, 16, 1, true, false);
        this.buffer = Objects.requireNonNull(buffer);
    }

    public void start() throws LineUnavailableException {
        if (running) {
            return;
        }
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        line = (SourceDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();
        running = true;

        playThread = new Thread(() -> {
            while (running) {
                try {
                    byte[] frame = buffer.poll(100);
                    if (frame != null) {
                        line.write(frame, 0, frame.length);
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }, "AudioPlayer-Thread");
        playThread.setDaemon(true);
        playThread.start();
    }

    public void stop() {
        running = false;
        if (line != null) {
            line.drain();
            line.stop();
            line.close();
        }
        if (playThread != null) {
            try {
                playThread.join(200);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
