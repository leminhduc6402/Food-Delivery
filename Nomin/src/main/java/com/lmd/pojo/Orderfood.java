/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmd.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nome
 */
@Entity
@Table(name = "orderfood")
@NamedQueries({
    @NamedQuery(name = "Orderfood.findAll", query = "SELECT o FROM Orderfood o"),
    @NamedQuery(name = "Orderfood.findById", query = "SELECT o FROM Orderfood o WHERE o.id = :id"),
    @NamedQuery(name = "Orderfood.findByStatus", query = "SELECT o FROM Orderfood o WHERE o.status = :status"),
    @NamedQuery(name = "Orderfood.findByTotalAmount", query = "SELECT o FROM Orderfood o WHERE o.totalAmount = :totalAmount"),
    @NamedQuery(name = "Orderfood.findByPaymentMethod", query = "SELECT o FROM Orderfood o WHERE o.paymentMethod = :paymentMethod"),
    @NamedQuery(name = "Orderfood.findByOrderDate", query = "SELECT o FROM Orderfood o WHERE o.orderDate = :orderDate")})
public class Orderfood implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "status")
    private Integer status;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_amount")
    private Double totalAmount;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @OneToMany(mappedBy = "ordersId")
    @JsonIgnore
    private Set<Coupon> couponSet;
    @OneToMany(mappedBy = "ordersId")
    private Set<OrderDetail> orderDetailSet;
    @JoinColumn(name = "restaurants_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnore
    private Restaurant restaurantsId;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne
    private User usersId;

    public Orderfood() {
    }

    public Orderfood(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @XmlTransient
    public Set<Coupon> getCouponSet() {
        return couponSet;
    }

    public void setCouponSet(Set<Coupon> couponSet) {
        this.couponSet = couponSet;
    }

    public Set<OrderDetail> getOrderDetailSet() {
        return orderDetailSet;
    }

    public void setOrderDetailSet(Set<OrderDetail> orderDetailSet) {
        this.orderDetailSet = orderDetailSet;
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
        if (!(object instanceof Orderfood)) {
            return false;
        }
        Orderfood other = (Orderfood) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.lmd.pojo.Orderfood[ id=" + id + " ]";
    }
    
}
