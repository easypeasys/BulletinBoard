package model;

import java.io.Serializable;
import java.util.Date;

public class Tips implements Serializable {
    private static final long serialVersionUID = -8389540962775363835L;
    private int id;
    private int userId;
    private String content;
    private int messageId;
    private String status;
    private Date CreateTime;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tips{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", messageId=" + messageId +
                ", status='" + status + '\'' +
                ", CreateTime=" + CreateTime +
                ", type=" + type +
                '}';
    }
}
