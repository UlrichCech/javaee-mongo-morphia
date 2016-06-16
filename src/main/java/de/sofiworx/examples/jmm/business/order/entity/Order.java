package de.sofiworx.examples.jmm.business.order.entity;

import de.sofiworx.examples.jmm.business.product.entity.Product;
import de.sofiworx.examples.jmm.business.user.entity.User;
import de.sofiworx.examples.jmm.platform.persistence.PersistentEntity;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Ulrich Cech
 */
@Entity(value = "orders", noClassnameStored = true)
public class Order extends PersistentEntity {

    private static final long serialVersionUID = 7958903679851324189L;

    private Date orderDate;

    private BigDecimal totalAmount;

    @Reference
    private User user;

    @Embedded
    private List<Product> orderedProducts;


    public Order() {
    }


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(totalAmount, order.totalAmount) &&
                Objects.equals(user, order.user) &&
                Objects.equals(orderedProducts, order.orderedProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderDate, totalAmount, user, orderedProducts);
    }
}
