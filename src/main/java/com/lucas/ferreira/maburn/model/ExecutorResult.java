package com.lucas.ferreira.maburn.model;

public class ExecutorResult {
    private String errorOutput;
    private boolean success = false;

    public String getErrorOutput() {
        return errorOutput;
    }

    public void setErrorOutput(String errorOutput) {
        this.errorOutput = errorOutput;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

