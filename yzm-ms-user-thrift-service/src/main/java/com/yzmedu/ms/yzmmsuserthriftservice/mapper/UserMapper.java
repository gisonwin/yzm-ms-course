package com.yzmedu.ms.yzmmsuserthriftservice.mapper;

import com.yzmedu.ms.yzmmsuserthriftservice.com.yzmedu.ms.mapper.UserBaseMapper;
import com.yzmedu.ms.yzmmsuserthriftservice.domain.User;
import com.yzmedu.protocol.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends UserBaseMapper<User> {
    /****getuserbyname 通用mapper里没有. 这里自己实现,直接写的select 语句  */
    @Select("select id,username,password from user where username=#{username}")
    UserInfo getUserByName(@Param("username") String username);
}
