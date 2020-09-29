package com.yzmedu.ms.yzmmsscorethriftservice.service;

import com.yzmedu.ms.yzmmsscorethriftservice.domain.Score;
import com.yzmedu.ms.yzmmsscorethriftservice.mapper.ScoreMapper;
import com.yzmedu.protocol.score.ScoreInfo;
import com.yzmedu.protocol.score.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScoreServiceImpl implements ScoreService.Iface {
    @Autowired(required = false)
    private ScoreMapper scoreMapper;
    /**
     * *
     * 根据用户id查询当前用户得分情况.返回分数,如果为空说明不存在,则默认为0分.
     *
     * @param userId
     */
    @Override
    public ScoreInfo getScoreByUser(String userId) throws TException {
        ScoreInfo scoreInfo = new ScoreInfo();
        try {
            Score score = scoreMapper.getScoreByUserId(userId);
            BeanUtils.copyProperties(score, scoreInfo);
        }catch(Exception ex){
            //ignore ex
        }
        return scoreInfo;
    }

    /**
     * *
     * * 根据userid更新得分
     * *如果当前用户不存在,则插入该用户
     *如果存在,首先查询出当前用户的score,然后根据传的参数socre相加后再重新该用户的得分.
     * @param userId
     * @param score
     */
    @Override
    public int updateScore(String userId, int score) throws TException {
        int update = 0;
        ScoreInfo scoreInfo = getScoreByUser(userId);
        int score_ = scoreInfo.getScored();
        if (0==score_) {
            Score _score = new Score();
            _score.setUserId(userId);
            _score.setScored(score);
            update = scoreMapper.insert(_score);
        } else {
            int totalScore = score_ + score;
            Score _score = new Score();
            _score.setUserId(userId);
            _score.setScored(totalScore);
            update = scoreMapper.updateScoreByUserId(_score);
        }
        log.info("update " + update + " record(s) score service");
        return update;
    }
}
