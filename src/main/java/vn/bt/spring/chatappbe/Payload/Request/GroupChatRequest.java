package vn.bt.spring.chatappbe.Payload.Request;

import java.util.List;

public class GroupChatRequest {
    private List<Long> userIds;
    private String chatName;
    private String chatImage;
    private String description;

    public GroupChatRequest() {
    }

    public GroupChatRequest(List<Long> userIds, String chatName, String chatImage, String description) {
        this.userIds = userIds;
        this.chatName = chatName;
        this.chatImage = chatImage;
        this.description = description;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
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

    @Override
    public String toString() {
        return "GroupChatRequest{" +
                "userIds=" + userIds +
                ", chatName='" + chatName + '\'' +
                ", chatImage='" + chatImage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
