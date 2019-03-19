package com.zttx.po;

import java.util.Date;

public class File_info {
	private String file_id;
	private String file_name;
	private String file_type;
	private String uploader;
	private Date upload_time;
	private Integer is_verify;
	private String reviewer;
	private Date review_time;
	private String need_to_sign_list;
	private String path;
	private Date sign_dead_time;
	private Integer is_Issued;
	private String note;
	private String project_name;
	private String construction_units;
	
	private String vPath;
	
	public String getvPath() {
		return vPath;
	}
	public void setvPath(String vPath) {
		this.vPath = vPath;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public Date getUpload_time() {
		return upload_time;
	}
	public void setUpload_time(Date upload_time) {
		this.upload_time = upload_time;
	}
	public Integer getIs_verify() {
		return is_verify;
	}
	public void setIs_verify(Integer is_verify) {
		this.is_verify = is_verify;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public Date getReview_time() {
		return review_time;
	}
	public void setReview_time(Date review_time) {
		this.review_time = review_time;
	}
	public String getNeed_to_sign_list() {
		return need_to_sign_list;
	}
	public void setNeed_to_sign_list(String need_to_sign_list) {
		this.need_to_sign_list = need_to_sign_list;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getSign_dead_time() {
		return sign_dead_time;
	}
	public void setSign_dead_time(Date sign_dead_time) {
		this.sign_dead_time = sign_dead_time;
	}
	public Integer getIs_Issued() {
		return is_Issued;
	}
	public void setIs_Issued(Integer is_Issued) {
		this.is_Issued = is_Issued;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getConstruction_units() {
		return construction_units;
	}
	public void setConstruction_units(String construction_units) {
		this.construction_units = construction_units;
	}
	
	
}
