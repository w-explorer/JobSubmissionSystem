package com.cdtu.model;
/**
 * 
 * @author weiyuhang
 *
 */
public class CourseStudent {
	  private String sId;

	    private String sName;

	    private String sMajor;
	    
		private Integer cId;
        private Integer ctId;
        private Integer tscId;
        
	    public Integer getCtId() {
			return ctId;
		}

		public void setCtId(Integer ctId) {
			this.ctId = ctId;
		}

		public Integer getTscId() {
			return tscId;
		}

		public void setTscId(Integer tscId) {
			this.tscId = tscId;
		}

		public Integer getcId() {
			return cId;
		}

		public void setcId(Integer cId) {
			this.cId = cId;
		}

		public String getsId() {
			return sId;
		}

		public void setsId(String sId) {
			this.sId = sId;
		}

		public String getsName() {
			return sName;
		}

		public void setsName(String sName) {
			this.sName = sName;
		}

		public String getsMajor() {
			return sMajor;
		}

		public void setsMajor(String sMajor) {
			this.sMajor = sMajor;
		}

		


		
		
	    
}
