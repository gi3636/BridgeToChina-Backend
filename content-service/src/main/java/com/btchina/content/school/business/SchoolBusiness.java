package com.btchina.content.school.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.content.school.model.School;
import com.btchina.content.school.model.qo.SchoolListQO;
import com.btchina.content.school.model.vo.SchoolVO;
import com.btchina.content.school.service.SchoolService;
import com.btchina.core.util.dozer.DozerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SchoolBusiness {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private DozerUtil dozerUtil;

    public List<SchoolVO> list(SchoolListQO schoolListQO) {
        LambdaQueryWrapper<School> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(schoolListQO.getName())) {
            queryWrapper.like(School::getName, schoolListQO.getName());
        }
        List<School> list = schoolService.list(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            log.info("schoolListQO = {}, list is empty", schoolListQO);
            list = CollectionUtil.newArrayList();
        }
        // 映射数据
        List<SchoolVO> schoolVOList = dozerUtil.copyList(list, SchoolVO.class);
        log.info("schoolVOList = {}", JSON.toJSONString(schoolVOList));
        return schoolVOList;
    }
}
