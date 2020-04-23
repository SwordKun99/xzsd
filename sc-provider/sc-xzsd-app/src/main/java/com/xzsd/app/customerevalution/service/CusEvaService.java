package com.xzsd.app.customerevalution.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.dao.CustomerEvalutionDao;
import com.xzsd.app.entity.CustomerEvaluationInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-10
 */
@Service
public class CusEvaService {

    @Resource
    private CustomerEvalutionDao customerEvalutionDao;

    /**
     * drive 查询订单列表（分页）
     *
     * @param customerEvaluationInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listCusEva(CustomerEvaluationInfo customerEvaluationInfo) {
        QueryWrapper<CustomerEvaluationInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustomerEvaluationInfo::getIsDelete, 0);
        if (customerEvaluationInfo.getStarLevel() != null && customerEvaluationInfo.getStarLevel() != "") {
            queryWrapper.lambda().in(CustomerEvaluationInfo::getStarLevel,4,5);
            customerEvaluationInfo.getStarLevel();
        }
        if (customerEvaluationInfo.getStarLevel() != null && customerEvaluationInfo.getStarLevel() != "") {
            queryWrapper.lambda().in(CustomerEvaluationInfo::getStarLevel,2,3);
            customerEvaluationInfo.getStarLevel();
        }
        if (customerEvaluationInfo.getStarLevel() != null && customerEvaluationInfo.getStarLevel() != "") {
            queryWrapper.lambda().in(CustomerEvaluationInfo::getStarLevel,0,1);
            customerEvaluationInfo.getStarLevel();
        }
        PageInfo<CustomerEvaluationInfo> pageData = PageHelper.startPage(customerEvaluationInfo.getPageNum(), customerEvaluationInfo.getPageSize()).doSelectPageInfo(() -> customerEvalutionDao.selectList(queryWrapper));
        return AppResponse.success("查询司机列表成功", pageData);
    }
}
