package vn.bt.spring.chatappbe.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JsonBackReference
    private Chat chat;

    @ManyToOne
    private User user;

    public Message() {
    }

    public Message(Long id, String content, LocalDateTime timestamp, Chat chat, User user) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.chat = chat;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Chat getChat() {
        return chat;
    }
    @JsonProperty("chatId")
    public Long getChatId() {
        return chat.getId();
    }
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", chat=" + chat.getId() +
                ", user=" + user +
                '}';
    }


}
