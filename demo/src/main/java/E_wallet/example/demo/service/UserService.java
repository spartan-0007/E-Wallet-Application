package E_wallet.example.demo.service;

import E_wallet.example.demo.dto.CreateUserRequest;
import E_wallet.example.demo.enums.UserType;
import E_wallet.example.demo.mapper.UserMapper;
import E_wallet.example.demo.model.User;
import E_wallet.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User loadUserByUsername(String phoneNo) throws UsernameNotFoundException {
        User user= userRepository.findByPhoneNo(phoneNo);

        if(user==null){
            throw new UsernameNotFoundException("User Does Not exist");
        }
        return user;
    }

    public User createUser(CreateUserRequest userRequest) {

        User user= UserMapper.mapToUser(userRequest);
        user.setUserType(UserType.USER);
        user.setAuthorities("USER");

        userRepository.save(user);

        return user;
    }
}
