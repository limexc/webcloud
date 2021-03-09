package cn.limexc.model;

import java.sql.Date;

public class User {
    private Integer id;
    private Integer gid;
    private String username;
    private String password;
    private String email;
    private Integer status;
    private Integer storage;
    private Date create_at;
    private Date delete_at;
    private String alisa;

    public User() {
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

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public Date getDelete_at() {
        return delete_at;
    }

    public void setDelete_at(Date delete_at) {
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
                ", create_at=" + create_at +
                ", delete_at=" + delete_at +
                ", alisa='" + alisa + '\'' +
                '}';
    }
}
