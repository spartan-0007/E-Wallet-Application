package org.transactionservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.transactionservice.enums.TransactionStatus;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Transaction {



    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    String senderPhoneNo;

    String receiverPhoneNo;

    String transactionId;

    Double amount;

    String purpose;

    @Enumerated(value=EnumType.STRING)
    TransactionStatus status;

    @CreationTimestamp
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;







}
