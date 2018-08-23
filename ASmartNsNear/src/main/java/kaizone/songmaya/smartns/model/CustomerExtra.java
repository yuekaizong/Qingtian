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
    private int houseState;  //房产情况
    private int carState;  //车辆情况
    private String workAddress;  //单位地址

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

    public int getHouseState() {
        return houseState;
    }

    public void setHouseState(int houseState) {
        this.houseState = houseState;
    }

    public int getCarState() {
        return carState;
    }

    public void setCarState(int carState) {
        this.carState = carState;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public void fill(CustomerExtra c) {
        ObjectUtil.fill(c, this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
