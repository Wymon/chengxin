package xx.tream.chengxin.ms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="tb_user_back")
public class UserBack implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6863347315506074304L;
	@Id 
	@Column(name = "id")
	private long id;
	/**
	 * 登录名
	 */
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "realName")
	private String realName;
	
	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;
	
	/**
	 * 创建人
	 */
	@Column(name = "createUser")
	private long createUser;
	
	@Column(name = "createUserName")
	private String createUserName;
	/**
	 * 创建时间
	 */
	@Column(name = "createTime")
	private Date createTime;
	/**
	 * 1表示有效,0表示无效
	 */
	@Column(name = "status")
	private String status;
	@Column(name = "email")
	private String email;
	@Column(name = "canEmail")
	private String canEmail;
	
	@Column(name = "updateUserName")
	private String updateUserName;
	
	@Column(name = "auditNote")
	private String auditNote;
	@Column(name = "updateUser")
	private Long updateUser;

	@Column(name = "updateTime")
	private Date updateTime;

	@Column(name = "updateNote")
	private String updateNote;

	@Column(name = "updateStatus")
	private String updateStatus;
	
	@Column(name = "backId")
	private Long backId;
	
	@Column(name = "roleId")
	private Long roleId;
	
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getBackId() {
		return backId;
	}
	public void setBackId(Long backId) {
		this.backId = backId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(long createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCanEmail() {
		return canEmail;
	}
	public void setCanEmail(String canEmail) {
		this.canEmail = canEmail;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
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
}
