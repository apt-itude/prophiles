package com.itude.apt.prophiles.util;

/**
 * Created by athompson on 2/26/17.
 */

public class InvalidStreamError extends RuntimeException {

    public int stream;

    public InvalidStreamError(int stream) {
        super("Invalid volume stream: " + stream);
        this.stream = stream;
    }
}
