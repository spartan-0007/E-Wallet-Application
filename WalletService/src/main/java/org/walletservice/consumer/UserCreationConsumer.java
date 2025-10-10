package org.walletservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.walletservice.model.Wallet;
import org.walletservice.repository.WalletRepository;

import static org.walletservice.constants.KafkaConstants.USER_CREATION_TOPIC;
import static org.walletservice.constants.UserCreationTopicConstans.*;

@Service
public class UserCreationConsumer {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    WalletRepository walletRepository;

//    @Autowired
//    JavaMailSender javaMailSender;

    @Value("${wallet.initial.amount}")
    Double walletAmount;


    @KafkaListener (topics=USER_CREATION_TOPIC, groupId="wallet-group")
    public void userCreated(String message) throws JsonProcessingException{


        ObjectNode node=mapper.readValue(message, ObjectNode.class);

        String phoneNo=node.get(PHONENO).textValue();
        Integer userId =node.get(USERID).intValue();

        SimpleMailMessage mailMessage=new SimpleMailMessage();

          Wallet wallet= Wallet.builder()
                          .phoneNo(phoneNo)
                                  .userId(String.valueOf(userId))
                                          .balance(String.valueOf(walletAmount)).build();


          walletRepository.save(wallet);



    }
}
