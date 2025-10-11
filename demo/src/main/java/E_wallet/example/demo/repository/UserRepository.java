package E_wallet.example.demo.repository;

import E_wallet.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

     User findByPhoneNo(String phoneNo);
}
