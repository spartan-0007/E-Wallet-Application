package E_wallet.example.demo;

import E_wallet.example.demo.enums.UserStatus;
import E_wallet.example.demo.enums.UserType;
import E_wallet.example.demo.model.User;
import E_wallet.example.demo.repository.UserRepository;
import E_wallet.example.demo.configuration.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EWalletApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(EWalletApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
        User transactionService= User.builder()
                .phoneNo("transaction-service")
                .password(passwordEncoder.encode("transaction-service"))
                .userStatus(UserStatus.ACTIVE)
                .userType(UserType.ADMIN)
                .authorities("SERVICE").build();


        if(userRepository.findByPhoneNo("transaction-service")==null){

            userRepository.save(transactionService);
        }
    }}