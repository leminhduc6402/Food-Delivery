/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.pojo;

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
@Table(name = "comment_restaurant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CommentRestaurant.findAll", query = "SELECT c FROM CommentRestaurant c"),
    @NamedQuery(name = "CommentRestaurant.findById", query = "SELECT c FROM CommentRestaurant c WHERE c.id = :id"),
    @NamedQuery(name = "CommentRestaurant.findByContent", query = "SELECT c FROM CommentRestaurant c WHERE c.content = :content"),
    @NamedQuery(name = "CommentRestaurant.findByRate", query = "SELECT c FROM CommentRestaurant c WHERE c.rate = :rate")})
public class CommentRestaurant implements Serializable {

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
    @JoinColumn(name = "restaurants_id", referencedColumnName = "id")
    @ManyToOne
    private Restaurant restaurantsId;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne
    private User usersId;

    public CommentRestaurant() {
    }

    public CommentRestaurant(Integer id) {
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

    public Restaurant getRestaurantsId() {
        return restaurantsId;
    }

    public void setRestaurantsId(Restaurant restaurantsId) {
        this.restaurantsId = restaurantsId;
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
        if (!(object instanceof CommentRestaurant)) {
            return false;
        }
        CommentRestaurant other = (CommentRestaurant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lmd.pojo.CommentRestaurant[ id=" + id + " ]";
    }
    
}
