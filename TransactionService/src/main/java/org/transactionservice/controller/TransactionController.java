package org.transactionservice.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transactionservice.dto.InitiateTransactionRequest;
import org.transactionservice.enums.TransactionStatus;
import org.transactionservice.service.TransactionService;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PostMapping("/transaction")
    public String InitiateTransaction(@RequestBody @Valid InitiateTransactionRequest request){
       UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

  String senderPhoneNo=userDetails.getUsername();
        return transactionService.initiateTransaction(senderPhoneNo,request);
    }
}
