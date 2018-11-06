package com.apap.tutorial6.controller;

import com.apap.tutorial6.model.PasswordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;

	@RequestMapping(value = "/addUser", method = RequestMethod.POST) 
	public String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}

	@RequestMapping(value = "/password")
    public String updatePasswordGet(){
	    return "updatePassword";
    }

    @RequestMapping(value = "/password" , method = RequestMethod.POST)
    public String updatePasswordSubmit(@ModelAttribute PasswordModel password, Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserRoleModel user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String msg = "";
        if(password.getPasswordKonfirm().equals(password.getPasswordBaru())) {

            if(passwordEncoder.matches(password.getPasswordLama(), user.getPassword())) {
                userService.ubahPassword(user, password.getPasswordBaru());
                msg = "Sukses";
            }else {
                msg = "NotMatch";
            }

        }else {
            msg = "Failed";
        }
        model.addAttribute("message", msg);
        return "updatePassword";
    }
}