package main.web;

import main.Entity.User;
import main.Repositories.UserRepository;
import main.Security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pwdEncoder;

    @PostMapping("/signIn")
    public ResponseEntity singIn(@RequestBody AuthRequest request) {
        try {
            String name = request.getUserName();
            String password = request.getPassword();
            Optional<User> user = userRepository.findUserByUserName(name);
            boolean passwordMatch = false;
            if (user.isPresent()) {
                User us = user.get();
                passwordMatch = pwdEncoder.matches(password, us.getPassword());
            }
            if (!passwordMatch)
                throw new BadCredentialsException("Invalid username or password");
            String token = jwtTokenProvider.createToken(
                    name,
                    user
                            .orElseThrow(() -> new UsernameNotFoundException("User not found")).getRoles());

            Map<Object, Object> model = new HashMap<>();
            model.put("userName", name);
            model.put("token", token);

            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }


        /*        @PostMapping("/singIn")
        public ResponseEntity singIn(@RequestBody AuthRequest request){
            try{
            String name = request.getUserName();
            String token = jwtTokenProvider.createToken(name,
                    userRepository.findUserByUserName(name)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found")).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("userName", name);
            model.put("token", token);

            return ResponseEntity.ok(model);
            } catch (AuthenticationException e ){
                throw new BadCredentialsException("Invalid username or password");
            }
        }*/


}
