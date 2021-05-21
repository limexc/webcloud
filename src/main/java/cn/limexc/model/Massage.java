package cn.limexc.model;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-05-18 13:40
 * @since jdk1.8.0
 */
public class Massage {
    private Integer id;
    private Integer uid;
    private String title;
    private String info;
    private String size;
    private String create_time;
    private String reply;
    private Integer status;

    public Massage() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Massage{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", size='" + size + '\'' +
                ", create_time='" + create_time + '\'' +
                ", reply='" + reply + '\'' +
                ", status=" + status +
                '}';
    }
}
