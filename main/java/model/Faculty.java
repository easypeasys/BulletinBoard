package model;

import java.io.Serializable;

public class Faculty implements Serializable {
    private static final long serialVersionUID = 7173890857490699120L;
    private int id;
    private String facultyName;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
