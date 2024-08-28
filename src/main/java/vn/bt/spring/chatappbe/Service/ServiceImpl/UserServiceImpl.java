package vn.bt.spring.chatappbe.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import vn.bt.spring.chatappbe.Config.TokenProvider;
import vn.bt.spring.chatappbe.Entity.Chat;
import vn.bt.spring.chatappbe.Entity.Profile;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Exception.UserException;
import vn.bt.spring.chatappbe.Payload.Request.UpdateUserRequest;
import vn.bt.spring.chatappbe.Repository.ChatRepository;
import vn.bt.spring.chatappbe.Repository.UserRepository;
import vn.bt.spring.chatappbe.Service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    private UserRepository userRepository;
    private ChatRepository chatRepository;
    private TokenProvider tokenProvider;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ChatRepository chatRepository, TokenProvider tokenProvider) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }



    @Override
    public User findUserById(Long id) throws UserException {
        return this.userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email = this.tokenProvider.getEmailFromToken(jwt);
        logger.info("Extracted email from token: " + email);
        if (email == null) {
            throw new BadCredentialsException("Received invalid token");
        }

        User user = this.userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("User not found");
        }
        return user;
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequest req) throws UserException {
        logger.info("Updating user with ID: " + userId);
        User user = this.findUserById(userId);
        if(req.getName() != null ){
            user.setName(req.getName());
        }

        if(req.getProfile() != null){
            Profile profile = user.getProfile();
            if(profile == null ){
                profile = new Profile();
            }
            Profile reqProfile = req.getProfile();
            if(reqProfile.getBio() != null){
                profile.setBio(reqProfile.getBio());
            }
            if(reqProfile.getBirthday() != null){
                profile.setBirthday(reqProfile.getBirthday());
            }
            if(reqProfile.getGender() != null){
                profile.setGender(reqProfile.getGender());
            }
            if(reqProfile.getImage() != null){
                profile.setImage(reqProfile.getImage());
            }

            user.setProfile(profile);
        }

        User updatedUser = this.userRepository.save(user);
        logger.info("Updated user: " + updatedUser.toString());
        return updatedUser;
    }

    @Override
    public List<Object> searchUser(String query) {
        List<User> users = this.userRepository.searchUser(query);
        List<Chat> groups = this.chatRepository.searchGroupChats(query);
        List<Object> res = new ArrayList<>();
        res.addAll(users);
        res.addAll(groups);
        return res;
    }

    @Override
    public List<Chat> findCommnGroups(Long currentUserId, Long userId)  {
        List<Chat> currentChats = this.chatRepository.findChatByUserId(currentUserId);
        List<Chat> userChats = this.chatRepository.findChatByUserId(userId);
        return currentChats.stream()
                .filter(Chat::isGroup)
                .filter(userChats::contains)
                .collect(Collectors.toList());
    }
}
