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

    private String certNo;  //身份证
    private String realName;  //真实姓名
    private String idFront;
    private String idReverse;
    private String idHold;
    private String contacts;

    private String borrowAmt;
    private String borrowLimit;
    private String workAsCode; //职业身份
    private String incomeAsCode;   //收入类型
    private String income;  //月收入
    private int houseAsCode;
    private int carAsCode;
    private String workAddress;  //单位地址
    private String liveAddress;  //家庭住址

    private String liveProvinceCode;
    private String liveCityCode;
    private String liveAreaCode;

    private String workProvinceCode;
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

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getWorkAsCode() {
        return workAsCode;
    }

    public void setWorkAsCode(String workAsCode) {
        this.workAsCode = workAsCode;
    }

    public String getIncomeAsCode() {
        return incomeAsCode;
    }

    public void setIncomeAsCode(String incomeAsCode) {
        this.incomeAsCode = incomeAsCode;
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

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public int getHouseAsCode() {
        return houseAsCode;
    }

    public void setHouseAsCode(int houseAsCode) {
        this.houseAsCode = houseAsCode;
    }


    public int getCarAsCode() {
        return carAsCode;
    }

    public void setCarAsCode(int carAsCode) {
        this.carAsCode = carAsCode;
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

    public String getLiveProvinceCode() {
        return liveProvinceCode;
    }

    public void setLiveProvinceCode(String liveProvinceCode) {
        this.liveProvinceCode = liveProvinceCode;
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

    public String getWorkProvinceCode() {
        return workProvinceCode;
    }

    public void setWorkProvinceCode(String workProvinceCode) {
        this.workProvinceCode = workProvinceCode;
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
