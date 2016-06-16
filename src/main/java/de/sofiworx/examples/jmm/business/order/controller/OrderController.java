package de.sofiworx.examples.jmm.business.order.controller;

import de.sofiworx.examples.jmm.business.order.entity.Order;
import de.sofiworx.examples.jmm.business.product.controller.ProductController;
import de.sofiworx.examples.jmm.business.product.entity.Product;
import de.sofiworx.examples.jmm.business.user.entity.User;
import de.sofiworx.examples.jmm.platform.cdi.Current;
import de.sofiworx.examples.jmm.platform.persistence.BigDecimalConverter;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Ulrich Cech
 */
@ViewScoped
@Named
public class OrderController implements Serializable {

    private static final long serialVersionUID = 3341943397531831252L;

    @Inject
    Datastore datastore;

    @Inject @Current
    User currentSelectedUser;

    @Inject
    ProductController productController;

    private List<Order> allOrdersOfCurrentUser;


    public OrderController() {
    }


    @PostConstruct
    private void init() {
        final Query<Order> query = datastore.createQuery(Order.class);
        query.criteria("user").equal(currentSelectedUser);
        allOrdersOfCurrentUser = query.asList();
        productController.getAllAvailableProducts().stream().forEach(p -> p.setSelected(false));
    }


    @Produces
    @Named
    public List<Order> getAllOrdersOfCurrentUser() {
        return allOrdersOfCurrentUser;
    }

    public void placeOrder() {
        final List<Product> selectedProducts = productController.getAllAvailableProducts().stream().filter(Product::isSelected).collect(Collectors.toList());
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setUser(currentSelectedUser);
        order.setOrderedProducts(selectedProducts);
        Function<Product, BigDecimal> totalMapper = Product::getPrice;
        BigDecimal total = selectedProducts.stream().map(totalMapper).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);
        datastore.save(order);
        init();
    }

    private BigDecimal addPrice(BigDecimal value1, BigDecimal value2) {
        return value1.add(value2);
    }

}
