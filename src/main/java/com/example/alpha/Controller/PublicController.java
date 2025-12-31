package com.example.alpha.Controller;

import com.example.alpha.Entity.User;
import com.example.alpha.Service.UserDetailsServiceImpl;
import com.example.alpha.Service.UserService;
import com.example.alpha.dto.UserDTO;
import com.example.alpha.utilis.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs")
public class PublicController {

    @Autowired
    private UserService userService;

//    @PostMapping("/create-user")
//    public void createUser(@RequestBody User user){
//        userService.saveNewUser(user);
//    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO user){
        try {
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setUserName(user.getUserName());
            newUser.setPassword(user.getPassword());
            newUser.setSentimentAnalysis(user.isSentimentAnalysis());
            userService.saveNewUser(newUser); // make sure password is encoded in service
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (Exception e){
            log.error("Error during user signup", e);
            return new ResponseEntity<>("Signup failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());

            // Generate JWT
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            // Return JWT in response
            return ResponseEntity.ok(Map.of("jwt", jwt));
        } catch (BadCredentialsException e){
            log.error("Invalid credentials", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            log.error("Login error", e);
            return new ResponseEntity<>("Something went wrong: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
