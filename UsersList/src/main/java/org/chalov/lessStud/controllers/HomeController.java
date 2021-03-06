package org.chalov.lessStud.controllers;

import org.chalov.lessStud.DAO.UserDAO;
import org.chalov.lessStud.models.User;
import org.chalov.lessStud.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
public class HomeController {
    @Qualifier("hibernateUserDAO")
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = "/")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/users")
    public String getUsers(Model model) throws SQLException {
        model.addAttribute( "users",userDAO.getAll());
        return "users";
    }
    @GetMapping(value = "/addUsers")
    public String getSignUp(Model model)
    {
        model.addAttribute( "user", new User() );
        return "sign_up";
    }
    @PostMapping(value = "/addUsers")
    public String getSignUp(@ModelAttribute @Valid User user, BindingResult result) throws SQLException {
        userValidator.validate(user,result);
        if (result.hasErrors()){
            return "sign_up";
        }
        userDAO.add(user);
        return "redirect:/users";
    }
}
