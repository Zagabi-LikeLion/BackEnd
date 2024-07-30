//package org.likelion.zagabi.Domain.Account.Jwt.UserDetails;
//
//import lombok.Getter;
//import org.likelion.zagabi.Domain.Account.Entity.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//@Getter
//public class CustomUserDetails implements UserDetails {
//
//    private final String username;
//    private final String password;
//    private final Boolean isAdmin;
//
//    public CustomUserDetails(User user) {
//        username = user.getEmail();
//        password = user.getPassword();
//        isAdmin = user.getIsAdmin();
//    }
//
//    public CustomUserDetails(String email, String password, Boolean isAdmin) {
//        this.username = email;
//        this.password = password;
//        this.isAdmin = isAdmin;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (isAdmin) {
//            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        } else {
//            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//        }
//    }
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
package org.likelion.zagabi.Domain.Account.Jwt.UserDetails;

import lombok.Getter;
import org.likelion.zagabi.Domain.Account.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Boolean isAdmin;

    public CustomUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.isAdmin = user.getIsAdmin() != null ? user.getIsAdmin() : false; // null 체크
    }

    public CustomUserDetails(String email, String password, Boolean isAdmin) {
        this.username = email;
        this.password = password;
        this.isAdmin = isAdmin != null ? isAdmin : false; // null 체크
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isAdmin) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
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

