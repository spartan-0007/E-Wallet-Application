package org.transactionservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.transactionservice.client.UserServiceClient;
import org.transactionservice.dto.InitiateTransactionRequest;
import org.transactionservice.enums.TransactionStatus;
import org.transactionservice.model.Transaction;
import org.transactionservice.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.transactionservice.constants.KafkaContants.TRANSACTION_INITIATED_TOPIC;
import static org.transactionservice.constants.TransactionInitiatedConstants.*;


@Service
public class TransactionService implements UserDetailsService {

    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public UserDetails loadUserByUsername(String phoneNo) throws UsernameNotFoundException {


        String auth="transaction-service:transaction-service";
        byte[] encodedAuth= Base64.getEncoder().encode(auth.getBytes());

String authValue = "Basic " + new String( encodedAuth);
ObjectNode node=userServiceClient.getUser(phoneNo,authValue);

if(node==null){
    throw new UsernameNotFoundException("User does not exist");
}



        ArrayNode authorities=(ArrayNode) node.get("authorities");

        final List<GrantedAuthority> authorityList=new ArrayList<>();

        authorities.iterator().forEachRemaining(jsonNode -> {authorityList.add(new SimpleGrantedAuthority(jsonNode.get("authority").textValue()));}
        );



        User user=new User(node.get("phoneNo").textValue(),node.get("password").textValue(),authorityList);

        return user;

    }


public String initiateTransaction(String senderPhoneNo, InitiateTransactionRequest request){


        Transaction transaction=Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .senderPhoneNo(senderPhoneNo)
                .receiverPhoneNo(request.getReceiverPhoneNo())
                .amount(request.getAmount())
                .purpose(request.getMessage())
                .status(TransactionStatus.INITIATED).build();

        transactionRepository.save(transaction);



    ObjectNode objectNode=objectMapper.createObjectNode();
    // objectNode.put(EMAIL,user.getEmail());
    objectNode.put(SENDERPHONENO,transaction.getSenderPhoneNo());
    objectNode.put(RECEIVERPHONENO,transaction.getReceiverPhoneNo());
    objectNode.put(AMOUNT,transaction.getAmount());
    objectNode.put(TRANSACTIONID,transaction.getTransactionId());

String kafkaMessage=objectNode.toString();
kafkaTemplate.send(TRANSACTION_INITIATED_TOPIC,kafkaMessage);

//    kafkaTemplate.send(USER_CREATION_TOPIC,objectNode.toString());

        return transaction.getTransactionId();

}

}









