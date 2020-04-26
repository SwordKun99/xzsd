package com.xzsd.pc.dictionary.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.core.restful.AppResponse;
import com.xzsd.pc.dao.CityDao;
import com.xzsd.pc.dao.DistrictDao;
import com.xzsd.pc.dao.PrviceDao;
import com.xzsd.pc.entity.CityInfo;
import com.xzsd.pc.entity.DistrictInfo;
import com.xzsd.pc.entity.PrviceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @DescriptionDemo 省市区公用实现类
 * @Author SwordKun.
 * @Date 2020-04-10
 */

@Service
public class DictionaryService {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);

    @Autowired
    private PrviceDao prviceDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private DistrictDao districtDao;

    /**
     * shop 省份列表
     *
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    public AppResponse getProviceByIdList() {
        QueryWrapper<PrviceInfo> queryWrapper = new QueryWrapper<>();
        List<PrviceInfo> list = prviceDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", list);
    }

    /**
     * shop 查询市列表
     *
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    public AppResponse getCityByPrviceCode(String proviceCode) {
        QueryWrapper<CityInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CityInfo::getProviceCode, proviceCode);
        List<CityInfo> list = cityDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", list);
    }

    /**
     * shop 查询区列表
     *
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    public AppResponse getDistrictByCityCode(String cityCode) {
        QueryWrapper<DistrictInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DistrictInfo::getCityCode, cityCode);
        List<DistrictInfo> list = districtDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", list);
    }
}
