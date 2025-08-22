package com.leavetracker.controller;

import com.leavetracker.model.User;
import com.leavetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User saved = userService.saveUser(user);
        return ResponseEntity.status(201).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        // Username is actually the associateId (int32)
        Integer associateId;
        try {
            associateId = Integer.parseInt(loginRequest.getUsername());
        } catch (NumberFormatException e) {
            System.out.println("AssociateId is not a valid integer: " + loginRequest.getUsername());
            return ResponseEntity.status(401).body("Invalid Username or password");
        }
        // Query user by associateId (int32)
        Optional<User> userOpt = userService.getUserByAssociateId(associateId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean matches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
            System.out.println("Password match: " + matches);
            if (matches) {
                System.out.println("Login successful for associateId: " + user.getAssociateId() + ", role: " + user.getRole());
                // Return only associateId and role
                return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
                    put("associateId", user.getAssociateId());
                    put("role", user.getRole());
                }});
            } else {
                System.out.println("Password mismatch for associateId: " + user.getAssociateId());
            }
        } else {
            System.out.println("No user found for associateId: " + associateId);
        }
        return ResponseEntity.status(401).body("Invalid Username or password");
    }


}
