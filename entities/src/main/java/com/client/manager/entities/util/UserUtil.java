package com.client.manager.entities.util;

import com.client.manager.entities.User;
import com.client.manager.entities.dto.UserDTO;

public class UserUtil {
    private UserUtil() {
    }

    public static UserDTO buildDTOFrom(User user) {
        return new UserDTO(
                user.getId(),
                user.getStatus(),
                user.getCreatedDate(),
                user.getUpdatedDate(),
                user.getRoles(),
                user.getUsername(),
                user.getPassword(),
                CompanyUtil.buildLightDTOFrom(user.getCompany())
        );
    }

    public static UserDTO buildLightDTOFrom(User user) {
        return new UserDTO(
                user.getId(),
                user.getStatus(),
                user.getCreatedDate(),
                user.getUpdatedDate(),
                user.getRoles(),
                user.getUsername(),
                user.getPassword(),
                null
        );
    }

    public static User buildLightEntityFrom(UserDTO user) {
        return new User(
                user.getId(),
                user.getStatus(),
                user.getCreatedDate(),
                user.getUpdatedDate(),
                user.getRoles(),
                user.getUsername(),
                user.getPassword(),
                null
        );
    }
}
