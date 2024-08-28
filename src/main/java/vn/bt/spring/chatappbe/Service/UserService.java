package vn.bt.spring.chatappbe.Service;

import vn.bt.spring.chatappbe.Entity.Chat;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Exception.UserException;
import vn.bt.spring.chatappbe.Payload.Request.UpdateUserRequest;

import java.util.List;
import java.util.Objects;

public interface UserService {
    public User findUserById(Long id) throws UserException;

    public User findUserProfile(String jwt) throws UserException;

    public User updateUser(Long userId, UpdateUserRequest req) throws UserException;

    public List<Object> searchUser(String query);

    public List<Chat> findCommnGroups(Long currentUserId, Long userId) ;
}
