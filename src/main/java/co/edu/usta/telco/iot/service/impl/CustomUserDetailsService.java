package co.edu.usta.telco.iot.service.impl;

import co.edu.usta.telco.iot.data.model.User;
import co.edu.usta.telco.iot.data.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("error: the username is empty");
        }

        User user = userRepository.findByLogin(username);

        if (Objects.isNull(user) || StringUtils.isEmpty(user.getId())) {
            throw new UsernameNotFoundException("error: the was not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                    user.getPassword(),
                    user.isAdmin()? buildUserAuthority(ROLE_ADMIN): buildUserAuthority(ROLE_USER));
    }

    private List<GrantedAuthority> buildUserAuthority(String...roles) {
        return Arrays.stream(roles).map(
                (role)-> new SimpleGrantedAuthority(role)
        ).collect(Collectors.toList());
    }

}
