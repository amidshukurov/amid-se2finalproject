package az.phonebook.service.impl;

import az.phonebook.dto.UserOperation;
import az.phonebook.entity.UserEntity;
import az.phonebook.repository.UserRepository;
import az.phonebook.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserOperation postUser(UserEntity userEntity) {

        try {
            UserEntity user = userRepository.save(userEntity);
            return UserOperation.builder().user_id(user.getUser_id()).operation_type("add")
                    .operation_status("SUCCESS").build();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return UserOperation.builder().user_id(userEntity.getUser_id()).operation_type("add")
                    .operation_status("FAILURE-" + e.getMessage()).build();
        }
    }

    @Override
    public UserOperation editUser(UserEntity userEntity) {
        if (userEntity.getUser_id() == null) {
            return UserOperation.builder().operation_status("FAILURE").operation_type("edit")
                    .user_id(userEntity.getUser_id()).build();
        }
        try {
            Optional<UserEntity> user = userRepository.findById(userEntity.getUser_id());
            if (user.isPresent()) {
                userRepository.save(userEntity);
                return UserOperation.builder().operation_status("SUCCESS").operation_type("edit")
                        .user_id(userEntity.getUser_id()).build();
            } else {
                return UserOperation.builder().operation_status("FAILURE-USER NOT FOUND").operation_type("edit")
                        .user_id(userEntity.getUser_id()).build();

            }
        } catch (Exception ex) {
            return UserOperation.builder().operation_status("FAILURE-" + ex.getMessage()).operation_type("edit")
                    .user_id(userEntity.getUser_id()).build();
        }
    }

    @Override
    public UserOperation deleteUser(Long userId) {
        try {
            if (userId != null) {
                Optional<UserEntity> user = userRepository.findById(userId);
                if (user.isPresent()) {
                    userRepository.delete(new UserEntity(userId, null, null));
                    return UserOperation.builder().operation_status("SUCCESS").operation_type("delete")
                            .user_id(userId).build();
                } else {
                    return UserOperation.builder().operation_status("FAILURE-USER NOT FOUND").operation_type("delete")
                            .user_id(userId).build();
                }

            } else {
                return UserOperation.builder().operation_status("FAILURE-USER_ID IS NULL").operation_type("delete")
                        .user_id(userId).build();
            }

        } catch (Exception e) {
            return UserOperation.builder().operation_status("FAILURE-" + e.getMessage()).operation_type("delete")
                    .user_id(userId).build();
        }
    }

}
