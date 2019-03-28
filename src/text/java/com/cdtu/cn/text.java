package com.cdtu.cn;

import java.io.File;

public class text {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//获得文件类型（可以判断如果不是图片，禁止上传）ssss
		String contentType ="image/jpeg";
		//获得文件后缀名称
		System.out.println("//////"+contentType);    //////image/jpeg
													 //////image/jpeg
		String imageName = contentType.substring(contentType.lastIndexOf("/") + 1);
		System.out.println("//////"+imageName);
	}

}
