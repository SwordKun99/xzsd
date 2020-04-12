package com.xzsd.pc.menu.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.entity.MenuInfo;
import com.xzsd.pc.menu.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description增删改查Menu
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Resource
    private MenuService menuService;


    /**
     * menu 新增菜单
     *
     * @param menuInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-03-28
     */
    @PostMapping("saveMenu")
    public AppResponse saveMenu(@RequestBody MenuInfo menuInfo) {
        try {
            //获取菜单id
            String menuId = AuthUtils.getCurrentUserId();
            menuInfo.setCreateUser(menuId);
            menuInfo.setMenuId(UUIDUtils.getUUID());
            AppResponse appResponse = menuService.saveMenu(menuInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("菜单新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * menu 菜单列表(分页)
     *
     * @param menuInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @RequestMapping(value = "listMenus")
    public AppResponse listMenus(@RequestBody MenuInfo menuInfo) {
        try {
            return menuService.listMenus(menuInfo);
        } catch (Exception e) {
            logger.error("查询菜单列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * menu 删除菜单
     *
     * @param menuInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @PostMapping("deleteMenu")
    public AppResponse deleteMenu(@RequestBody MenuInfo menuInfo) {
        try {
            return menuService.updateMenuById(menuInfo);
        } catch (Exception e) {
            logger.error("菜单删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * menu 修改菜单
     *
     * @param menuInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @PostMapping("updateMenu")
    public AppResponse updateMenu(@RequestBody MenuInfo menuInfo) {
        try {
            //获取菜单id
            String menuId = AuthUtils.getCurrentUserId();
            menuInfo.setCreateUser(menuId);
            menuInfo.setUpdateUser(menuId);
            return menuService.updateMenu(menuInfo);
        } catch (Exception e) {
            logger.error("修改菜单信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * menu 查询菜单详情
     *
     * @param menuInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @RequestMapping(value = "getMenuByInfo")
    public AppResponse getMenuByInfo(@RequestBody MenuInfo menuInfo) {
        try {
            return menuService.getMenuByInfo(menuInfo);
        } catch (Exception e) {
            logger.error("菜单查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}