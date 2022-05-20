package pl.coderslab.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.project.dao.UserDao;
import pl.coderslab.project.entities.User;
import pl.coderslab.project.repositories.UserRepository;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private UserDao userDao;
    private UserRepository userRepository;

    public LoginController(UserDao userDao, UserRepository userRepository) {
        this.userDao = userDao;
        this.userRepository = userRepository;
    }
    // Log in
    @GetMapping("/login")
    public String logIn() {
        return "views/login";
    }
    @PostMapping("/login")
    public String userLogged(@RequestParam String email, @RequestParam String password, HttpSession ses, Model model) {

        if(userRepository.findUserByEmail(email) == null) {
            return "redirect:/login";
        } else {
            User userByEmail = userRepository.findUserByEmail(email);
            if(userByEmail.getPassword().equals(password) && userByEmail.getTrainer() == 0) {
                ses.setAttribute("user", userByEmail);
                model.addAttribute("user", userByEmail);
                return "playerDashboard";
            } else if (userByEmail.getPassword().equals(password) && userByEmail.getTrainer() == 1){
                ses.setAttribute("user", userByEmail);
                model.addAttribute("user", userByEmail);
                return "trainerDashboard";
            } else {
                return "redirect:/login";
            }
        }
    }
    //Logged user navigation
    @GetMapping("/dashboard")
    public String navigate(HttpSession ses, Model model) {
        User user = (User) ses.getAttribute("user");
        model.addAttribute("user", user);
        if(user.getTrainer() == 0) {
            return "playerDashboard";
        } else {
            return "trainerDashboard";
        }
    }
    // Log out
    @GetMapping("/logout")
    public String logOut (HttpSession ses) {
        ses.removeAttribute("user");
        return "index";
    }

}
