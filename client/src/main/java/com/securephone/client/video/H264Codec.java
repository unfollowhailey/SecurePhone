package com.securephone.client.video;

/**
 * Placeholder H264 codec stub. Pass-through implementation for Tflow. Replace
 * with native binding or library for real encoding/decoding.
 */
public class H264Codec {

    public H264Codec() {
    }

    public byte[] encode(byte[] rawFrame) {
        if (rawFrame == null) {
            return new byte[0];
        }
        byte[] out = new byte[rawFrame.length];
        System.arraycopy(rawFrame, 0, out, 0, rawFrame.length);
        return out;
    }

    public byte[] decode(byte[] encoded) {
        if (encoded == null) {
            return new byte[0];
        }
        byte[] out = new byte[encoded.length];
        System.arraycopy(encoded, 0, out, 0, encoded.length);
        return out;
    }
}
