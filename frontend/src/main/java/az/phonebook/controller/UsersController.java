package az.phonebook.controller;

import az.phonebook.client.PhonebookClient;
import az.phonebook.dto.UserEntity;
import az.phonebook.dto.UserOperation;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {

	private PhonebookClient phonebookClient;

	public UsersController(PhonebookClient phonebookClient) {
		this.phonebookClient = phonebookClient;
	}


	@GetMapping("/")
	public String listUsers(Model model) {
		List<UserEntity> users = new ArrayList<>();
		try {
			users = phonebookClient.getAllUsers("phonebook.az");
			model.addAttribute("hostname",Inet4Address.getLocalHost().getHostName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("users", users);
		model.addAttribute("userEntity",new UserEntity());
		return "userForm";
	}

	@PostMapping("/user/add")
	public String addUser(@ModelAttribute UserEntity userEntity, Model model) {
		UserOperation userOperation = phonebookClient.postUser(userEntity,"phonebook.az");
		try{
			model.addAttribute("hostname",Inet4Address.getLocalHost().getHostName());
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		model.addAttribute("operation", userOperation);
		return "submitMessage";
	}

	@PostMapping("/user/edit")
	public String editUser(@ModelAttribute UserEntity userEntity, Model model) {
		UserOperation userOperation = phonebookClient.editUser(userEntity,"phonebook.az");
		try{
			model.addAttribute("hostname",Inet4Address.getLocalHost().getHostName());
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		model.addAttribute("operation", userOperation);
		return "submitMessage";
	}

	@PostMapping("/user/delete")
	public String deleteUser(@RequestParam Long userId, Model model) {
		UserOperation userOperation = phonebookClient.deleteUser(userId,"phonebook.az");
		try{
			model.addAttribute("hostname",Inet4Address.getLocalHost().getHostName());
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		model.addAttribute("operation", userOperation);
		return "submitMessage";
	}

}
