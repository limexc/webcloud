package cn.limexc.model;

/**
 * 真的需要这个类吗？正在思考。
 */
public class UserGroup {
    private Integer id;
    private Integer uid;
    private Integer gid;

    public UserGroup() {
    }

    public UserGroup(Integer id, Integer uid, Integer gid) {
        this.id = id;
        this.uid = uid;
        this.gid = gid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", uid=" + uid +
                ", gid=" + gid +
                '}';
    }
}
