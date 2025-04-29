package com.financetrack.development.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.financetrack.development.Repository.NotificationRepository;
import com.financetrack.development.dto.ApiResponse;
import com.financetrack.development.model.Notification;
import com.financetrack.development.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get-notifications")
    public ResponseEntity<ApiResponse<List<Notification>>> getMethodName(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        System.out.println("userId in controller: " + userId);
        List<Notification> notification = notificationRepository.getNotificationByUserIdAndDueDaysLessThanEqual(userId, 30);

        ApiResponse<List<Notification>> entity = new ApiResponse<>(true, "Notification Fetched Successfully", notification);
        return ResponseEntity.status(200).body(entity);
    }
    
    
}
