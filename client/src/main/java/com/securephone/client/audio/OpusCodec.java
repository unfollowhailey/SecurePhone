package com.securephone.client.audio;

/**
 * Placeholder Opus codec interface.
 *
 * NOTE: This implementation is a pass-through stub. Replace with a real Opus
 * binding (e.g., via JNI or a Java Opus wrapper) for production-quality
 * encoding/decoding.
 */
public class OpusCodec {

    public OpusCodec() {
    }

    /**
     * Encode raw PCM bytes into compressed bytes. Current stub returns the
     * input buffer unchanged.
     */
    public byte[] encode(byte[] pcm) {
        if (pcm == null) {
            return new byte[0];
        }
        // TODO: integrate real Opus encoder
        byte[] out = new byte[pcm.length];
        System.arraycopy(pcm, 0, out, 0, pcm.length);
        return out;
    }

    /**
     * Decode compressed bytes into raw PCM bytes. Current stub returns the
     * input buffer unchanged.
     */
    public byte[] decode(byte[] compressed) {
        if (compressed == null) {
            return new byte[0];
        }
        // TODO: integrate real Opus decoder
        byte[] out = new byte[compressed.length];
        System.arraycopy(compressed, 0, out, 0, compressed.length);
        return out;
    }
}
