package com.cdtu.xsshome.vo.response;

import java.util.List;

/**
 * 人脸搜索返回的JSON对应的JavaBean
 * @author 小帅丶
 *
 */
public class FaceSerachResponse {
	private String log_id;
	private String error_msg;
	private String error_code;
	private String cached;
	private String timestamp;
	private Result result;
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getCached() {
		return cached;
	}
	public void setCached(String cached) {
		this.cached = cached;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public static class Result{
		private String face_token;
		private List<UserList> user_list;
		public String getFace_token() {
			return face_token;
		}
		public void setFace_token(String face_token) {
			this.face_token = face_token;
		}
		public List<UserList> getUser_list() {
			return user_list;
		}
		public void setUser_list(List<UserList> user_list) {
			this.user_list = user_list;
		}
	}
	public static class UserList{
		private float score;
		private String group_id;
		private String user_id;
		private String user_info;
		public float getScore() {
			return score;
		}
		public void setScore(float score) {
			this.score = score;
		}
		public String getGroup_id() {
			return group_id;
		}
		public void setGroup_id(String group_id) {
			this.group_id = group_id;
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		public String getUser_info() {
			return user_info;
		}
		public void setUser_info(String user_info) {
			this.user_info = user_info;
		}
	}
}
