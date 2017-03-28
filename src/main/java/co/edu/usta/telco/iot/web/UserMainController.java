package co.edu.usta.telco.iot.web;

import co.edu.usta.telco.iot.config.MailSenderImpl;
import co.edu.usta.telco.iot.data.model.User;
import co.edu.usta.telco.iot.data.repository.UserRepository;
import co.edu.usta.telco.iot.exception.BusinessException;
import co.edu.usta.telco.iot.service.UserService;
import co.edu.usta.telco.iot.util.IdentifierUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Validated
@Controller
public class UserMainController {
    private static final Logger LOG = LoggerFactory.getLogger(UserMainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderImpl mailSenderImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        if (!Objects.isNull(userRepository.findByLogin(email))) {
            model.addAttribute("errorMessage", "Error: the user already exists");
            return "user/register";
        }
        return doPerformRegister(model, email, password);
    }

    private String doPerformRegister(Model model, @RequestParam String email, @RequestParam String password) {
        try {
            User user = new User();
            user.setLogin(email);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            mailSenderImpl.send(email,
                    "IoT repository - Account activation pending",
                    "Your account is in process of verification");
            return "redirect:/solutions";
        } catch (BusinessException exception) {
            LOG.error(exception.getMessage(), exception);
            model.addAttribute("errorMessage", "Error: creating the user");
            return "user/register";
        }
    }

    @RequestMapping(value = "/admin/approve", method = RequestMethod.GET)
    String approveList(Model model) {
        List<User> usersToApprove = userRepository.findByTokenIsNull();
        model.addAttribute("users", usersToApprove);
        return "user/approveUsers";
    }

    @RequestMapping(value = "/admin/saveApprove", method = RequestMethod.POST)
    String approveUser(Model model, @NotEmpty @RequestParam String id) {
        User user = userRepository.findOne(id);
        if (Objects.isNull(user)) {
            model.addAttribute("errorMessage", "Error: problem approving the user");
            LOG.error("error: user not found in database " + id);
            return approveList(model);
        }

        if (StringUtils.isNotBlank(user.getToken())) {
            model.addAttribute("errorMessage", "Error: problem approving the user");
            LOG.error("error: user has already a token assigned " + id);
            return approveList(model);
        }

        try {
            user.setToken(IdentifierUtil.nextSessionId());
            userRepository.save(user);
            mailSenderImpl.send(user.getLogin(),
                    "IoT repository - Your account was verified",
                    "Your account was verified. Your token is: " + user.getToken()
            );
        } catch (BusinessException e) {
            LOG.error(e.getMessage(), e);
            model.addAttribute("errorMessage", "Error: problem approving the user, please reload the page to validate " +
                    "if the approval is still pending");
            return approveList(model);

        }
        return approveList(model);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String prepareLogin() {
        return "/user/login";
    }


}