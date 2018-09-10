package kaizone.songmaya.smartns.model;

import kaizone.songmaya.smartns.util.ObjectUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "near_customer_extra")
public class CustomerExtra {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "sso_id", unique = true, nullable = false)
    private String ssoId;
    private String idFront;
    private String idReverse;
    private String idHold;
    private String contacts;

    private String borrowAmt;
    private String borrowLimit;
    private String profession; //职业身份
    private String monthIncome;  //月收入
    private String houseFlag;  //房产情况
    private int houseCode;
    private String carFlag;
    private int carCode;
    private String workAddress;  //单位地址
    private String liveAddress;  //家庭住址

    private String liveProCode;
    private String liveCityCode;
    private String liveAreaCode;

    private String workProCode;
    private String workCityCode;
    private String workAreaCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getIdFront() {
        return idFront;
    }

    public void setIdFront(String idFront) {
        this.idFront = idFront;
    }

    public String getIdReverse() {
        return idReverse;
    }

    public void setIdReverse(String idReverse) {
        this.idReverse = idReverse;
    }

    public String getIdHold() {
        return idHold;
    }

    public void setIdHold(String idHold) {
        this.idHold = idHold;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getBorrowAmt() {
        return borrowAmt;
    }

    public void setBorrowAmt(String borrowAmt) {
        this.borrowAmt = borrowAmt;
    }

    public String getBorrowLimit() {
        return borrowLimit;
    }

    public void setBorrowLimit(String borrowLimit) {
        this.borrowLimit = borrowLimit;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    public String getHouseFlag() {
        return houseFlag;
    }

    public void setHouseFlag(String houseFlag) {
        this.houseFlag = houseFlag;
    }

    public int getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(int houseCode) {
        this.houseCode = houseCode;
    }

    public String getCarFlag() {
        return carFlag;
    }

    public void setCarFlag(String carFlag) {
        this.carFlag = carFlag;
    }

    public int getCarCode() {
        return carCode;
    }

    public void setCarCode(int carCode) {
        this.carCode = carCode;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress;
    }

    public String getLiveProCode() {
        return liveProCode;
    }

    public void setLiveProCode(String liveProCode) {
        this.liveProCode = liveProCode;
    }

    public String getLiveCityCode() {
        return liveCityCode;
    }

    public void setLiveCityCode(String liveCityCode) {
        this.liveCityCode = liveCityCode;
    }

    public String getLiveAreaCode() {
        return liveAreaCode;
    }

    public void setLiveAreaCode(String liveAreaCode) {
        this.liveAreaCode = liveAreaCode;
    }

    public String getWorkProCode() {
        return workProCode;
    }

    public void setWorkProCode(String workProCode) {
        this.workProCode = workProCode;
    }

    public String getWorkCityCode() {
        return workCityCode;
    }

    public void setWorkCityCode(String workCityCode) {
        this.workCityCode = workCityCode;
    }

    public String getWorkAreaCode() {
        return workAreaCode;
    }

    public void setWorkAreaCode(String workAreaCode) {
        this.workAreaCode = workAreaCode;
    }

    public void fill(CustomerExtra c) {
        ObjectUtil.fill(c, this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
