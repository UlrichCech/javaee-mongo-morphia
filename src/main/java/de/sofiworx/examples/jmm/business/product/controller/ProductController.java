package de.sofiworx.examples.jmm.business.product.controller;

import de.sofiworx.examples.jmm.business.product.entity.Product;
import org.mongodb.morphia.Datastore;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author Ulrich Cech
 */
@ViewScoped
public class ProductController implements Serializable {

    private static final long serialVersionUID = -4166516355114826188L;

    @Inject
    Datastore datastore;

    @Produces
    @Named
    private List<Product> allAvailableProducts;


    public ProductController() {
    }


    @PostConstruct
    private void init() {
        allAvailableProducts = datastore.find(Product.class).asList();
    }

    public List<Product> getAllAvailableProducts() {
        return allAvailableProducts;
    }
}
