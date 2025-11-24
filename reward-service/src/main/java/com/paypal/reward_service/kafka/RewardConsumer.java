package com.paypal.reward_service.kafka;

import com.paypal.reward_service.entity.Reward;
import com.paypal.reward_service.entity.Transaction;
import com.paypal.reward_service.repository.RewardRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Component
public class RewardConsumer {

    private final RewardRepository rewardRepository;
    private final ObjectMapper mapper;

    public RewardConsumer(RewardRepository rewardRepository, ObjectMapper mapper) {
        this.rewardRepository = rewardRepository;
        this.mapper = mapper;
    }


    @KafkaListener(topics = "txn-initiated",groupId = "reward-group")
    private void consumerTransaction(Transaction transaction){
        try{
            if(rewardRepository.existsByTransactionId(transaction.getId())){
                System.out.printf("Reward already exists for transaction: "+ transaction.getId());
                return;
            }
            Reward reward = new Reward();
            reward.setUserId(transaction.getId());
            reward.setPoints(transaction.getAmount()*100);
            reward.setSentAt(LocalDateTime.now());
            reward.setTransactionId(transaction.getId());

            rewardRepository.save(reward);

            System.out.println("Reward saved: "+reward);
        }
        catch (Exception e){
            System.err.println("Failed to process transaction "+transaction.getId() +": "+e.getMessage());
            throw e;
        }
    }
}
