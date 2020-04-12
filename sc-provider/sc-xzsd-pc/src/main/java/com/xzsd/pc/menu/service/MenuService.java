package com.xzsd.pc.menu.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.xzsd.pc.dao.MenuDao;
import com.xzsd.pc.entity.MenuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    /**
     * menu 新增菜单
     *
     * @param menuInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveMenu(MenuInfo menuInfo) {
        // 校验账号是否存在
        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MenuInfo::getMenuName, menuInfo.getMenuName());
        int countMenuName = menuDao.selectCount(queryWrapper);
        if (0 != countMenuName) {
            return AppResponse.bizError("菜单名称已存在，请重新输入！");
        }
        menuInfo.setMenuNumber(StringUtil.getCommonCode(2));
        menuInfo.setIsDelete(0);
        // 新增菜单
        Integer count = menuDao.insert(menuInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }


    /**
     * menu 查询菜单列表（分页）
     *
     * @param menuInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listMenus(MenuInfo menuInfo) {
        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(MenuInfo::getMenuName, menuInfo.getMenuName());
        PageInfo<MenuInfo> pageData = PageHelper.startPage(menuInfo.getStartPage(), menuInfo.getPagesize()).doSelectPageInfo(() -> menuDao.selectList(queryWrapper));
        return AppResponse.success("查询成功！", pageData);
    }


    /**
     * menu 删除菜单
     *
     * @param menuInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateMenuById(MenuInfo menuInfo) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        menuInfo = menuDao.selectById(menuInfo.getMenuId());
        if (menuInfo == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
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
     * @param menuInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateMenu(MenuInfo menuInfo) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验菜单是否存在
        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MenuInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(MenuInfo::getMenuName, menuInfo.getMenuName());
        queryWrapper.lambda().ne(MenuInfo::getMenuId, menuInfo.getMenuId());
        Integer countMenuAcct = menuDao.selectCount(queryWrapper);
        if (0 != countMenuAcct) {
            return AppResponse.bizError("菜单名称已存在，请重新输入！");
        }
        // 修改菜单信息
        MenuInfo menuInfoOld = menuDao.selectById(menuInfo.getMenuId());
        if (menuInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        menuInfo.setVersion(menuInfoOld.getVersion() + 1);
        int count = menuDao.updateById(menuInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("数据有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }


    /**
     * menu 查询菜单详情
     *
     * @param menuInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse getMenuByInfo(MenuInfo menuInfo) {
        QueryWrapper<MenuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MenuInfo::getMenuNumber, menuInfo.getMenuNumber());
        MenuInfo info = menuDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", info);
    }

}
