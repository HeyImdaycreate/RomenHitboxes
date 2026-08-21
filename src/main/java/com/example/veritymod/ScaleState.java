package com.example.veritymod;

import java.util.UUID;

public final class ScaleState {
    public static final float MIN = 0.2f;
    public static final float MAX = 10.0f;
    public static final float STEP = 0.25f;

    private static float factor = 1.0f;
    private static boolean disabled = false;
    private static UUID exemptUuid = null;

    private ScaleState() {
    }

    public static float getFactor() {
        return disabled ? 1.0f : factor;
    }

    public static boolean isDisabled() {
        return disabled;
    }

    public static void setDisabled(boolean value) {
        disabled = value;
        if (value) {
            factor = 1.0f;
        }
    }

    public static void disable() {
        setDisabled(true);
    }

    public static void setFactor(float value) {
        factor = Math.max(MIN, Math.min(MAX, value));
    }

    public static void increase() {
        setFactor(factor + STEP);
    }

    public static void decrease() {
        setFactor(factor - STEP);
    }

    public static void setExempt(UUID uuid) {
        exemptUuid = uuid;
    }

    public static boolean isExempt(UUID uuid) {
        return uuid != null && uuid.equals(exemptUuid);
    }
}