package com.lucas.ferreira.maburn.model;

public class ExecutorResult {
    private String output;
    private boolean success = false;

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

