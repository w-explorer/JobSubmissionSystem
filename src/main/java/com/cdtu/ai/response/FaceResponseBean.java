package com.cdtu.ai.response;
/***
 * 接口返回的JSON对应的JavaBean 暂时用不到
 * @author 小帅丶
 *
 */
public class FaceResponseBean {
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
		private Location location;
		public String getFace_token() {
			return face_token;
		}
		public void setFace_token(String face_token) {
			this.face_token = face_token;
		}
		public Location getLocation() {
			return location;
		}
		public void setLocation(Location location) {
			this.location = location;
		} 
	}
	public static class Location{
		private double top;
		private double left;
		private double width;
		private double height;
		private int rotation;
		public double getTop() {
			return top;
		}
		public void setTop(double top) {
			this.top = top;
		}
		public double getLeft() {
			return left;
		}
		public void setLeft(double left) {
			this.left = left;
		}
		public double getWidth() {
			return width;
		}
		public void setWidth(double width) {
			this.width = width;
		}
		public double getHeight() {
			return height;
		}
		public void setHeight(double height) {
			this.height = height;
		}
		public int getRotation() {
			return rotation;
		}
		public void setRotation(int rotation) {
			this.rotation = rotation;
		}
	}
}
