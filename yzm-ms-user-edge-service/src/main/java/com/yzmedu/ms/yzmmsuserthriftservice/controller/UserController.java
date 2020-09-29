package com.yzmedu.ms.yzmmsuserthriftservice.controller;

import com.yzmedu.ms.yzmmsuserthriftservice.thrift.ServiceProvider;
import com.yzmedu.protocol.score.ScoreInfo;
import com.yzmedu.protocol.user.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private ServiceProvider serviceProvider;

    @PostMapping("/login")
    public void login(@RequestParam("username") String username,@RequestParam("password") String password){
    //用户登录,成功后调用积分服务增加该用户10个积分.
        //先去调用user service 根据用户名去查询是否存在
        UserInfo userInfo = null;
        try {
            userInfo  = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            log.error(e.toString(),e);
        }

        //如果用户不存在,直接记录错误日志,并退出该程序
        if (null == userInfo) {
            log.error("user" +username+" not exist ");
            return;
        }
        //如果用户存在,但是密码错误,记录错误日志,并退出该程序
        if (!userInfo.getPassword().equals(password)) {
            log.error("user"+username+" password is valid");
            return;
        }
        log.info(username + " login success "+ LocalDateTime.now());
        //调用 积分 微服务 给用户增加10个积分
        int userId = userInfo.getId();
        int update = 0;
        try {
             update = serviceProvider.getScoreService().updateScore(String.valueOf(userId), 10);
        } catch (TException e) {
            e.printStackTrace();
        }

        if (update>0) {
            log.info("update "+ update+"record(s) success");
        }
        //根据用户id查询该用户当前有多少积分
        ScoreInfo scoreInfo = null;
        try {
            scoreInfo  = serviceProvider.getScoreService().getScoreByUser(String.valueOf(userId));
        } catch (TException e) {
            e.printStackTrace();
        }
        if (null != scoreInfo) {
            log.info(userId+" now score is " + scoreInfo.getScored());
        }
    }
}
