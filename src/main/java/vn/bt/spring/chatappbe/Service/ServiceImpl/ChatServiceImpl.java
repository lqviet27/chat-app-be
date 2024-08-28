package vn.bt.spring.chatappbe.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import vn.bt.spring.chatappbe.Entity.Chat;
import vn.bt.spring.chatappbe.Entity.Message;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Exception.ChatException;
import vn.bt.spring.chatappbe.Exception.UserException;
import vn.bt.spring.chatappbe.Payload.Request.GroupChatRequest;
import vn.bt.spring.chatappbe.Repository.ChatRepository;
import vn.bt.spring.chatappbe.Repository.MessageRepository;
import vn.bt.spring.chatappbe.Service.ChatService;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private UserServiceImpl userService;
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;
    @Autowired
    public ChatServiceImpl(UserServiceImpl userService, ChatRepository chatRepository, MessageRepository messageRepository) {
        this.userService = userService;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;

    }
    @Override
    public Chat createChat(User reqUser, Long userId) throws UserException {
        User user = this.userService.findUserById(userId);
        Chat isChatExist = this.chatRepository.findSingleChatByUserIds(user, reqUser);
        if(isChatExist != null) {
            return  isChatExist;
        }
        Chat chat = new Chat();
        chat.setOwner(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);

        return this.chatRepository.save(chat);
    }

    @Override
    public Chat createGroupChat(GroupChatRequest groupChatRequest, User reqUser) throws UserException {
        Chat groupChat = new Chat();
        groupChat.setGroup(true);
        groupChat.setChatImage(groupChatRequest.getChatImage());
        groupChat.setChatName(groupChatRequest.getChatName());
        groupChat.setOwner(reqUser);
        groupChat.getAdmins().add(reqUser);
        for(Long userId : groupChatRequest.getUserIds()) {
            User user = this.userService.findUserById(userId);
            groupChat.getUsers().add(user);
        }
        return this.chatRepository.save(groupChat);
    }

    @Override
    public Chat findChatById(Long chatId) throws ChatException {
        Chat chat = this.chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException("The requested chat is not found"));
        List<Message> messages = this.messageRepository.findMessagesByChatId(chatId);
        chat.setMessages(messages);
        return chat;
    }

    @Override
    public List<Chat> findAllChatsByUserId(Long userId) throws UserException {
        User user = this.userService.findUserById(userId);
        List<Chat> chats = this.chatRepository.findChatByUserId(user.getId());
        chats.forEach(chat ->{
            List<Message> messages = this.messageRepository.findMessagesByChatId(chat.getId());
            chat.setMessages(messages);
        });
        return chats;
    }

    @Override
    public Chat addUserToGroup(Long userId, Long chatId, User reqUser) throws ChatException, UserException {
        Chat chat = this.chatRepository.findById(chatId).orElseThrow(() -> new ChatException("Chat not found"));
        User user = this.userService.findUserById(userId);
        if (chat.getAdmins().contains(reqUser)) {
            chat.getUsers().add(user);
            return this.chatRepository.save(chat);
        }else{
            throw new UserException("You are not an admin of this group");
        }
    }

    @Override
    public Chat removeUserFromGroup(Long userId, Long chatId, User reqUser) throws ChatException, UserException {
        Chat chat = this.chatRepository.findById(chatId).orElseThrow(() -> new ChatException("Chat not found"));
        User user = this.userService.findUserById(userId);
        if (chat.getAdmins().contains(reqUser)) {
            chat.getUsers().remove(user);
            return this.chatRepository.save(chat);
        }else if(chat.getUsers().contains(reqUser)){
            if(user.getId() == reqUser.getId()){
                chat.getUsers().remove(user);
                return this.chatRepository.save(chat);
            }
        }
        throw new UserException("You don't have access to remove the user");
    }

    @Override
    public void deleteChat(Long chatId) throws ChatException {
        Chat chat = this.chatRepository.findById(chatId).orElseThrow(() -> new ChatException("Chat not found"));
        //messageRepository.deleteAll(chat.getMessages()); // them dong nay la do @OneToMany() da bo phan orphanRemoval = true trong Chat
        this.chatRepository.delete(chat);
    }

    @Override
    public Chat updateGroup(Long chatId, GroupChatRequest groupChatRequest, User reqUser) throws ChatException, UserException {
        Chat chat = this.chatRepository.findById(chatId).orElseThrow(() -> new ChatException("Chat not found"));
        if (!chat.isGroup()) {
            throw new ChatException("Chat is not a group chat");
        }
        if (!chat.getAdmins().contains(reqUser)) {
            throw new UserException("You are not an admin of this group");
        }
        if (groupChatRequest.getChatName() != null) {
            chat.setChatName(groupChatRequest.getChatName());
        }
        if (groupChatRequest.getChatImage() != null) {
            chat.setChatImage(groupChatRequest.getChatImage());
        }
        if (groupChatRequest.getDescription() != null) {
            chat.setDescription(groupChatRequest.getDescription());
        }
        return this.chatRepository.save(chat);
    }
}
