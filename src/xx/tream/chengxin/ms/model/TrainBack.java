package xx.tream.chengxin.ms.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_train_back")
public class TrainBack {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "payable")
	private double payable;

	@Column(name = "arrearage")
	private double arrearage;

	@Column(name = "paying")
	private double paying;

	@Column(name = "idcard")
	private String idcard;

	@Column(name = "newOrOld")
	private String newOrOld;

	@Column(name = "type")
	private String type;

	@Column(name = "licenseTag")
	private String licenseTag;

	@Column(name = "note")
	private String note;

	@Column(name = "createUser")
	private Long createUser;

	@Column(name = "createTime")
	private Date createTime;

	@Column(name = "trainId")
	private Long trainId;

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

	public String getAuditNote() {
		return auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

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

	public Long getTrainId() {
		return this.trainId;
	}

	public void setTrainId(Long trainId) {
		this.trainId = trainId;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPayable() {
		return this.payable;
	}

	public void setPayable(double payable) {
		this.payable = payable;
	}

	public double getArrearage() {
		return this.arrearage;
	}

	public void setArrearage(double arrearage) {
		this.arrearage = arrearage;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getNewOrOld() {
		return this.newOrOld;
	}

	public void setNewOrOld(String newOrOld) {
		this.newOrOld = newOrOld;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLicenseTag() {
		return this.licenseTag;
	}

	public void setLicenseTag(String licenseTag) {
		this.licenseTag = licenseTag;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public double getPaying() {
		return this.paying;
	}

	public void setPaying(double paying) {
		this.paying = paying;
	}
}