package com.paypal.notification_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.paypal.notification_service.entity.Notification;
import com.paypal.notification_service.entity.Transaction;
import com.paypal.notification_service.repository.NotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;

    public NotificationConsumer(NotificationRepository notificationRepository, ObjectMapper mapper) {
        this.notificationRepository = notificationRepository;
    }

//    @KafkaListener(topics = "txn-initiated", groupId = "notification-group")
//    public void listener(String message) throws JsonProcessingException {
//        Transaction txn = mapper.readValue(message,Transaction.class);
//        Notification notification = new Notification();
//        notification.setUserId(txn.get);
//
//    }
    @KafkaListener(topics = "txn-initiated",groupId = "notification-group")
    public void consumerTransaction(Transaction transaction){

        Notification notification = new Notification();
        notification.setUserId(transaction.getSenderId());
        notification.setMessage("₹" + transaction.getAmount() + " received from user " + transaction.getSenderId());
        notification.setSentAt(LocalDateTime.now());

        notificationRepository.save(notification);
        System.out.println("Notification saved:"+notification);
    }

}
