package E_wallet.example.demo.controller;

import E_wallet.example.demo.dto.CreateUserRequest;
import E_wallet.example.demo.model.User;
import E_wallet.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/User")
    public User createUser(@RequestBody @Valid CreateUserRequest userRequest){
        return userService.createUser(userRequest);

    }
}
