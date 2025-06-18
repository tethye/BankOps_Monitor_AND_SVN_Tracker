package com.example.myproject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "svnrepository")
public class SvnRepository {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "svn_repo_seq")
    @SequenceGenerator(
            name = "svn_repo_seq",
            sequenceName = "svn_repo_sequence",
            allocationSize = 1
    )
    Integer id;
    String repo_name;
    String repo_url;
    String username;
    String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public String getRepo_url() {
        return repo_url;
    }

    public void setRepo_url(String repo_url) {
        this.repo_url = repo_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SvnRepository(Integer id, String repo_name, String repo_url, String username, String password) {
        this.id = id;
        this.repo_name = repo_name;
        this.repo_url = repo_url;
        this.username = username;
        this.password = password;
    }

    public SvnRepository() {
    }

    @Override
    public String toString() {
        return "SvnRepository{" +
                "id=" + id +
                ", repo_name='" + repo_name + '\'' +
                ", repo_url='" + repo_url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}