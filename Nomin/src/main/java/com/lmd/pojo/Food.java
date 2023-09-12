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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nome
 */
@Entity
@Table(name = "food")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Food.findAll", query = "SELECT f FROM Food f"),
    @NamedQuery(name = "Food.findById", query = "SELECT f FROM Food f WHERE f.id = :id"),
    @NamedQuery(name = "Food.findByName", query = "SELECT f FROM Food f WHERE f.name = :name"),
    @NamedQuery(name = "Food.findByPrice", query = "SELECT f FROM Food f WHERE f.price = :price"),
    @NamedQuery(name = "Food.findByFoodType", query = "SELECT f FROM Food f WHERE f.foodType = :foodType"),
    @NamedQuery(name = "Food.findByAvailable", query = "SELECT f FROM Food f WHERE f.available = :available"),
    @NamedQuery(name = "Food.findByImage", query = "SELECT f FROM Food f WHERE f.image = :image")})
public class Food implements Serializable {

    @Size(max = 45)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @Size(max = 45)
    @Column(name = "food_type")
    private String foodType;
    @Size(max = 255)
    @Column(name = "image")
    private String image;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    
    @Column(name = "available")
    private Integer available;

    @OneToMany(mappedBy = "foodsId")
    @JsonIgnore
    private Set<CommentsFood> commentsFoodSet;
    
    @OneToMany(mappedBy = "foodsId")
    @JsonIgnore
    private Set<OrderDetail> orderDetailSet;
    
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @ManyToOne// 09 09 2023 1:56
    public Restaurant restaurantId;
    
    @Transient
    private MultipartFile file;

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    public Food() {
    }

    public Food(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }


    @XmlTransient
    public Set<CommentsFood> getCommentsFoodSet() {
        return commentsFoodSet;
    }

    public void setCommentsFoodSet(Set<CommentsFood> commentsFoodSet) {
        this.commentsFoodSet = commentsFoodSet;
    }

    @XmlTransient
    public Set<OrderDetail> getOrderDetailSet() {
        return orderDetailSet;
    }

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet) {
        this.orderDetailSet = orderDetailSet;
    }

    public Restaurant getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Restaurant restaurantId) {
        this.restaurantId = restaurantId;
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
        if (!(object instanceof Food)) {
            return false;
        }
        Food other = (Food) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lmd.pojo.Food[ id=" + id + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
