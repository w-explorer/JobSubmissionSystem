package com.cdtu.util;

public class MaxPage {
	/**
	 * 获取最大页数
	 * @param page
	 * @author LR
	 * @return
	 */
	public static Integer getMaxPage(Integer page){
		Integer max;
		if(page%5==0){
			max=page/5;
			return max;
		}else{
			max=(page/5)+1;
			return max;
		}
		
	}

}
