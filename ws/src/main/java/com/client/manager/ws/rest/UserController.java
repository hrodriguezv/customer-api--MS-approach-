package com.client.manager.ws.rest;

import com.client.manager.core.exception.UserNotFoundException;
import com.client.manager.core.service.IUserService;
import com.client.manager.entities.dto.UserDTO;
import com.client.manager.entities.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
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
}
