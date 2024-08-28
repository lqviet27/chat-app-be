package vn.bt.spring.chatappbe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bt.spring.chatappbe.Entity.Chat;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Exception.UserException;
import vn.bt.spring.chatappbe.Payload.Request.UpdateUserRequest;
import vn.bt.spring.chatappbe.Service.ServiceImpl.UserServiceImpl;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private UserServiceImpl userService;
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        logger.info("get user profile");
        User user = this.userService.findUserProfile(jwt);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getUserProfileById(@PathVariable Long userId) throws UserException {
        logger.info("get user by id");
        User user = this.userService.findUserById(userId);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @GetMapping("/commom-groups/{userId}")
    public ResponseEntity<List<Chat>> getCommonGroup(@PathVariable Long userId,
                                                     @RequestHeader("Authorization") String jwt) throws UserException
    {
        logger.info("get common groups");
        User reqUser = this.userService.findUserProfile(jwt);
        logger.info("ReqUser ID: " + reqUser.getId());
        List<Chat> commonGroups = this.userService.findCommnGroups(reqUser.getId(), userId);
        return new ResponseEntity<List<Chat>>(commonGroups, HttpStatus.OK);
    }
    @GetMapping("/{query}")
    public ResponseEntity<List<Object>> searchUser(@PathVariable String query) {
        logger.info("search user and group by query" + query);
        List<Object> result = this.userService.searchUser(query);
        return new ResponseEntity<List<Object>>(result, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(
            @RequestHeader("Authorization") String jwt,
            @RequestBody UpdateUserRequest req) throws UserException
    {
        logger.info("received update user request " + req.toString());
        User user = this.userService.findUserProfile(jwt);
        User updatedUser = this.userService.updateUser(user.getId(), req);
        return new ResponseEntity<User>(updatedUser, HttpStatus.ACCEPTED);
    }

}
