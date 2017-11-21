package kaizone.songmaya.qingtian.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "good_infos")
public class GoodInfoBean {

    @Id
    @Column(name = "tg_id")
    private Long id;
    @Column(name = "tg_title")
    private String table;
    @Column(name = "tg_price")
    private double price;
    @Column(name = "tg_order")
    private int order;
    @Column(name = "tg_type_id")
    private Long typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
