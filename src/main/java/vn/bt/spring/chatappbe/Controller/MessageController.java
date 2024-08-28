package vn.bt.spring.chatappbe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.bt.spring.chatappbe.Entity.Message;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Exception.ChatException;
import vn.bt.spring.chatappbe.Exception.MessageException;
import vn.bt.spring.chatappbe.Exception.UserException;
import vn.bt.spring.chatappbe.Payload.Request.SendMessageRequest;
import vn.bt.spring.chatappbe.Payload.Response.ApiResponse;
import vn.bt.spring.chatappbe.Service.ServiceImpl.MessageServiceImpl;
import vn.bt.spring.chatappbe.Service.ServiceImpl.UserServiceImpl;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private static final Logger logger = Logger.getLogger(MessageController.class.getName());
    private MessageServiceImpl messageService;
    private UserServiceImpl userService;
    @Autowired
    public MessageController(MessageServiceImpl messageService, UserServiceImpl userService) {
        this.messageService = messageService;
        this.userService = userService;
    }
    @PostMapping("/create")
    public ResponseEntity<Message> sendMessage(
            @RequestBody SendMessageRequest sendMessageRequest,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException
    {
        User user = this.userService.findUserProfile(jwt);
        sendMessageRequest.setUserId(user.getId());
        Message message = this.messageService.sendMessage(sendMessageRequest);
        logger.info("Message sent successfully" + message);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
    @GetMapping("/{chatId}")
    public ResponseEntity<List<Message>> getChatMessages(
            @PathVariable Long chatId,
            @RequestHeader("Authorization") String jwt) throws ChatException, UserException
    {
        User user = this.userService.findUserProfile(jwt);
        List<Message> messages = this.messageService.getChatMessages(chatId, user);
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }
    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessage(
            @PathVariable Long messageId,
            @RequestHeader("Authorization") String jwt) throws UserException, MessageException
    {
        User user = this.userService.findUserProfile(jwt);
        this.messageService.deleteMessage(messageId, user);
        return new ResponseEntity<ApiResponse>(new ApiResponse("message deleted successfully", true), HttpStatus.OK);
    }
}
