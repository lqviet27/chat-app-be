package vn.bt.spring.chatappbe.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.bt.spring.chatappbe.Config.CustomUserService;
import vn.bt.spring.chatappbe.Config.TokenProvider;
import vn.bt.spring.chatappbe.Entity.User;
import vn.bt.spring.chatappbe.Exception.UserException;
import vn.bt.spring.chatappbe.Payload.Request.LoginRequest;
import vn.bt.spring.chatappbe.Payload.Response.AuthResponse;
import vn.bt.spring.chatappbe.Repository.UserRepository;
import vn.bt.spring.chatappbe.Service.ServiceImpl.UserServiceImpl;
import vn.bt.spring.chatappbe.Service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenProvider tokenProvider;
    private CustomUserService customUserService;
    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, CustomUserService customUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.customUserService = customUserService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String name = user.getName();
        String password = user.getPassword();

        User isUser = this.userRepository.findByEmail(email);
        if (isUser != null) {
            throw new UserException("Email of user already exists");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(passwordEncoder.encode(password));

        this.userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse(jwt, true);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login (@RequestBody LoginRequest req) throws UserException, BadCredentialsException {
        String email = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse(jwt, true);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    public Authentication authenticate (String email, String password) {
        UserDetails userDetails = this.customUserService.loadUserByUsername(email);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid email");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
