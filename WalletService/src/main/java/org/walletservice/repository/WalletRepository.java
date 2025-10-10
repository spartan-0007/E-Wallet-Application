package org.walletservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.walletservice.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Integer> {
}
