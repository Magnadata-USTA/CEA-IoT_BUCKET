package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.config.MailSenderImpl;
import co.edu.usta.telco.iot.data.model.User;
import co.edu.usta.telco.iot.data.repository.UserRepository;
import co.edu.usta.telco.iot.exception.BusinessException;
import co.edu.usta.telco.iot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class UserMainController {
    private static final Logger LOG = LoggerFactory.getLogger(UserMainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository; //TODO: remove user repository

    @Autowired
    private MailSenderImpl mailSenderImpl;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    String prepareRegister(Model model) {
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    String performRegister(Model model, @RequestParam String email, @RequestParam String password) {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            model.addAttribute("errorMessage", "Error: the email and password are required");
            return "user/register";
        }
        if (! Objects.isNull(userRepository.findByLogin(email))){
            model.addAttribute("errorMessage", "Error: the user already exists");
            return "user/register";
        }
        return doPerformRegister(model, email, password);
    }

    private String doPerformRegister(Model model, @RequestParam String email, @RequestParam String password) {
        try {
            User user = new User();
            user.setLogin(email);
            user.setPassword(password);
            userRepository.save(user);
            mailSenderImpl.send(email);
            return "redirect:/solutions";
        } catch (BusinessException exception) {
            LOG.error(exception.getMessage(), exception);
            model.addAttribute("errorMessage", "Error: creating the user");
            return "user/register";
        }
    }

    @RequestMapping(value = "/register/approve", method = RequestMethod.GET)
    String approveList(Model model) {
        List<User> usersToApprove = userRepository.findByTokenIsNull();
        model.addAttribute("users", usersToApprove);
        return "user/approveUsers";
    }

    @RequestMapping(value = "/register/saveApprove", method = RequestMethod.GET) // TODO: use POST ***
    String approveUser(Model model, @RequestParam String id) {
        if (! Objects.isNull(userRepository.findOne(id))){
            model.addAttribute("errorMessage", "Error: the user already exists");
            return "user/register";
        }
        return "user/approveUsers";
    }

}
