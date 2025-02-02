package org.nightfury.shared;

public class Result<T> {
    private final T value;
    private final String errorMessage;
    private final boolean isSuccess;

    private Result(T value, String errorMessage, boolean isSuccess) {
        this.value = value;
        this.errorMessage = errorMessage;
        this.isSuccess = isSuccess;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, null, true);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(null, message, false);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isFailure() {
        return !isSuccess;
    }

    public T getValue() {
        if (!isSuccess) {
            throw new IllegalStateException("Cannot get value from a failed result");
        }
        return value;
    }

    public String getError() {
        if (isSuccess) {
            throw new IllegalStateException("Cannot get error message from a successful result");
        }
        return errorMessage;
    }
}

