package mobile.client.gentree.gentreemobile.rest.responses;

/**
 * Created by vanilka on 14/12/2017.
 */
public abstract class ServerResponse {
    private ResponseStatus status;

    public ServerResponse(ResponseStatus status) {
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
        return "ServerResponse{" +
                "status=" + status +
                '}';
    }

    public static enum ResponseStatus {
        OK, FAIL;
    }
}
