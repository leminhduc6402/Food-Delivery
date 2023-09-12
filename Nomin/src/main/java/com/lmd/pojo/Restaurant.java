/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nome
 */
@Entity
@Table(name = "restaurant")
@NamedQueries({
    @NamedQuery(name = "Restaurant.findAll", query = "SELECT r FROM Restaurant r"),
    @NamedQuery(name = "Restaurant.findById", query = "SELECT r FROM Restaurant r WHERE r.id = :id"),
    @NamedQuery(name = "Restaurant.findByName", query = "SELECT r FROM Restaurant r WHERE r.name = :name"),
    @NamedQuery(name = "Restaurant.findByDeliveryFee", query = "SELECT r FROM Restaurant r WHERE r.deliveryFee = :deliveryFee"),
    @NamedQuery(name = "Restaurant.findByStatus", query = "SELECT r FROM Restaurant r WHERE r.status = :status")})
public class Restaurant implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "delivery_fee")
    private double deliveryFee;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "restaurantsId")
    @JsonIgnore
    private Set<CommentRestaurant> commentRestaurantSet;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private User userId;

    @OneToMany(mappedBy = "restaurantsId")
    @JsonIgnore
    private Set<Follow> followSet;

    @OneToMany(mappedBy = "restaurantsId")
    @JsonIgnore
    private Set<Orderfood> orderfoodSet;

    @OneToMany(mappedBy = "restaurantId")
    @JsonIgnore
    private Set<Food> foodSet;

    public Restaurant() {
    }

    public Restaurant(Integer id) {
        this.id = id;
    }

    public Restaurant(Integer id, String name, double deliveryFee) {
        this.id = id;
        this.name = name;
        this.deliveryFee = deliveryFee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public Set<CommentRestaurant> getCommentRestaurantSet() {
        return commentRestaurantSet;
    }

    public void setCommentRestaurantSet(Set<CommentRestaurant> commentRestaurantSet) {
        this.commentRestaurantSet = commentRestaurantSet;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @XmlTransient
    public Set<Follow> getFollowSet() {
        return followSet;
    }

    public void setFollowSet(Set<Follow> followSet) {
        this.followSet = followSet;
    }

    @XmlTransient
    public Set<Orderfood> getOrderfoodSet() {
        return orderfoodSet;
    }

    public void setOrderfoodSet(Set<Orderfood> orderfoodSet) {
        this.orderfoodSet = orderfoodSet;
    }

    @XmlTransient
    public Set<Food> getFoodSet() {
        return foodSet;
    }

    public void setFoodSet(Set<Food> foodSet) {
        this.foodSet = foodSet;
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
        if (!(object instanceof Restaurant)) {
            return false;
        }
        Restaurant other = (Restaurant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lmd.pojo.Restaurant[ id=" + id + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
