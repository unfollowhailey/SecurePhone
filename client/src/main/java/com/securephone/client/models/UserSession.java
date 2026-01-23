package com.securephone.client.models;

public class UserSession {

    private String userId;
    private String username;
    private boolean connected;
    private AudioDevice audioDevice;

    public UserSession() {
        this.connected = false;
    }

    public UserSession(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.connected = false;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public AudioDevice getAudioDevice() {
        return audioDevice;
    }

    public void setAudioDevice(AudioDevice audioDevice) {
        this.audioDevice = audioDevice;
    }

    @Override
    public String toString() {
        return "UserSession{"
                + "userId='" + userId + '\''
                + ", username='" + username + '\''
                + ", connected=" + connected
                + ", audioDevice=" + (audioDevice != null ? audioDevice.getName() : "none")
                + '}';
    }
}
