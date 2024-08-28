package vn.bt.spring.chatappbe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.bt.spring.chatappbe.Entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query("select m from Message m join m.chat c where c.id=:chatId")
    public List<Message> findMessagesByChatId(@Param("chatId") Long chatId);
}

