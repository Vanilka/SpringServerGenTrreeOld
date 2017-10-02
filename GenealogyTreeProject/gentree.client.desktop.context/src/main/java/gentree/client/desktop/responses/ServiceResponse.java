package gentree.client.desktop.responses;

public abstract class ServiceResponse {
    private ResponseStatus status;

    public ServiceResponse(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "status=" + status +
                '}';
    }

    public static enum ResponseStatus {
        OK, FAIL;
    }
}
