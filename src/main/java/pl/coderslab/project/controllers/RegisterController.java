package pl.coderslab.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.project.dao.UserDao;
import pl.coderslab.project.entities.User;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserDao userDao;

    public RegisterController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "views/register";
    }

    @PostMapping("/register")
    public String processRegisteredUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/register";
        } else {
            userDao.create(user);
            return "redirect:/login";
        }
    }
}
