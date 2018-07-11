package com.scathon.ssm.mapper;

import com.scathon.ssm.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by linhd on 2018/7/11.
 */
@Mapper
public interface RoleMapper {
    List<Role> findByMulIds(@Param("ids") List<Long> ids);

    Role findByRoleName(@Param("roleName") String roleName);
}
