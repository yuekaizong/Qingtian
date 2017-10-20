package kaizone.songmaya.qingtian.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUId = 1L;
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(id).append(",").append(name).append("}");
        return sb.toString();
    }
}
