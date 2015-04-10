package xx.tream.chengxin.ms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "tb_password_back")
public class PasswordBack implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1935499848217984716L;
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "userId")
	private Long userId;
	@Column(name = "createUser")
	private Long createUser;
	@Column(name = "createUserName")
	private String createUserName;
	@Column(name = "updateStatus")
	private String updateStatus;
	@Column(name = "updateNote")
	private String updateNote;
	@Column(name = "auditUser")
	private Long auditUser;
	@Column(name = "auditUserName")
	private String auditUserName;
	@Column(name = "auditNote")
	private String auditNote;
	@Column(name = "password")
	private String password;
	@Column(name = "createTime")
	private Date createTime;
	@Column(name = "auditTime")
	private Date auditTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}
	public String getUpdateNote() {
		return updateNote;
	}
	public void setUpdateNote(String updateNote) {
		this.updateNote = updateNote;
	}
	public Long getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(Long auditUser) {
		this.auditUser = auditUser;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public String getAuditNote() {
		return auditNote;
	}
	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

}
