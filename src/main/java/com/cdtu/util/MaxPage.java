package com.cdtu.util;

public class MaxPage {
	/**
	 * 获取最大页数
	 * @param pagenum
	 * @author LR
	 * @return
	 */
	public static Integer getMaxPage(Integer pagenum, Integer page){
		return pagenum % page == 0 ? pagenum / page : (pagenum / page) + 1;
	}

}
