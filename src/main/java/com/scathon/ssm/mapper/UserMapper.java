package com.scathon.ssm.mapper;

import com.scathon.ssm.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by linhd on 2018/7/11.
 */
@Repository
@Mapper
public interface UserMapper {
    int delete(@Param("username") String username);

    int updateStatus(@Param("status") int status, @Param("username") String username);

    int insertOne(Users users);

    List<Users> findAll();

    Users findByUsername(@Param("username") String username);
}
