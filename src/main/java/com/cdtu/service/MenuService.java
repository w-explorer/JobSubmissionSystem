package com.cdtu.service;

import java.util.List;

import com.cdtu.model.Menu;

public interface MenuService {

	List<Menu> getMenuAll();

	List<Menu> getMenusByRole(String role);
}
