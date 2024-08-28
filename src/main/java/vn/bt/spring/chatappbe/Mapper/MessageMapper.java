package vn.bt.spring.chatappbe.Mapper;

import vn.bt.spring.chatappbe.DTO.MessageDTO;
import vn.bt.spring.chatappbe.Entity.Message;

public class MessageMapper {
    public static MessageDTO toDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getChatId(),
                message.getUser().getId(),
                message.getContent(),
                message.getTimestamp()
        );
    }
}
