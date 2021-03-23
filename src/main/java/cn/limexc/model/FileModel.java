package cn.limexc.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;

@Component
public class FileModel {
    private Integer id;
    private String filename;
    private String md5;
    private Date create_time;
    private BigDecimal filesize;
    private String filetype;

    public FileModel() {}

    public FileModel(Integer id, String filename, String md5, Date create_time, BigDecimal filesize, String filetype) {
        this.id = id;
        this.filename = filename;
        this.md5 = md5;
        this.create_time = create_time;
        this.filesize = filesize;
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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public BigDecimal getFilesize() {
        return filesize;
    }

    public void setFilesize(BigDecimal filesize) {
        this.filesize = filesize;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", md5='" + md5 + '\'' +
                ", create_time=" + create_time +
                ", filesize=" + filesize +
                ", filetype='" + filetype + '\'' +
                '}';
    }
}
