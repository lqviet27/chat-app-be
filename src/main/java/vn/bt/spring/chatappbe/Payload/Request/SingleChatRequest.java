package vn.bt.spring.chatappbe.Payload.Request;

public class SingleChatRequest {
    private Long userId;

    public SingleChatRequest() {
    }

    public SingleChatRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SingleChatRequest{" +
                "userId=" + userId +
                '}';
    }
}
