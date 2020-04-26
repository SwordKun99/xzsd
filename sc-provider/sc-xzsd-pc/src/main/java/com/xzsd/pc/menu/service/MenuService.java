package com.xzsd.pc.menu.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.dao.MenuDao;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.MenuInfo;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.MenuInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-28
 */

@Service
public class MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private UserDao userDao;

    /**
     * menu 新增菜单
     *
     * @param menuInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveMenu(MenuInfo menuInfo) {
        // 校验菜单是否存在
        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MenuInfo::getMenuName, menuInfo.getMenuName());
        int countMenuName = menuDao.selectCount(queryWrapper);
        if (0 != countMenuName) {
            return AppResponse.bizError("菜单已存在，请重新输入！");
        }
        menuInfo.setMenuNumber(StringUtil.getCommonCode(2));
        menuInfo.setIsDelete(0);
        menuInfo.setVersion(0);
        menuInfo.setCreateTime(new Date());
        String menuId = SecurityUtils.getCurrentUserId();
        menuInfo.setCreateUser(menuId);
        menuInfo.setMenuId(UUIDUtils.getUUID());
        // 新增菜单
        Integer count = menuDao.insert(menuInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * menu 删除菜单
     *
     * @param menuInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateMenuById(MenuInfo menuInfo) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        menuInfo = menuDao.selectById(menuInfo.getMenuId());
        if (menuInfo == null) {
            appResponse = AppResponse.bizError("查询不到该菜单，请重试！");
            return appResponse;
        }
        menuInfo.setIsDelete(1);
        int count = menuDao.updateById(menuInfo);
        if (count == 0) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        return appResponse;
    }

    /**
     * menu 修改菜单
     *
     * @param menuInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateMenu(MenuInfoVO menuInfoVO) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验菜单是否存在
        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MenuInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(MenuInfo::getMenuName, menuInfoVO.getMenuName());
        queryWrapper.lambda().ne(MenuInfo::getMenuId, menuInfoVO.getMenuId());
        Integer countMenuAcct = menuDao.selectCount(queryWrapper);
        if (0 != countMenuAcct) {
            return AppResponse.bizError("菜单已存在，请重新输入！");
        }
        // 修改菜单信息
        MenuInfo menuInfoOld = menuDao.selectById(menuInfoVO.getMenuId());
        if (menuInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该菜单，请重试！");
            return appResponse;
        }
        MenuInfo menuInfo = new MenuInfo();
        BeanUtils.copyProperties(menuInfoVO, menuInfo);
        String commodityId = SecurityUtils.getCurrentUserId();
        menuInfo.setUpdateUser(commodityId);
        menuInfo.setVersion(menuInfoOld.getVersion() + 1);
        menuInfo.setUpdateTime(new Date());
        int count = menuDao.updateById(menuInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("菜单信息有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }

    /**
     * menu 查询菜单详情
     *
     * @param menuInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse getMenuByInfo(MenuInfo menuInfo) {
        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MenuInfo::getMenuId, menuInfo.getMenuId());
        MenuInfo info = menuDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", info);
    }

    /**
     * menu 查询菜单列表
     *
     * @param menuInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listMenus(MenuInfo menuInfo) {
        String userId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(userId);
        Integer userRole = userInfo.getRole();
        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MenuInfo::getIsDelete, 0);
        List<MenuInfo> menuInfoList = null;
        PageHelper.startPage(menuInfo.getPagesize(), menuInfo.getStartPage());
        MenuInfoVO menuInfoVO = new MenuInfoVO();
        BeanUtils.copyProperties(menuInfo, menuInfoVO);
        if (userRole != null && userRole == 2) {//店长，2/3
            queryWrapper.lambda().in(MenuInfo::getRole, 2, 3);
        } else if (userRole != null && userRole == 1) {//管理员,1/3
            queryWrapper.lambda().in(MenuInfo::getRole, 1, 3);
        }
        menuInfoList = menuDao.selectList(queryWrapper);
        // 包装Page对象
        PageInfo<MenuInfo> pageData = new PageInfo<>(menuInfoList);
        return AppResponse.success("查询成功！", pageData);
    }

}
