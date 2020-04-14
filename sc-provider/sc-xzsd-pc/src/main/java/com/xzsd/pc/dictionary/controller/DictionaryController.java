package com.xzsd.pc.dictionary.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.pc.dictionary.service.DictionaryService;
import com.xzsd.pc.user.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description查省市区
 * @Author SwordKun.
 * @Date 2020-04-10
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * shop 查询省份列表
     *
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @RequestMapping(value = "getProviceByIdList")
    public AppResponse getProviceByIdList() {
        try {
            return dictionaryService.getProviceByIdList();
        } catch (Exception e) {
            logger.error("省份查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * shop 查询市列表
     *
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @RequestMapping(value = "getCityByPrviceCode")
    public AppResponse getCityByPrviceCode(String proviceCode) {
        try {
            return dictionaryService.getCityByPrviceCode(proviceCode);
        } catch (Exception e) {
            logger.error("市查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * shop 查询区列表
     *
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @RequestMapping(value = "getDistrictByCityCode")
    public AppResponse getDistrictByCityCode(String cityCode) {
        try {
            return dictionaryService.getDistrictByCityCode(cityCode);
        } catch (Exception e) {
            logger.error("区查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
