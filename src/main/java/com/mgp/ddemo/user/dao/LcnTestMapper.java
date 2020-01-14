package com.mgp.ddemo.user.dao;

import com.mgp.ddemo.user.bean.LcnTest;
import com.mgp.ddemo.user.bean.LcnTestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LcnTestMapper {
    long countByExample(LcnTestExample example);

    int deleteByExample(LcnTestExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LcnTest record);

    int insertSelective(LcnTest record);

    List<LcnTest> selectByExample(LcnTestExample example);

    LcnTest selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LcnTest record, @Param("example") LcnTestExample example);

    int updateByExample(@Param("record") LcnTest record, @Param("example") LcnTestExample example);

    int updateByPrimaryKeySelective(LcnTest record);

    int updateByPrimaryKey(LcnTest record);
}