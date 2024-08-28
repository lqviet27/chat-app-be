package vn.bt.spring.chatappbe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.bt.spring.chatappbe.Entity.Chat;
import vn.bt.spring.chatappbe.Entity.User;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("select c from Chat c join c.users u where u.id=:userId")
    public List<Chat> findChatByUserId(@Param("userId") Long userId);
    @Query("select c from Chat c where c.isGroup=true and c.chatName like %:query%")
    public List<Chat> searchGroupChats(@Param("query") String query);
    @Query("select c from Chat c where c.isGroup=false and :user member of c.users and :reqUser member of c.users")
    public Chat findSingleChatByUserIds(@Param("user")User user, @Param("reqUser") User reqUser);
}
