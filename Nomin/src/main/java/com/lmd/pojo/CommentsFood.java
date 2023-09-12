/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nome
 */
@Entity
@Table(name = "comments_food")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CommentsFood.findAll", query = "SELECT c FROM CommentsFood c"),
    @NamedQuery(name = "CommentsFood.findById", query = "SELECT c FROM CommentsFood c WHERE c.id = :id"),
    @NamedQuery(name = "CommentsFood.findByContent", query = "SELECT c FROM CommentsFood c WHERE c.content = :content"),
    @NamedQuery(name = "CommentsFood.findByRate", query = "SELECT c FROM CommentsFood c WHERE c.rate = :rate")})
public class CommentsFood implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 255)
    @Column(name = "content")
    private String content;
    
    @Column(name = "rate")
    private Integer rate;
    
    @JoinColumn(name = "foods_id", referencedColumnName = "id")
    @ManyToOne
//    @JsonIgnore
    private Food foodsId;
    
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne
    private User usersId;

    public CommentsFood() {
    }

    public CommentsFood(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Food getFoodsId() {
        return foodsId;
    }

    public void setFoodsId(Food foodsId) {
        this.foodsId = foodsId;
    }

    public User getUsersId() {
        return usersId;
    }

    public void setUsersId(User usersId) {
        this.usersId = usersId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentsFood)) {
            return false;
        }
        CommentsFood other = (CommentsFood) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lmd.pojo.CommentsFood[ id=" + id + " ]";
    }
    
}
