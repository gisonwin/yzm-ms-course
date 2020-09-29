package com.yzmedu.ms.yzmmsscorethriftservice.mapper;

import com.yzmedu.ms.yzmmsscorethriftservice.com.yzmedu.ms.mapper.ScoreBaseMapper;
import com.yzmedu.ms.yzmmsscorethriftservice.domain.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ScoreMapper extends ScoreBaseMapper<Score> {
    @Select("select id,user_id,scored from score where user_id=#{userId}")
    Score getScoreByUserId(@Param("userId") String userId);
    @Update("update score set scored=#{s.scored} where user_id=#{s.userId}")
    int updateScoreByUserId(@Param("s") Score score);
}
