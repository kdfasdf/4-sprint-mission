package com.sprint.mission.discodeit.security;

import com.sprint.mission.discodeit.dto.user.UserResponse;
import java.util.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class DiscodeitUserDetails implements UserDetails {
    private final UserResponse userResponse;
    private final String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_".concat(userResponse.getRole().name()));
    }

    @Override
    public String getUsername() {
        return "";
    }

}
