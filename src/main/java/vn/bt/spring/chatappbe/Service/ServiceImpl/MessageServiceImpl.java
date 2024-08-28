package vn.bt.spring.chatappbe.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.bt.spring.chatappbe.Entity.Chat;
import vn.bt.spring.chatappbe.Entity.Message;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Exception.ChatException;
import vn.bt.spring.chatappbe.Exception.MessageException;
import vn.bt.spring.chatappbe.Exception.UserException;
import vn.bt.spring.chatappbe.Payload.Request.SendMessageRequest;
import vn.bt.spring.chatappbe.Repository.ChatRepository;
import vn.bt.spring.chatappbe.Repository.MessageRepository;
import vn.bt.spring.chatappbe.Service.MessageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;
    private UserServiceImpl userService;
    private ChatServiceImpl chatService;
//    @Autowired
//    private ChatRepository chatRepository;
    private static final Logger logger = Logger.getLogger(MessageServiceImpl.class.getName());
    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserServiceImpl userService, ChatServiceImpl chatService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.chatService = chatService;
    }
    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
        User user = this.userService.findUserById(req.getUserId());
        Chat chat = this.chatService.findChatById(req.getChatId());
        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());
        Message savedMessage = this.messageRepository.save(message);
        chat.getMessages().add(message);
//        this.chatRepository.save(chat);
        return savedMessage;
    }

    @Override
    public List<Message> getChatMessages(Long chatId, User reqUser) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);
        if (!chat.getUsers().contains(reqUser)){
            throw  new UserException("You are not in this chat");
        }
        return this.messageRepository.findMessagesByChatId(chatId);
    }

    @Override
    public Message findMessageById(Long id) throws MessageException {
        return this.messageRepository.findById(id).orElseThrow(()->new MessageException("Message not found"));
    }

    @Override
    public void deleteMessage(Long id, User reqUser) throws MessageException {
        Message message = this.messageRepository.findById(id).orElseThrow(()->new MessageException("Message not found"));
        if (message.getUser().getId() == reqUser.getId()) {
            this.messageRepository.delete(message);
        }else {
            throw new MessageException("You are not the owner of this message");
        }
    }
}
