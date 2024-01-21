package com.btchina.content.school.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.content.school.model.School;
import com.btchina.content.school.model.qo.SchoolQO;
import com.btchina.content.school.model.qo.SchoolListQO;
import com.btchina.content.school.model.vo.SchoolVO;
import com.btchina.content.school.service.SchoolService;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.core.util.dozer.DozerUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    public List<SchoolVO> list(SchoolQO schoolQO) {
        LambdaQueryWrapper<School> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(schoolQO.getName())) {
            queryWrapper.like(School::getName, schoolQO.getName());
        }

        if (StringUtils.isNotEmpty(schoolQO.getId())) {
            queryWrapper.eq(School::getId, schoolQO.getId());
        }

        List<School> list = schoolService.list(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            log.info("schoolListQO = {}, list is empty", schoolQO);
            list = CollectionUtil.newArrayList();
        }
        // 映射数据
        System.out.println("list1 = " + cn.hutool.json.JSONUtil.toJsonStr(list));
        System.out.println("list2 = " + JSON.toJSONString(list));
        List<SchoolVO> schoolVOList = dozerUtil.copyList(list, SchoolVO.class);
        log.info("schoolVOList = {}", JSON.toJSONString(schoolVOList));
        return schoolVOList;
    }

    public boolean add(List<SchoolQO> schoolListQO) {
        if (CollectionUtil.isEmpty(schoolListQO)) {
            log.info("schoolListQO is empty");
            throw GlobalException.from(ResultCode.ARGUMENT_NOT_VALID);
        }
        List<School> schoolList = dozerUtil.copyList(schoolListQO, School.class);
        log.info("schoolList = {}", JSON.toJSONString(schoolList));
        return schoolService.saveBatch(schoolList);
    }

    public boolean edit(List<SchoolQO> schoolQOList) {
        if (CollectionUtil.isEmpty(schoolQOList)) {
            log.info("schoolQOList is empty");
            throw GlobalException.from(ResultCode.ARGUMENT_NOT_VALID);
        }
        List<School> schoolList = dozerUtil.copyList(schoolQOList, School.class);
        log.info("schoolList = {}", JSON.toJSONString(schoolList));
        return schoolService.updateBatchById(schoolList);
    }
}
