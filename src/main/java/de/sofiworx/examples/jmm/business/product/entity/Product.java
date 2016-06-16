package de.sofiworx.examples.jmm.business.product.entity;

import de.sofiworx.examples.jmm.platform.persistence.PersistentEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Ulrich Cech
 */
@Entity(value = "products", noClassnameStored = true)
public class Product extends PersistentEntity {

    private static final long serialVersionUID = -7245514305118509921L;

    private String productNo;

    private String description;

    private BigDecimal price;

    @Transient
    private boolean selected;


    public Product() {
    }


    public String getSummary() {
        return this.productNo + ", " + this.getDescription() + ", " + this.getPrice().toString();
    }


    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(productNo, product.productNo) &&
                Objects.equals(description, product.description) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), productNo, description, price);
    }
}
