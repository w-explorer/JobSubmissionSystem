package com.cdtu.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.MenuMapper;
import com.cdtu.model.Menu;
import com.cdtu.service.MenuService;
/**
 * 
 * ClassName:描述类
 *
 * @author wencheng
 *
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

	@Resource
	private MenuMapper menuMapper;

	@Override
	public List<Menu> getMenuAll() {
		return this.menuMapper.selectMenuAll();
	}

	@Override
	public List<Menu> getMenusByRole(String role) {
		List<Menu> allList = this.menuMapper.selectMenuByRole(role);
		List<Menu> parentList = new ArrayList<Menu>();
		List<Menu> childList = new ArrayList<Menu>();
		for (Menu menu : allList) {
			if (menu.getParentId() == 0) {
				parentList.add(menu);
			} else if (menu.getParentId() != 0) {
				childList.add(menu);
			}
		}
		for (Menu pMenu : parentList) {
			List<Menu> list = new ArrayList<Menu>();
			for (Menu cMenu : childList) {
				if (cMenu.getParentId() == pMenu.getMenuId()) {
					list.add(cMenu);
				}
			}
			pMenu.setList(list);
		}
		return parentList;
	}
}
