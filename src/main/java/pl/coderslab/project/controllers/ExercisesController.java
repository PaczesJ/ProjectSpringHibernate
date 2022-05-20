package pl.coderslab.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.project.dao.ExercisesDao;
import pl.coderslab.project.dao.UserDao;
import pl.coderslab.project.entities.Exercise;
import pl.coderslab.project.entities.User;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/exercise")
public class ExercisesController {

    private ExercisesDao exercisesDao;
    private UserDao userDao;

    public ExercisesController(ExercisesDao exercisesDao, UserDao userDao) {
        this.exercisesDao = exercisesDao;
        this.userDao = userDao;
    }

    // Display exercises
    @GetMapping("/exercises")
    public String displayExercises(Model model) {
        model.addAttribute("exercises", exercisesDao.findAllExercises());
        return "views/exercise/list";
    }

    // Add exercise
    @GetMapping("/add")
    public String addExercise(Model model) {
        model.addAttribute("exercise", new Exercise());
        return "views/exercise/add";
    }

    @PostMapping("/add")
    public String processAddedExercise(@Valid Exercise exercise, BindingResult result, HttpSession ses) {
        if (result.hasErrors()) {
            return "views/exercise/add";
        } else {
            User user = (User) ses.getAttribute("user");
            exercise.setUser(user);
            exercisesDao.createExercise(exercise);
            return "redirect:/exercise/exercises";
        }
    }
    // Edit exercise
    @GetMapping("/edit/{id}")
    public String editExercise(@PathVariable int id, Model model) {
        model.addAttribute("exercise", exercisesDao.readExercise(id));
        return "views/exercise/edit";
    }
    @PostMapping("/edit/{id}")
    public String processEditedExercise(@Valid Exercise exercise, BindingResult result) {
        if(result.hasErrors()) {
            return "redirect:/exercise/exercises";
        }
        exercisesDao.updateExercise(exercise);
        return "redirect:/exercise/exercises";
    }
    // Deleting Exercise
    @GetMapping("/delete/{id}")
    public String removeExercise(@PathVariable int id) {
        exercisesDao.deleteExercise(exercisesDao.readExercise(id));
        return "redirect:/exercise/exercises";
    }
}
