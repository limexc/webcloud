package cn.limexc.model;

import java.math.BigDecimal;
import java.sql.Date;

public class UserFile {
    private Integer id;
    private String filename;
    private BigDecimal filesize;
    private String vfname;
    private String virtualpath;
    private Date up_time;

    public UserFile() {
    }

    public UserFile(Integer id, String filename, BigDecimal filesize, String vfname, String virtualpath, Date up_time) {
        this.id = id;
        this.filename = filename;
        this.filesize = filesize;
        this.vfname = vfname;
        this.virtualpath = virtualpath;
        this.up_time = up_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public BigDecimal getFilesize() {
        return filesize;
    }

    public void setFilesize(BigDecimal filesize) {
        this.filesize = filesize;
    }

    public String getVfname() {
        return vfname;
    }

    public void setVfname(String vfname) {
        this.vfname = vfname;
    }

    public String getVirtualpath() {
        return virtualpath;
    }

    public void setVirtualpath(String virtualpath) {
        this.virtualpath = virtualpath;
    }

    public Date getUp_time() {
        return up_time;
    }

    public void setUp_time(Date up_time) {
        this.up_time = up_time;
    }

    @Override
    public String toString() {
        return "UserFile{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", filesize=" + filesize +
                ", vfname='" + vfname + '\'' +
                ", virtualpath='" + virtualpath + '\'' +
                ", up_time=" + up_time +
                '}';
    }
}
