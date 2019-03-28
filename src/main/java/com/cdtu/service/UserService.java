package com.cdtu.service;

import java.util.List;
import java.util.Map;

public  interface UserService {

	public List<Map<String, Object>> getPassword(String username, String email);

	public String getEmailByUsername(String username);

}
