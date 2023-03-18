package com.example.a1_postings.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "postings")
@JsonIgnoreProperties({"items"})
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mat_doc")
    private String matDoc;

    @Column(name = "doc_date")
    private LocalDate docDate;

    @Column(name = "pstng_date")
    private LocalDate pstngDate;

    @Column(name = "user_name")
    private String userName;

    @ManyToOne
    @JoinColumn(name = "login_id")
    private Login login;

    @Column(name = "authorized")
    private boolean authorized;

    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    private List<Item> items;

    public Posting() {
    }

    public Posting(String matDoc, LocalDate docDate, LocalDate pstngDate, String userName, Login login, boolean authorized) {
        this.matDoc = matDoc;
        this.docDate = docDate;
        this.pstngDate = pstngDate;
        this.userName = userName;
        this.login = login;
        this.authorized = authorized;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatDoc() {
        return matDoc;
    }

    public void setMatDoc(String matDoc) {
        this.matDoc = matDoc;
    }

    public LocalDate getDocDate() {
        return docDate;
    }

    public void setDocDate(LocalDate docDate) {
        this.docDate = docDate;
    }

    public LocalDate getPstngDate() {
        return pstngDate;
    }

    public void setPstngDate(LocalDate pstngDate) {
        this.pstngDate = pstngDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

