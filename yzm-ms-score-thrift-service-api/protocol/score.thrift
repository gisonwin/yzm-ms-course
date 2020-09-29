namespace java com.yzmedu.protocol.score

struct ScoreInfo{
1:i32 Id;
2:string userId;
3:i32 scored
}

service ScoreService{
/***
* 根据用户id查询当前用户得分情况.返回分数,如果为空说明不存在,则默认为0分.
**/
     ScoreInfo getScoreByUser(1:string userId);
 /***
     * 根据userid更新得分
**/
     i32 updateScore(1:string userId,2:i32  score)
}