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
@Table(name = "tb_payoutcommon")
public class PayoutCommon {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "createUser")
	private Long createUser;

	@Column(name = "createTime")
	private Date createTime;

	@Column(name = "auditUser")
	private Long auditUser;

	@Column(name = "auditTime")
	private Date auditTime;

	@Column(name = "type")
	private String type;

	@Column(name = "payout")
	private Double payout;

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

	public Double getPayout() {
		return this.payout;
	}

	public void setPayout(Double payout) {
		this.payout = payout;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAuditUser() {
		return this.auditUser;
	}

	public void setAuditUser(Long auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
}