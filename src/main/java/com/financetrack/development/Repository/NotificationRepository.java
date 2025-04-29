package com.financetrack.development.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financetrack.development.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> getNotificationByUserIdAndDueDaysLessThanEqual(UUID userId, int dueDays);

    void deleteAllByUserId(UUID userId);
}
