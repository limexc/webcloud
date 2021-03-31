package cn.limexc.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用于多文件上传
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-01 0:58
 * @since jdk1.8.0
 */
public class MultiFileDomain {
    private List<String> description;
    private List<MultipartFile> myFile;

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<MultipartFile> getMyFile() {
        return myFile;
    }

    public void setMyFile(List<MultipartFile> myFile) {
        this.myFile = myFile;
    }
}
