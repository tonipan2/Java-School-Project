package com.example.groupproject.service;

import com.example.groupproject.model.User;
import com.example.groupproject.model.UserPrinciple;
import com.example.groupproject.dto.UserDTO;
import com.example.groupproject.MapperUtil;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipleServiceImpl implements UserPrincipleService {
    @Lazy
    private final UserService userService;
    private final MapperUtil mapperUtil;

    public UserPrincipleServiceImpl(UserService userService, MapperUtil mapperUtil) {
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userService.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrinciple(mapperUtil.getModelMapper().map(user, User.class));
    }
}
