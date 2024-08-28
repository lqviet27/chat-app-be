package vn.bt.spring.chatappbe.DTO;

import java.time.LocalDateTime;

public class MessageDTO {
    private Long id;
    private Long chatId;
    private Long userId;
    private String content;
    private LocalDateTime timestamp;

    public MessageDTO() {
    }

    public MessageDTO(Long id, Long chatId, Long userId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.chatId = chatId;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
