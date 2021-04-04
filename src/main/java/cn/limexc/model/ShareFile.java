package cn.limexc.model;

public class ShareFile {
    private Integer id;
    private Integer fid;
    //uid好像貌似与fuid功能差不多，要啥uid？搞数据库的时候脑子进水了。
    private Integer uid;
    private Integer ufid;
    private String url;
    //忘了当时搞这个index是干啥的了，可能是存什么的路径？忘了，实在想不起来后期就删了。
    private String index;
    private String other;

    public ShareFile() {
    }

    public ShareFile(Integer id, Integer fid, Integer uid, Integer ufid, String url, String index, String other) {
        this.id = id;
        this.fid = fid;
        this.uid = uid;
        this.ufid = ufid;
        this.url = url;
        this.index = index;
        this.other = other;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getUfid() {
        return ufid;
    }

    public void setUfid(Integer fuid) {
        this.ufid = fuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "ShareFile{" +
                "id=" + id +
                ", fid=" + fid +
                ", uid=" + uid +
                ", ufid=" + ufid +
                ", url='" + url + '\'' +
                ", index='" + index + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}
