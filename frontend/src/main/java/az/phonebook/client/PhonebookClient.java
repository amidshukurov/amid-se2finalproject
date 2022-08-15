package az.phonebook.client;

import az.phonebook.dto.UserEntity;
import az.phonebook.dto.UserOperation;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "phonebook-api", url = "${endpoints.phonebook-api}")
public interface PhonebookClient {

    @GetMapping(value = "/status",
        produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserEntity> getStatus( @RequestHeader(value = "Host") String hostName);

    @GetMapping(value = "/user/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserEntity> getAllUsers( @RequestHeader(value = "Host") String hostName);

    @PostMapping(value = "/user/add",
            produces = MediaType.APPLICATION_JSON_VALUE)
    UserOperation postUser(@RequestBody UserEntity userEntity,  @RequestHeader(value = "Host") String hostName );

    @PutMapping(value = "/user/edit",
            produces = MediaType.APPLICATION_JSON_VALUE)
    UserOperation editUser(@RequestBody UserEntity userEntity, @RequestHeader(value = "Host") String hostName);

    @DeleteMapping(value = "/user/delete",
            produces = MediaType.APPLICATION_JSON_VALUE)
    UserOperation deleteUser(@RequestParam Long userId, @RequestHeader(value = "Host") String hostName);

}
