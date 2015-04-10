package xx.tream.chengxin.ms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 公共支出
 * @author huawen
 *
 */
@Table(name = "tb_payoutcommon_back")
public class PayoutCommonBack {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "createUser")
	private Long createUser;

	@Column(name = "createTime")
	private Date createTime;



	@Column(name = "type")
	private String type;

	@Column(name = "payout")
	private double payout;

	@Column(name = "status")
	private String status;

	@Column(name = "note")
	private String note;

	@Column(name = "otherType")
	private String otherType;
	
	@Column(name = "createUserName")
	private String createUserName;

	@Column(name = "auditUserName")
	private String auditUserName;
	
	@Column(name = "updateStatus")
	private String updateStatus;
	
	@Column(name = "updateUser")
	private Long updateUser;

	@Column(name = "updateTime")
	private Date updateTime;

	@Column(name = "updateNote")
	private String updateNote;
	
	@Column(name = "updateUserName")
	private String updateUserName;
	
	public String getAuditNote() {
		return auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

	@Column(name = "auditNote")
	private String auditNote;
	
	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	
	public Long getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateNote() {
		return updateNote;
	}

	public void setUpdateNote(String updateNote) {
		this.updateNote = updateNote;
	}

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public Long getPayoutId() {
		return payoutId;
	}

	public void setPayoutId(Long payoutId) {
		this.payoutId = payoutId;
	}

	@Column(name = "payoutId")
	private Long payoutId;

	public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPayout() {
		return this.payout;
	}

	public void setPayout(double payout) {
		this.payout = payout;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}