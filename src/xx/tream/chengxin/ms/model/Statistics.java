package xx.tream.chengxin.ms.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_statistics")
public class Statistics
{

  @Id
  @Column(name="id")
  private Long id;

  @Column(name="statisticsTime")
  private Date statisticsTime;

  @Column(name="createTime")
  private Date createTime;

  @Column(name="paying")
  private double paying;

  @Column(name="exam")
  private double exam;

  @Column(name="makeup")
  private double makeup;

  @Column(name="cardFee")
  private double cardFee;

  @Column(name="carRental")
  private double carRental;

  @Column(name="signUp")
  private double signUp;

  @Column(name="trainFee")
  private double trainFee;

  @Column(name="checkUp")
  private double checkUp;

  @Column(name="dmvFee")
  private double dmvFee;

  @Column(name="oilFee")
  private double oilFee;

  @Column(name="repairFee")
  private double repairFee;

  @Column(name="baoShen")
  private double baoShen;

  @Column(name="tyreFee")
  private double tyreFee;

  @Column(name="officeFee")
  private double officeFee;

  @Column(name="foodFee")
  private double foodFee;

  @Column(name="businessFee")
  private double businessFee;

  @Column(name="takeCarFee")
  private double takeCarFee;

  @Column(name="otherFee")
  private double otherFee;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getStatisticsTime() {
    return this.statisticsTime;
  }

  public void setStatisticsTime(Date statisticsTime) {
    this.statisticsTime = statisticsTime;
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

  public double getExam() {
    return this.exam;
  }

  public void setExam(double exam) {
    this.exam = exam;
  }

  public double getMakeup() {
    return this.makeup;
  }

  public void setMakeup(double makeup) {
    this.makeup = makeup;
  }

  public double getCardFee() {
    return this.cardFee;
  }

  public void setCardFee(double cardFee) {
    this.cardFee = cardFee;
  }

  public double getCarRental() {
    return this.carRental;
  }

  public void setCarRental(double carRental) {
    this.carRental = carRental;
  }

  public double getSignUp() {
    return this.signUp;
  }

  public void setSignUp(double signUp) {
    this.signUp = signUp;
  }

  public double getTrainFee() {
    return this.trainFee;
  }

  public void setTrainFee(double trainFee) {
    this.trainFee = trainFee;
  }

  public double getCheckUp() {
    return this.checkUp;
  }

  public void setCheckUp(double checkUp) {
    this.checkUp = checkUp;
  }

  public double getDmvFee() {
    return this.dmvFee;
  }

  public void setDmvFee(double dmvFee) {
    this.dmvFee = dmvFee;
  }

  public double getOilFee() {
    return this.oilFee;
  }

  public void setOilFee(double oilFee) {
    this.oilFee = oilFee;
  }

  public double getRepairFee() {
    return this.repairFee;
  }

  public void setRepairFee(double repairFee) {
    this.repairFee = repairFee;
  }

  public double getBaoShen() {
    return this.baoShen;
  }

  public void setBaoShen(double baoShen) {
    this.baoShen = baoShen;
  }

  public double getTyreFee() {
    return this.tyreFee;
  }

  public void setTyreFee(double tyreFee) {
    this.tyreFee = tyreFee;
  }

  public double getOfficeFee() {
    return this.officeFee;
  }

  public void setOfficeFee(double officeFee) {
    this.officeFee = officeFee;
  }

  public double getFoodFee() {
    return this.foodFee;
  }

  public void setFoodFee(double foodFee) {
    this.foodFee = foodFee;
  }

  public double getBusinessFee() {
    return this.businessFee;
  }

  public void setBusinessFee(double businessFee) {
    this.businessFee = businessFee;
  }

  public double getTakeCarFee() {
    return this.takeCarFee;
  }

  public void setTakeCarFee(double takeCarFee) {
    this.takeCarFee = takeCarFee;
  }

  public double getOtherFee() {
    return this.otherFee;
  }

  public void setOtherFee(double otherFee) {
    this.otherFee = otherFee;
  }
}