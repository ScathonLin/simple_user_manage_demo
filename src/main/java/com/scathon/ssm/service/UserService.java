package com.scathon.ssm.service;

import com.scathon.ssm.mapper.RoleMapper;
import com.scathon.ssm.mapper.UserMapper;
import com.scathon.ssm.mapper.UserRoleMapper;
import com.scathon.ssm.pojo.Role;
import com.scathon.ssm.pojo.Users;
import com.scathon.ssm.pojo.UsersRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by linhd on 2018/7/11.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    public boolean deleteUser(String username) {
        userMapper.delete(username);
        userRoleMapper.deleteByUsername(username);
        return true;
    }

    public boolean updateStatus(String username) {
        return userMapper.updateStatus(1, username) == 1;
    }

    public boolean insertOne(Users users) {
        users.setStatus(0);
        int i1 = userMapper.insertOne(users);
        UsersRole usersRole = new UsersRole();
        usersRole.setUsername(users.getUsername());
        Role role = roleMapper.findByRoleName("ROLE_USER");
        usersRole.setRoleId(role.getId());
        int i2 = userRoleMapper.insertOne(usersRole);
        return i1 == 1 && i2 == 1;
    }

    public List<Users> findAll() {
        return userMapper.findAll();
    }

    public Users findByUsername(String username) {
        Users users = userMapper.findByUsername(username);
        if (users != null) {
            List<UsersRole> userRoles = userRoleMapper.findByUsername(username);
            List<Long> roleIdList = userRoles.stream().map(UsersRole::getRoleId).collect(Collectors.toList());
            List<Role> roleList = roleMapper.findByMulIds(roleIdList);
            users.setUserRoleList(roleList);
        }
        return users;
    }
}
