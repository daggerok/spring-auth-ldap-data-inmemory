package daggerok.admin.userdetails;

import daggerok.admin.data.AdminUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

class AdminDetails extends AdminUser implements UserDetails {

    AdminDetails(AdminUser adminUser) {
        setUsername(adminUser.getUsername());
        setPassword(adminUser.getPassword());
        setEnabled(adminUser.isEnabled());
        setRoles(adminUser.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(getRoles().toArray(new String[0]));
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }
}
