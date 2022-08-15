package az.phonebook.service;

import az.phonebook.dto.UserOperation;
import az.phonebook.entity.UserEntity;
import java.util.List;

public interface UserService {

    List<UserEntity> getAllUsers();

    UserOperation postUser(UserEntity userEntity);

    UserOperation editUser(UserEntity userEntity);

    UserOperation deleteUser(Long userId);
}
