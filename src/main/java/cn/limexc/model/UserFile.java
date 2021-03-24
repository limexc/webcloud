package cn.limexc.model;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;


@Component
public class UserFile {
    private Integer id;
    private String filename;
    private BigDecimal filesize;
    private String vfname;
    private String virtualpath;
    private String uptime;
    private String filetype;

    public UserFile() {
    }

    public UserFile(Integer id, String filename, BigDecimal filesize, String vfname, String virtualpath, String uptime, String filetype) {
        this.id = id;
        this.filename = filename;
        this.filesize = filesize;
        this.vfname = vfname;
        this.virtualpath = virtualpath;
        this.uptime = uptime;
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

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
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
                ", uptime=" + uptime +
                ", filetype='" + filetype + '\'' +
                '}';
    }
}
