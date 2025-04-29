package com.financetrack.development.service;

import java.net.http.HttpHeaders;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.financetrack.development.Repository.BillRepository;
import com.financetrack.development.Repository.NotificationRepository;
import com.financetrack.development.Repository.UserRepository;
import com.financetrack.development.dto.LoginDTO;
import com.financetrack.development.dto.RegisterDTO;
import com.financetrack.development.model.Bill;
import com.financetrack.development.model.Notification;
import com.financetrack.development.model.User;
import com.financetrack.development.security.JwtUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    private boolean isDevelopment() {
        return "development".equals(System.getenv("SPRING_PROFILES_ACTIVE"));
    }

//     public int dueDays(Date dueDate) {
//         System.out.println("dueDate in function: " + dueDate);
//     LocalDate today = LocalDate.now();
//     System.out.println("today: " + today);
//     LocalDate due = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//     System.out.println("due: " + due);
//     return (int)ChronoUnit.DAYS.between(today, due);
// }

public int dueDays(Date dueDate) {
    LocalDate today = LocalDate.now();
    System.out.println("today: " + today);

    LocalDate due;
    if (dueDate instanceof java.sql.Date) {
        due = ((java.sql.Date) dueDate).toLocalDate();
    } else {
        due = dueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    System.out.println("due: " + due);
    return (int) ChronoUnit.DAYS.between(today, due);
}



    
    public User createUser(RegisterDTO data){
        System.out.println("entered in service: ");
        // User createdUser = userRepository.save(data);
        System.out.println(" data in service: " + data);

        String password = data.getPassword();

        System.out.println("password in service: " + password);

        // // create hash password and store in database
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String newHashedPassword = passwordEncoder.encode(password);

        System.out.println("newHashedPassword: " + newHashedPassword);

        User user = new User();

        user.setFullName(data.getFullName());
        user.setEmail(data.getEmail());
        user.setPasswordHash(newHashedPassword);

        User createdUser = userRepository.save(user);

        return createdUser;                     
    }

    //  public String loginUser(LoginDTO loginDTO){
    //     System.out.println("loginDTO: " + loginDTO);
    //     // check if the user exist in the database , 
    //     String email = loginDTO.getEmail();
    //     String password = loginDTO.getPassword();

    //     System.out.println("email: " + email);
    //     System.out.println("password: " + password);

    //     // User user = userRepository.findByEmail(email);
    //     Optional<User> checked = userRepository.findByEmail(email);

    //     User user;

    //     if(!checked.isPresent()) return "User does not exist";

    //     user = checked.get();
      

    //     // if not then return user does not exist
    //     // hash the passowrd

      
    //     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //     boolean isPasswordMatch = passwordEncoder.matches(password, user.getPasswordHash());

    //     if(!isPasswordMatch) return "Incorrect password";

    //     System.out.println("login successful");

    //     System.out.println("user id: " + user.getId());


    //     String token = jwtUtils.generateToken(user.getId());

    //     System.out.println("token in service: " + token);
        

    //     // Need to inject HttpServletResponse to set cookies
    //     HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    //     Cookie cookie = new Cookie("token", token);
    //     cookie.setHttpOnly(true); // Set to false so it's visible in browser
    //     cookie.setSecure(true); // Set to false for development/HTTP
    //     cookie.setPath("/");
    //     cookie.setMaxAge(3600); // 1 hour
    //     response.addCookie(cookie);

    //     System.out.println("cookie in service: { name: " + cookie.getName() + 
    //                       ", value: " + cookie.getValue() + 
    //                       ", path: " + cookie.getPath() + 
    //                       ", maxAge: " + cookie.getMaxAge() + " }");

    //     return "Login successful";
        


    // }

    public ResponseEntity<String> loginUser(LoginDTO loginDTO) {
        try {
            
       
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
    
        Optional<User> checked = userRepository.findByEmail(email);
        if (!checked.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
    
        User user = checked.get();
    
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }
    
        String token = jwtUtils.generateToken(user.getId());
    
        // Create the cookie
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                // .secure(!isDevelopment()) // Use `false` for localhost/testing
                .secure(false)
                .path("/")
                .maxAge(Duration.ofHours(24)) // 24 hours
                .sameSite("Strict")
                .build();

        // System.out.println("cookie in service: " + cookie);
       
        List<Bill> bills = billRepository.findBillsDueInNext30Days(user.getId());

        // System.out.println("bills getting: " + bills);

        // notificationRepository.deleteAllByUserId(user.getId());

        List<Bill> afterBills = billRepository.findBillsDueInNext30Days(user.getId());


        // System.out.println("notifications deleting: " + afterBills);

        for (Bill bill : bills) {
            System.out.println("bill first one: " + bill);
            Notification notification = new Notification();
            notification.setName(bill.getBillName());
            System.out.println("notification name: " + notification.getName());
            notification.setAmount(bill.getAmount());
            System.out.println("notification amount: " + notification.getAmount());
            System.out.println("notification dueDate: " + bill.getDueDate());
            System.out.println("notification dueDays: " + dueDays(bill.getDueDate()));
            notification.setDueDays(dueDays(bill.getDueDate()));
            System.out.println("notification dueDays: " + notification.getDueDays());
            notification.setStatus("active");
            System.out.println("notification status: " + notification.getStatus());
            notification.setTotalLimit(bill.getAmount());
            System.out.println("notification totalLimit: " + notification.getTotalLimit());
            notification.setType("reminder");
            notification.setUser(user);
            System.out.println("notification after all details: " + notification);
            notificationRepository.save(notification);
        }

    
        return ResponseEntity.ok()
        .header("Set-Cookie", cookie.toString())

                .body("Login successful");


                } catch (Exception e) {
                    System.out.println("error in service of login: " + e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
                }
    }
    

    // public String logoutUser(){
    //     Cookie cookie = new Cookie("token", null);
    //     cookie.setHttpOnly(true);
    //     cookie.setSecure(true); // true if using HTTPS
    //     cookie.setPath("/");
    //     cookie.setMaxAge(0);    

    //     return "Logout successful";
    // }
    public ResponseEntity<String> logoutUser() {
        // Create multiple cookies to ensure all variations are cleared
        ResponseCookie clearCookie1 = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        ResponseCookie clearCookie2 = ResponseCookie.from("token", "")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", clearCookie1.toString())
                .header("Set-Cookie", clearCookie2.toString())
                .header("Clear-Site-Data", "\"cookies\"")
                .body("Logout successful");
    }
    
    
   
}


