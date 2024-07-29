package org.likelion.zagabi.Domain.Account.Jwt.UserDetails;

import lombok.Getter;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;

    public CustomUserDetails(User user) {
        username = user.getEmail();
        password = user.getPassword();
    }

    public CustomUserDetails(String email, String password) {
        this.username = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 빈 컬렉션 반환
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
