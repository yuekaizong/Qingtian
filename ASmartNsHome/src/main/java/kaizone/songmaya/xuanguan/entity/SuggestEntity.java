package kaizone.songmaya.xuanguan.entity;

import javax.persistence.*;

@Entity
@Table(name = "p1_suggest")
public class SuggestEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private String nickname;
    private String email;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
