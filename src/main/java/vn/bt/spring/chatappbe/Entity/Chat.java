package vn.bt.spring.chatappbe.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "chat_name")
    private String chatName;
    @Lob
    @Column(name = "chat_image", columnDefinition = "MEDIUMBLOB")
    private String chatImage;
    @Column(name = "description")
    private String description;
    @Column(name = "is_group")
    private boolean isGroup;

    @ManyToMany
    private Set<User> admins = new HashSet<>();

    @ManyToOne
    private User owner;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Message> messages = new ArrayList<>();

    public Chat() {
    }

    public Chat(Long id, String chatName, String chatImage, String description, boolean isGroup, Set<User> admins, User owner, Set<User> users, List<Message> messages) {
        this.id = id;
        this.chatName = chatName;
        this.chatImage = chatImage;
        this.description = description;
        this.isGroup = isGroup;
        this.admins = admins;
        this.owner = owner;
        this.users = users;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public Set<User> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<User> admins) {
        this.admins = admins;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getUsers()  {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", chatName='" + chatName + '\'' +
                ", chatImage='" + chatImage + '\'' +
                ", description='" + description + '\'' +
                ", isGroup=" + isGroup +
                ", admins=" + admins +
                ", owner=" + owner +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }
//@Override
//public String toString() {
//    return "Chat{" +
//            "id=" + id +
//            ", chatName='" + chatName + '\'' +
//            ", chatImage='" + chatImage + '\'' +
//            ", description='" + description + '\'' +
//            ", isGroup=" + isGroup +
//            '}';
//}
}
