package com.cdtu.xsshome.common;

import com.baidu.aip.face.AipFace;

/**
 * 单例加载百度SDK
 * @author 小帅丶
 *
 */
public class FactoryUtil {
	private static AipFace aipFace;

	public static AipFace getAipFace(){
		if(aipFace==null){
			synchronized (AipFace.class) {
				if(aipFace==null){
					aipFace = new AipFace(AIConstant.BD_FACE_APPID, AIConstant.BD_FACE_APPKEY, AIConstant.BD_FACE_SECRETKEY);
				}
			}
		}
		return aipFace;
	}

}
