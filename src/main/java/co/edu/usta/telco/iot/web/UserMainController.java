package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.config.MailSenderImpl;
import co.edu.usta.telco.iot.data.model.User;
import co.edu.usta.telco.iot.data.repository.UserRepository;
import co.edu.usta.telco.iot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/register")
public class UserMainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository; //TODO: remove user repository

    @Autowired
    private MailSenderImpl mailSenderImpl;

    @RequestMapping(method = RequestMethod.GET)
    String prepareRegister(Model model) {
        return "user/register";
    }

    @RequestMapping(method = RequestMethod.POST)
    String performRegister(Model model, @RequestParam String email, @RequestParam String password) {
        //TODO: validate fields, non repeated eMail too ...
        if (! Objects.isNull(userRepository.findByLogin(email))){
            model.addAttribute("errorMessage", "Error: the user already exists");
            return "user/register";
        }

        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        mailSenderImpl.send(email);
        userRepository.save(user);
        return "redirect:/solutions";
    }

}
