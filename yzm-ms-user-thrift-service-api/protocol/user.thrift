namespace java com.yzmedu.protocol.user

struct UserInfo{
1:i32 Id;
2:string username;
3:string password
}

service UserService{
/**
* 根据id获取用户对象
**/
     UserInfo getUserById(1:i32 id);
     /**
     * 根据用户名称获取用户对象
**/
     UserInfo getUserByName(1:string username);
     /**
     * 注册新用户
**/
     void registerUser(1:UserInfo userInfo);
}