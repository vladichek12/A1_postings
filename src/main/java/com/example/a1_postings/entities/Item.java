package com.example.a1_postings.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = true)
    private Posting posting;

    @Column(name = "material_description")
    private String materialDescription;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "bun")
    private String bun;

    @Column(name = "amount_lc")
    private Double amountLc;

    @Column(name = "crcy")
    private String crcy;


    public Item() {
    }

    public Item(Posting posting, String materialDescription, Integer quantity, String bun, Double amountLc, String crcy) {
        this.posting = posting;
        this.materialDescription = materialDescription;
        this.quantity = quantity;
        this.bun = bun;
        this.amountLc = amountLc;
        this.crcy = crcy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Posting getPosting() {
        return posting;
    }

    public void setPosting(Posting posting) {
        this.posting = posting;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBun() {
        return bun;
    }

    public void setBun(String bun) {
        this.bun = bun;
    }

    public Double getAmountLc() {
        return amountLc;
    }

    public void setAmountLc(Double amountLc) {
        this.amountLc = amountLc;
    }

    public String getCrcy() {
        return crcy;
    }

    public void setCrcy(String crcy) {
        this.crcy = crcy;
    }
}

