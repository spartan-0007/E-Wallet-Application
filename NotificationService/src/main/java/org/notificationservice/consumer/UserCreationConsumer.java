package org.notificationservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static org.notificationservice.constants.KafkaConstants.USER_CREATION_TOPIC;
import static org.notificationservice.constants.UserCreationTopicConstanss.EMAIL;
import static org.notificationservice.constants.UserCreationTopicConstanss.NAME;

@Service
public class UserCreationConsumer {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JavaMailSender javaMailSender;

    @KafkaListener (topics=USER_CREATION_TOPIC, groupId="notification-group")
    public void userCreated(String message) throws JsonProcessingException{


        ObjectNode node=mapper.readValue(message, ObjectNode.class);

        String name=node.get(NAME).textValue();
        String email =node.get(EMAIL).textValue();

        SimpleMailMessage mailMessage=new SimpleMailMessage();


        mailMessage.setFrom("spartan6201139@gmail.com");
mailMessage.setTo("ujjwalraj7023@gmail.com");
mailMessage.setSubject("Welcome");
mailMessage.setText("Welcome welcome");

javaMailSender.send(mailMessage);


    }
}
