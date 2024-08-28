package vn.bt.spring.chatappbe.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CustomUserService implements UserDetailsService {
    private static final Logger LOGGER = Logger.getLogger(CustomUserService.class.getName());
    private UserRepository userRepository;
    @Autowired
    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);

        if(user == null) {
            LOGGER.warning("User not found " + email);
            throw new UsernameNotFoundException("User not found " + email);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
