package com.btchina.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements UserDetails {

    private SysUser sysUser;

    @JsonIgnore//不会序列化到redis
    private List<String> permissions = new ArrayList<>();

    @JsonIgnore//不会序列化到redis
    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    public LoginUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public LoginUser(SysUser user, List<String> permissions) {
        this.permissions = permissions;
        this.sysUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //把permissions转换成GrantedAuthority
        if (authorities != null && authorities.size() > 0) {
            return authorities;
        }
        for (String permission : permissions) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
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
