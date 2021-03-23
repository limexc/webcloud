package cn.limexc.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
@Component
public class UserFile {
    private Integer id;
    private String filename;
    private BigDecimal filesize;
    private String vfname;
    private String virtualpath;
    private Date up_time;
    private String filetype;

    public UserFile() {
    }

    public UserFile(Integer id, String filename, BigDecimal filesize, String vfname, String virtualpath, Date up_time, String filetype) {
        this.id = id;
        this.filename = filename;
        this.filesize = filesize;
        this.vfname = vfname;
        this.virtualpath = virtualpath;
        this.up_time = up_time;
        this.filetype = filetype;
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

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
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
                ", filetype='" + filetype + '\'' +
                '}';
    }
}
