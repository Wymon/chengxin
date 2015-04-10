package xx.tream.chengxin.ms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_autumnnumber")
public class AutumnNumber
{

  @Id
  @Column(name="id")
  private Long id;

  @Column(name="trainId")
  private Long trainId;

  @Column(name="autumnNumber")
  private String autumnNumber;

  @Column(name="createUser")
  private Long createUser;

  @Column(name="createTime")
  private Date createTime;
  
  @Column(name="createUserName")
  private String createUserName;

  public String getCreateUserName() {
	return createUserName;
  }
  public void setCreateUserName(String createUserName) {
	this.createUserName = createUserName;
  }

  public Long getId()
  {
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
  public String getAutumnNumber() {
    return this.autumnNumber;
  }
  public void setAutumnNumber(String autumnNumber) {
    this.autumnNumber = autumnNumber;
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
}