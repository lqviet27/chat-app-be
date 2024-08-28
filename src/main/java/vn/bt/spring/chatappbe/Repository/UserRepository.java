package vn.bt.spring.chatappbe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.bt.spring.chatappbe.Entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email like %:query% OR u.name like %:query%")
    public List<User> searchUser(@Param("query") String query);
}
