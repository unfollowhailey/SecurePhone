package com.securephone.client.models;

public class AudioDevice {

    private final String id;
    private final String name;
    private final float sampleRate;
    private final int channels;

    public AudioDevice(String id, String name, float sampleRate, int channels) {
        this.id = id;
        this.name = name;
        this.sampleRate = sampleRate;
        this.channels = channels;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getSampleRate() {
        return sampleRate;
    }

    public int getChannels() {
        return channels;
    }

    @Override
    public String toString() {
        return "AudioDevice{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", sampleRate=" + sampleRate
                + ", channels=" + channels
                + '}';
    }
}
