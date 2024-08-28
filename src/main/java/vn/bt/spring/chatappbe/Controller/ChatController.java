package vn.bt.spring.chatappbe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bt.spring.chatappbe.Entity.Chat;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Exception.ChatException;
import vn.bt.spring.chatappbe.Exception.UserException;
import vn.bt.spring.chatappbe.Payload.Request.GroupChatRequest;
import vn.bt.spring.chatappbe.Payload.Request.SingleChatRequest;
import vn.bt.spring.chatappbe.Payload.Response.ApiResponse;
import vn.bt.spring.chatappbe.Service.ChatService;
import vn.bt.spring.chatappbe.Service.ServiceImpl.ChatServiceImpl;
import vn.bt.spring.chatappbe.Service.ServiceImpl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private ChatServiceImpl chatService;
    private UserServiceImpl userService;

    @Autowired
    public ChatController(ChatServiceImpl chatService, UserServiceImpl userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/single")
    public ResponseEntity<Chat> createChat(@RequestBody SingleChatRequest singleChatRequest,
                                           @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = this.userService.findUserProfile(jwt);
        Chat chat = this.chatService.createChat(reqUser, singleChatRequest.getUserId());

        return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupChat(@RequestBody GroupChatRequest groupChatRequest,
                                                @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = this.userService.findUserProfile(jwt);
        Chat chat = this.chatService.createGroupChat(groupChatRequest, reqUser);
        return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatById(@PathVariable Long chatId) throws ChatException {
        Chat chat = this.chatService.findChatById(chatId);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findChatsByUserId(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = this.userService.findUserProfile(jwt);
        List<Chat> chats = this.chatService.findAllChatsByUserId(user.getId());
        return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<Chat> getGroupChatById(@PathVariable Long groupId) throws ChatException {
        Chat chat = this.chatService.findChatById(groupId);
        if (!chat.isGroup()) {
            throw new ChatException("Chat is not a group chat");
        }
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupChat(
            @PathVariable Long chatId,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        User reqUser = this.userService.findUserProfile(jwt);
        Chat chat = this.chatService.addUserToGroup(userId, chatId, reqUser);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserFromGroupChat(
            @PathVariable Long chatId,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        //removed by admin or leave group
        User reqUser = this.userService.findUserProfile(jwt);
        Chat chat = this.chatService.removeUserFromGroup(userId, chatId, reqUser);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChat(@PathVariable Long chatId, @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        User reqUser = this.userService.findUserProfile(jwt);
        this.chatService.deleteChat(chatId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Chat deleted", true), HttpStatus.OK);
    }

    @PutMapping("/update-group/{chatId}")
    public ResponseEntity<ApiResponse> updateGroup(
            @PathVariable Long chatId,
            @RequestBody GroupChatRequest groupChatRequest,
            @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        User reqUser = this.userService.findUserProfile(jwt);
        Chat chat = this.chatService.updateGroup(chatId, groupChatRequest, reqUser);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Group updated successfully.", true), HttpStatus.OK);
    }
}
