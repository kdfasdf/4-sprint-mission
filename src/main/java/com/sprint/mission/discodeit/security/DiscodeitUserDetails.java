package com.sprint.mission.discodeit.security;

import com.sprint.mission.discodeit.dto.user.UserResponse;
import com.sprint.mission.discodeit.util.AuthorityRolesUtils;
import java.util.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class DiscodeitUserDetails implements UserDetails {
    private final UserResponse userResponse;
    private final String password;
    private final AuthorityRolesUtils authorityRolesUtils;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityRolesUtils.createAuthorities(userResponse.getEmail());
    }

    @Override
    public String getUsername() {
        return "";
    }

}
