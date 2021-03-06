package cn.limexc.model;

import org.springframework.stereotype.Component;


@Component
public class User {
    private Integer id;
    private Integer gid;
    private String username;
    private String password;
    private String email;
    private Integer status;
    private String storage;
    private String create_at;
    private String delete_at;
    private String alisa;
    private String  profile;

    public User() {
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String  create_at) {
        this.create_at = create_at;
    }

    public String  getDelete_at() {
        return delete_at;
    }

    public void setDelete_at(String delete_at) {
        this.delete_at = delete_at;
    }

    public String getAlisa() {
        return alisa;
    }

    public void setAlisa(String alisa) {
        this.alisa = alisa;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", gid=" + gid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", storage=" + storage +
                ", create_at='" + create_at + '\'' +
                ", delete_at='" + delete_at + '\'' +
                ", alisa='" + alisa + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
