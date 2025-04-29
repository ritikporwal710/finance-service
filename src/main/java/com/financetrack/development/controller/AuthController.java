package com.financetrack.development.controller;

import org.springframework.web.bind.annotation.RestController;

import com.financetrack.development.Repository.UserRepository;
import com.financetrack.development.dto.ApiResponse;
import com.financetrack.development.dto.LoginDTO;
import com.financetrack.development.dto.RegisterDTO;
import com.financetrack.development.dto.UserDetailDTO;
import com.financetrack.development.model.Transaction;
import com.financetrack.development.model.User;
import com.financetrack.development.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class AuthController {

    private UserDetailDTO userDetailDTO;


    @Autowired
    private UserRepository userRepository;

    

    @Autowired
    private UserService userService;  
    
    @GetMapping("/get-user")
        public ResponseEntity<ApiResponse<UserDetailDTO>> getUser(HttpServletRequest request){
            UUID id = UUID.fromString((String) request.getAttribute("userId"));
            User user = userRepository.findById(id).orElse(null);
            System.out.println("user in controller: " + user);
            UserDetailDTO userDetailDTO = new UserDetailDTO();
            userDetailDTO.setFullName(user.getFullName());
            userDetailDTO.setEmail(user.getEmail());
            ApiResponse<UserDetailDTO> response = new ApiResponse<>(true,"User fetched successfully", userDetailDTO );
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }
        
         


    @PostMapping("/signup")
    public User registerUser(@RequestBody RegisterDTO data) {
        // User createduser = userRepository.save(data);                

        System.out.println("data in controller: " + data);
        User newUser = userService.createUser(data);
        return newUser;
    }     

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
            ResponseEntity<String> result = userService.loginUser(loginDTO);
            System.out.println("result in controller: " + result);


            if (result.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok().header("Set-Cookie", result.getHeaders().get("Set-Cookie").get(0)).body(result.getBody());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.getBody());
            }
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logoutUser(HttpServletRequest request) {
        try {
            ResponseEntity<String> result = userService.logoutUser();
            ApiResponse<String> response = new ApiResponse<>(true, "Logout successful", null);
            
            return ResponseEntity.ok()
                .headers(result.getHeaders()) // This will include all Set-Cookie headers
                .body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "Logout failed", null));
        }
    }
    
}
