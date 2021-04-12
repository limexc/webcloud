package cn.limexc.model;

import org.springframework.stereotype.Component;


@Component
public class UserFile {
    private Integer id;
    private String filesize;
    private String vfname;
    private String vpath;
    private String  uptime;
    private Integer uid;
    private Integer fid;
    private String iconsign;

    public UserFile() {
    }

    public UserFile(Integer id, String filesize, String vfname, String vpath, String uptime, Integer uid, Integer fid, String iconsign) {
        this.id = id;
        this.filesize = filesize;
        this.vfname = vfname;
        this.vpath = vpath;
        this.uptime = uptime;
        this.uid = uid;
        this.fid = fid;
        this.iconsign = iconsign;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getVfname() {
        return vfname;
    }

    public void setVfname(String vfname) {
        this.vfname = vfname;
    }

    public String getVpath() {
        return vpath;
    }

    public void setVpath(String vpath) {
        this.vpath = vpath;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getIconsign() {
        return iconsign;
    }

    public void setIconsign(String iconsign) {
        this.iconsign = iconsign;
    }

    @Override
    public String toString() {
        return "UserFile{" +
                "id=" + id +
                ", filesize='" + filesize + '\'' +
                ", vfname='" + vfname + '\'' +
                ", vpath='" + vpath + '\'' +
                ", uptime='" + uptime + '\'' +
                ", uid=" + uid +
                ", fid=" + fid +
                ", iconsign='" + iconsign + '\'' +
                '}';
    }
}
