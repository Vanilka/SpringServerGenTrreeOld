package com.genealogytree.services.responses;

public abstract class ServerResponse {
    private ResponseStatus status;

    ServerResponse(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public static enum ResponseStatus {
        OK, FAIL;
    }

}
