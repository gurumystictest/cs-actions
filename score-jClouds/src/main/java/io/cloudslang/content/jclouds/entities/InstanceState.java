package io.cloudslang.content.jclouds.entities;

/**
 * Created by Mihai Tusa.
 * 6/3/2016.
 */
public enum InstanceState {
    PENDING(0),
    RUNNING(16),
    SHUTTING_DOWN(32),
    TERMINATED(48),
    STOPPING(64),
    STOPPED(80);

    private final int key;

    InstanceState(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public static int getKey(String input) throws RuntimeException {
        try {
            InstanceState instanceState = InstanceState.valueOf(input.toUpperCase());
            return instanceState.getKey();
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("Invalid instanceStateCode value: [" + input + "]. Valid values: " +
                    "0, 16, 32, 48, 64, 80.");
        }
    }

    public static String getValue(String input) throws RuntimeException {
        try {
            return InstanceState.valueOf(input.toUpperCase()).toString().toLowerCase();
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("Invalid instanceStateName value: [" + input + "]. Valid values: " +
                    "pending, running, shutting-down, terminated, stopping, stopped.");
        }
    }
}