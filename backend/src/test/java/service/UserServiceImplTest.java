package service;

import az.phonebook.dto.UserOperation;
import az.phonebook.entity.UserEntity;
import az.phonebook.exceptions.FailedToGetSuccessfulResponseException;
import az.phonebook.repository.UserRepository;
import az.phonebook.service.impl.UserServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        doReturn(new UserEntity()).when(userRepository).save(any());
        doNothing().when(userRepository).delete(any());

    }

    @Test
    void testPostWhenUserCanNotBeSaved() {
        when(userRepository.save(any())).thenThrow(FailedToGetSuccessfulResponseException.class);
        UserOperation userOperation = userService.postUser(UserEntity.builder()
                .name("Amid Shukurov").user_id(1l).build());
        Assertions.assertTrue(userOperation.getOperation_status().contains("FAILURE"));
    }

    @Test
    void testPostHappyPath() {
        UserOperation userOperation = userService.postUser(UserEntity.builder()
                .name("Amid Shukurov").user_id(1l).build());
        Assertions.assertTrue(userOperation.getOperation_status().contains("SUCCESS"));
    }

    @Test
    void testEditWhenUserIdIsNull() {
        UserOperation userOperation = userService.editUser(UserEntity.builder()
                .name("Amid").build());
        Assertions.assertEquals("FAILURE", userOperation.getOperation_status());
    }

    @Test
    void testEditWhenUserNotFoundInDb() {
        doReturn(Optional.empty()).when(userRepository).findById(any());
        UserOperation userOperation = userService.editUser(UserEntity.builder()
                .name("Amid Shukurov").user_id(1l).build());
        Assertions.assertEquals("FAILURE-USER NOT FOUND", userOperation.getOperation_status());
    }

    @Test
    void testEditWhenUserCanNotBeEdited() {
        doReturn(Optional.of(UserEntity.builder().user_id(1l).build())).when(userRepository).findById(any());
        when(userRepository.save(any())).thenThrow(new FailedToGetSuccessfulResponseException("EDITING FAILED"));
        UserOperation userOperation = userService.editUser(UserEntity.builder()
                .name("Amid Shukurov").user_id(1l).build());
        verify(userRepository, times(1)).findById(any());
        Assertions.assertEquals("FAILURE-EDITING FAILED", userOperation.getOperation_status());
    }

    @Test
    void testEditHappyPath() {
        doReturn(Optional.of(UserEntity.builder().user_id(1l).build())).when(userRepository).findById(any());
        UserOperation userOperation = userService.editUser(UserEntity.builder()
                .name("Amid Shukurov").user_id(1l).build());
        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any());
        Assertions.assertEquals("SUCCESS", userOperation.getOperation_status());
    }

    @Test
    void testDeleteWhenUserIdIsNull() {
        doReturn(Optional.of(UserEntity.builder().user_id(1l).build())).when(userRepository).findById(any());
        UserOperation userOperation = userService.deleteUser(null);
        Assertions.assertEquals("FAILURE-USER_ID IS NULL", userOperation.getOperation_status());
    }

    @Test
    void testDeleteWhenDbThrowException() {
        doReturn(Optional.of(UserEntity.builder().user_id(1l).build())).when(userRepository).findById(any());
        when(userRepository.findById(any())).thenThrow(new FailedToGetSuccessfulResponseException("DELETING FAILED"));
        verify(userRepository, times(0)).delete(any());
        UserOperation userOperation = userService.editUser(UserEntity.builder()
                .name("Amid Shukurov").user_id(1l).build());
        Assertions.assertEquals("FAILURE-DELETING FAILED", userOperation.getOperation_status());
    }

    @Test
    void testDeleteHappyPath() {
        doReturn(Optional.of(UserEntity.builder().user_id(1l).build())).when(userRepository).findById(any());
        UserOperation userOperation = userService.deleteUser(1l);
        verify(userRepository, times(1)).delete(any());
        Assertions.assertEquals("SUCCESS", userOperation.getOperation_status());
    }


}
