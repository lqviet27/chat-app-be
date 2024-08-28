package vn.bt.spring.chatappbe.Service;

import vn.bt.spring.chatappbe.Entity.Chat;
import vn.bt.spring.chatappbe.Entity.Message;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Exception.ChatException;
import vn.bt.spring.chatappbe.Exception.MessageException;
import vn.bt.spring.chatappbe.Exception.UserException;
import vn.bt.spring.chatappbe.Payload.Request.SendMessageRequest;

import java.util.List;

public interface MessageService {
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;
    public List<Message> getChatMessages(Long chatId, User reqUser) throws ChatException, UserException;
    public Message findMessageById(Long id) throws MessageException;
    public void deleteMessage(Long id, User reqUser) throws MessageException;
}
