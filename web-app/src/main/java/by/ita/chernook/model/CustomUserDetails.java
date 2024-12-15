package by.ita.chernook.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Data
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private UUID customerUuid;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, UUID customerUuid, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.customerUuid = customerUuid;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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