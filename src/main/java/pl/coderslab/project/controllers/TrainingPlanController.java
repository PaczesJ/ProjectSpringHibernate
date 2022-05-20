package pl.coderslab.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.project.dao.TrainingPlanDao;
import pl.coderslab.project.entities.Exercise;
import pl.coderslab.project.entities.TrainingPlan;
import pl.coderslab.project.entities.User;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/trainingPlan")
public class TrainingPlanController {

    private TrainingPlanDao trainingPlanDao;

    public TrainingPlanController(TrainingPlanDao trainingPlanDao) {
        this.trainingPlanDao = trainingPlanDao;
    }

    // Displaying trainingPlans
    @GetMapping("/trainingPlans")
    public String displayTrainingPlans(Model model) {
        model.addAttribute("trainingPlans", trainingPlanDao.findAllTrainingPlans());
        return "views/trainingPlan/list";
    }
    // Details of trainingPlan

    // Adding a trainingPlan
    @GetMapping("/add")
    public String addingTrainingPlan(Model model) {
        model.addAttribute("trainingPlan", new TrainingPlan());
        return "views/trainingPlan/add";
    }
    @PostMapping("/add")
    public String processAddedTrainingPlan(@Valid TrainingPlan trainingPlan, BindingResult result, HttpSession ses) {
        if (result.hasErrors()) {
            return "views/trainingPlan/add";
        } else {
            User user = (User) ses.getAttribute("user");
            trainingPlan.setUser(user);
            trainingPlanDao.createTrainingPlan(trainingPlan);
            return "redirect:/trainingPlan/trainingPlans";
        }
    }
    // Editing of trainingPlan
    @GetMapping("/edit/{id}")
    public String editTrainingPlan(@PathVariable int id, Model model) {
        model.addAttribute("trainingPlan", trainingPlanDao.readTrainingPlan(id));
        return "views/trainingPlan/edit";
    }
    @PostMapping("/edit/{id}")
    public String processEditedTrainingPlan(@Valid TrainingPlan trainingPlan, BindingResult result) {
        if(result.hasErrors()) {
            return "redirect:/trainingPlan/trainingPlans";
        }
        trainingPlanDao.updateTrainingPlan(trainingPlan);
        return "redirect:/trainingPlan/trainingPlans";
    }

    // Removal of trainingPlan
    @GetMapping("/delete/{id}")
    public String deleteTrainingPlan(@PathVariable int id) {
        trainingPlanDao.deleteTrainingPlan(trainingPlanDao.readTrainingPlan(id));
        return "redirect:/trainingPlan/trainingPlans";
    }
}
