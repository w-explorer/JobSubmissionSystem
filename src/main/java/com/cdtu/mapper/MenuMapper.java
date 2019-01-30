package com.cdtu.mapper;

import java.util.List;

import com.cdtu.model.Menu;

public interface MenuMapper {

	List<Menu> selectByRoleName(String roleName);

	List<Menu> selectMenuByRole(String role);

	List<Menu> selectMenuAll();
}