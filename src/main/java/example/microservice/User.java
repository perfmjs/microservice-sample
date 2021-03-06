package example.microservice;

import java.io.Serializable;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-12-22
 * <p>Version: 1.0
 */
public class User implements Serializable {
    
    private static final long serialVersionUID = 4636693420018954337L;
    private Long id;
    private String name;

    public User() {
    }
    public User(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
