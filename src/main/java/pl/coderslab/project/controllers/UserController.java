package pl.coderslab.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.project.dao.UserDao;
import pl.coderslab.project.entities.Exercise;
import pl.coderslab.project.entities.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }


    // Adding
    @GetMapping("/add")
    public String addUsere(Model model) {
        model.addAttribute("user", new User());
        return "views/user/add";
    }
    @PostMapping("/add")
    public String processAddingUser(@Valid  User user, BindingResult result) {
        if(result.hasErrors()){
            return "views/user/add";
        }
        userDao.create(user);
        return "redirect:/user/users";
    }
    // Editing
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        model.addAttribute("user", userDao.read(id));
        return "user/edit";
    }
    @PostMapping("/edit/{id}")
    public String processEditingUser(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "user/edit";
        }
        userDao.update(user);
        return "redirect:/user/users";
    }
    // Removing
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userDao.delete(userDao.read(id));
        return "redirect:/user/users";
    }
    // Displaying list of users
    @GetMapping("/users")
    public String listOfUsers(Model model) {
        model.addAttribute("users", userDao.finaAll());
        return "views/user/list";
    }
    // Displaying list of players
    @GetMapping("/players")
    public String listOfPlayers(Model model) {
        List<User> users = userDao.finaAll();
        List<User> players = new ArrayList<>();
        for(User u : users) {
            if(u.getTrainer() == 0) {
                players.add(u);
            }
        }
        model.addAttribute("players", players);
        return "views/user/list";
    }

}
