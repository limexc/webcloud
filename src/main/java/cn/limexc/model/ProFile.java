package cn.limexc.model;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-21 23:34
 * @since jdk1.8.0
 */
public class ProFile {
    Integer id;
    String name;
    String path;
    String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ProFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
