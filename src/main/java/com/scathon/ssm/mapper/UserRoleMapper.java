package com.scathon.ssm.mapper;

import com.scathon.ssm.pojo.UsersRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by linhd on 2018/7/11.
 */
@Mapper
public interface UserRoleMapper {
    int deleteByUsername(@Param("username") String username);

    int insertOne(UsersRole usersRole);

    List<UsersRole> findByUsername(@Param("username") String username);

}
