package vn.bt.spring.chatappbe.Controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import vn.bt.spring.chatappbe.DTO.MessageDTO;
import vn.bt.spring.chatappbe.Entity.Message;
import vn.bt.spring.chatappbe.Mapper.MessageMapper;
import vn.bt.spring.chatappbe.Payload.Request.SendMessageRequest;
import vn.bt.spring.chatappbe.Service.ServiceImpl.MessageServiceImpl;


@Controller
public class RealTimeChat {
    private static final Logger logger = LoggerFactory.getLogger(RealTimeChat.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageServiceImpl messageService;
    @Autowired
    public RealTimeChat(SimpMessagingTemplate simpMessagingTemplate, MessageServiceImpl messageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }
    @MessageMapping("/message")
    @SendTo("/group/public")
    public MessageDTO receiveMessage(@Payload SendMessageRequest sendMessageRequest) throws Exception {
        logger.info("Received SendMessageRequest: userId={}, chatId={}, content={}",
                sendMessageRequest.getUserId(), sendMessageRequest.getChatId(), sendMessageRequest.getContent());
        if (sendMessageRequest.getUserId() == null || sendMessageRequest.getChatId() == null) {
            logger.error("Invalid message data: userId or chatId is null");
            throw new IllegalArgumentException("userId and chatId must not be null");
        }

        Message savedMessage = messageService.sendMessage(sendMessageRequest);

        MessageDTO messageDTO = MessageMapper.toDTO(savedMessage);

        simpMessagingTemplate.convertAndSend("/group/" + savedMessage.getChat().getId(), messageDTO);
        logger.info("Broadcasted message to /group/{}", savedMessage.getChat().getId());

        return messageDTO;
    }
}
