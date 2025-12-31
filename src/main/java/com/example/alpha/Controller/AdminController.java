package com.example.alpha.Controller;

import com.example.alpha.Entity.User;
import com.example.alpha.Service.UserService;
import com.example.alpha.cache.AppCache;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
       List<User> all =  userService.getAll();
       if(all != null && !all.isEmpty()){
           return new ResponseEntity<>(all, HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);

    }

    // Agenda here is that if bean is created then
    // chnage in mongodb not show so we have to restart but we use this mapping and change the data
    @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
