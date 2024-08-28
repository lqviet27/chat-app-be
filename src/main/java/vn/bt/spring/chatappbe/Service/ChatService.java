package vn.bt.spring.chatappbe.Service;

import vn.bt.spring.chatappbe.Entity.Chat;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Exception.ChatException;
import vn.bt.spring.chatappbe.Exception.UserException;
import vn.bt.spring.chatappbe.Payload.Request.GroupChatRequest;

import java.util.List;

public interface ChatService {
    public Chat createChat(User reqUser, Long userId) throws UserException;
    public Chat createGroupChat(GroupChatRequest groupChatRequest, User reqUser) throws UserException;
    public Chat findChatById(Long chatId) throws ChatException;
    public List<Chat> findAllChatsByUserId(Long userId) throws UserException;
    public Chat addUserToGroup(Long userId, Long chatId, User reqUser) throws ChatException, UserException;
    public Chat removeUserFromGroup(Long userId, Long chatId, User reqUser) throws ChatException, UserException;
    public void deleteChat(Long chatId) throws ChatException;
    public Chat updateGroup(Long chatId, GroupChatRequest groupChatRequest, User reqUser) throws ChatException, UserException;
}
