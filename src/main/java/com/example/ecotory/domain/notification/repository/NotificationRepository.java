package com.example.ecotory.domain.notification.repository;

import com.example.ecotory.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    // 특정 멤버의 모든 알림 조회
    List<Notification> findByMemberId(String memberId);

    // 특정 멤버의 읽지 않은 알림 조회
    List<Notification> findByMemberIdAndIsReadFalse(String memberId);
}
