package com.client.manager.ws.rest;

import com.client.manager.core.exception.UserNotFoundException;
import com.client.manager.core.service.IUserService;
import com.client.manager.entities.dto.UserDTO;
import com.client.manager.entities.util.UserUtil;
import com.client.manager.ws.exception.LoginBadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;
    private final PasswordEncoder encoder;

    public UserController(IUserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping(value = "/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findByUsername(
            @PathVariable String username
    ) {
        return this.userService
                .findByUsername(username)
                .map(UserUtil::buildDTOFrom)
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        UserDTO user = this.userService
                .findByUsername(username)
                .map(UserUtil::buildDTOFrom)
                .orElseThrow(UserNotFoundException::new);

        if (!this.encoder.matches(password, user.getPassword())) {
            throw new LoginBadCredentialsException();
        }

        user.setPassword(null);

        return user;
    }
}
