package E_wallet.example.demo.service;

import E_wallet.example.demo.dto.CreateUserRequest;
import E_wallet.example.demo.enums.UserType;
import E_wallet.example.demo.mapper.UserMapper;
import E_wallet.example.demo.model.User;
import E_wallet.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static E_wallet.example.demo.constants.KafkaConstants.USER_CREATION_TOPIC;
import static E_wallet.example.demo.constants.UserCreationTopicConstanss.*;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.EMAIL;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

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

        ObjectNode objectNode=objectMapper.createObjectNode();
       // objectNode.put(EMAIL,user.getEmail());
        objectNode.put(NAME,user.getName());
        objectNode.put(PHONENO,user.getPhoneNo());
        objectNode.put(USERID,user.getId());



        kafkaTemplate.send(USER_CREATION_TOPIC,objectNode.toString());


        return user;
    }
}
