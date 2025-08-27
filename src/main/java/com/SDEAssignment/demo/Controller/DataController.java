package com.SDEAssignment.demo.Controller;

import com.SDEAssignment.demo.Entity.User;
import com.SDEAssignment.demo.Entity.UserData;
import com.SDEAssignment.demo.Repository.DataRepository;
import com.SDEAssignment.demo.Repository.UserRepository;
//import com.SDEAssignment.demo.Service.DataService;
import com.SDEAssignment.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("User")
public class DataController {
@Autowired
private   UserService userService;
//@Autowired
//private  DataService dataService;
@Autowired
   private  UserRepository userRepository;
@Autowired
private DataRepository dataRepository;
    @GetMapping
    public ResponseEntity<?> getNotes(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        return ResponseEntity.of(dataRepository.findByUser(user));
    }

    @PostMapping
    public ResponseEntity<?> createMessage(Authentication auth, @RequestBody UserData userData) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        if (dataRepository.findByUser(user).isPresent()) {
            return ResponseEntity.badRequest().body("Message already exists, use PUT to update");
        }

        UserData userData1 = new UserData();
        userData1.setMessage(userData.getMessage());
        userData1.setUser(user);
        return ResponseEntity.ok(dataRepository.save(userData1));
    }

    @PutMapping
    public ResponseEntity<?> updateMessage(Authentication auth, @RequestBody UserData req) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        UserData data = dataRepository.findByUser(user).orElseThrow(() -> new RuntimeException("No message found"));
        data.setMessage(req.getMessage());
        return ResponseEntity.ok(dataRepository.save(data));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMessage(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        UserData data = dataRepository.findByUser(user).orElseThrow(() -> new RuntimeException("No message found"));
//        dataRepository.delete(data);
        dataRepository.deleteById(data.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
