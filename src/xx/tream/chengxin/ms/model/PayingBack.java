package xx.tream.chengxin.ms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_paying_back")
public class PayingBack {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "trainId")
	private Long trainId;

	@Column(name = "paying")
	private double paying;

	@Column(name = "sort")
	private int sort;

	@Column(name = "status")
	private String status;

	@Column(name = "createUser")
	private Long createUser;

	@Column(name = "createTime")
	private Date createTime;

	@Column(name = "payingId")
	private Long payingId;

	@Column(name = "updateUser")
	private Long updateUser;

	@Column(name = "updateTime")
	private Date updateTime;

	@Column(name = "updateNote")
	private String updateNote;

	@Column(name = "updateStatus")
	private String updateStatus;
	
	@Column(name = "createUserName")
	private String createUserName;
	
	@Column(name = "updateUserName")
	private String updateUserName;
	
	@Column(name = "auditNote")
	private String auditNote;

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getAuditNote() {
		return auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

	public Long getPayingId() {
		return this.payingId;
	}

	public void setPayingId(Long payingId) {
		this.payingId = payingId;
	}

	public Long getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateNote() {
		return this.updateNote;
	}

	public void setUpdateNote(String updateNote) {
		this.updateNote = updateNote;
	}

	public String getUpdateStatus() {
		return this.updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTrainId() {
		return this.trainId;
	}

	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}

	public double getPaying() {
		return this.paying;
	}

	public void setPaying(double paying) {
		this.paying = paying;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Long getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}