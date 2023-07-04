package model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private static final long serialVersionUID = 1812908819029669642L;
    private int id;
    private String questioner;
    private int questionerId;
    private String courseName;
    private int courseId;
    private String content;
    private String title;
    private String attachForStu;
    private Date createTime;
    private String status; //1.老师未读 2.老师已读 3.老师回复学生未读 4.老师回复学生已读
    private String attachForTea;
    private String statusMessage;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestioner() {
        return questioner;
    }

    public void setQuestioner(String questioner) {
        this.questioner = questioner;
    }

    public int getQuestionerId() {
        return questionerId;
    }

    public void setQuestionerId(int questionerId) {
        this.questionerId = questionerId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttachForStu() {
        return attachForStu;
    }

    public void setAttachForStu(String attachForStu) {
        this.attachForStu = attachForStu;
    }

    public String getAttachForTea() {
        return attachForTea;
    }

    public void setAttachForTea(String attachForTea) {
        this.attachForTea = attachForTea;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
