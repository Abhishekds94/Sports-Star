package com.abhishek.sportsstar.data.network;

import androidx.annotation.Nullable;

public class NoConnectivityException extends Exception {

    private String message;

    public NoConnectivityException(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }
}
