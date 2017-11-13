package kaizone.songmaya.qingtian.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "api_user_infos", schema = "jwt")
public class UserInfoEntity implements Serializable {
    @Id
    @Column(name = "aui_app_id")
    private String appId;  //授权唯一标识

    @Column(name = "aui_app_secret")
    private byte[] appSecret;  //授权密钥

    @Column(name = "aui_status")
    private int status;  //用户状态,1：正常，0：无效

    @Column(name = "aui_day_request_count")
    private int dayRequestCount;  //日请求量

    @Column(name = "aui_ajax_bind_ip")
    private String ajaxBindIp;  //绑定IP地址多个使用“,”隔开

    @Column(name = "aui_mark")
    private String mark;  //备注

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public byte[] getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(byte[] appSecret) {
        this.appSecret = appSecret;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDayRequestCount() {
        return dayRequestCount;
    }

    public void setDayRequestCount(int dayRequestCount) {
        this.dayRequestCount = dayRequestCount;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getAjaxBindIp() {
        return ajaxBindIp;
    }

    public void setAjaxBindIp(String ajaxBindIp) {
        this.ajaxBindIp = ajaxBindIp;
    }
}
