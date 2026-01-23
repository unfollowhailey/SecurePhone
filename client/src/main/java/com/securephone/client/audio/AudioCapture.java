package com.securephone.client.audio;

import java.util.Objects;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * Captures audio from the default microphone and pushes frames into an
 * AudioBuffer. This is a simple, dependency-free implementation suitable for
 * local testing.
 */
public class AudioCapture {

    private final AudioFormat format;
    private TargetDataLine line;
    private final AudioBuffer buffer;
    private volatile boolean running = false;
    private Thread captureThread;

    public AudioCapture(AudioBuffer buffer) {
        this.format = new AudioFormat(48000f, 16, 1, true, false);
        this.buffer = Objects.requireNonNull(buffer);
    }

    public void start() throws LineUnavailableException {
        if (running) {
            return;
        }
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();
        running = true;

        captureThread = new Thread(() -> {
            byte[] buf = new byte[320]; // 10ms @ 48kHz 16-bit mono -> 960 bytes; keep small frames
            while (running) {
                int read = line.read(buf, 0, buf.length);
                if (read > 0) {
                    byte[] frame = new byte[read];
                    System.arraycopy(buf, 0, frame, 0, read);
                    buffer.push(frame);
                }
            }
        }, "AudioCapture-Thread");
        captureThread.setDaemon(true);
        captureThread.start();
    }

    public void stop() {
        running = false;
        if (line != null) {
            line.stop();
            line.close();
        }
        if (captureThread != null) {
            try {
                captureThread.join(200);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
