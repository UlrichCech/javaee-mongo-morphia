package de.sofiworx.examples.jmm.business.product.controller;

import de.sofiworx.examples.jmm.business.product.entity.Product;
import org.mongodb.morphia.Datastore;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.math.BigDecimal;

/**
 * @author Ulrich Cech
 */
@Startup
@DependsOn("DBConnection")
@Singleton
public class ProductStartupFactory {

    @Inject
    Datastore datastore;

    @PostConstruct
    private void init() {
        if (datastore.find(Product.class).countAll() == 0) {
            for (int i = 1; i < 12; i++) {
                Product product = new Product();
                product.setProductNo(Integer.toString(i));
                product.setDescription("Description of article with no. " + i);
                product.setPrice(new BigDecimal(i));
                datastore.save(product);
            }
        }
    }

}
