package de.sofiworx.examples.jmm.platform.persistence;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import java.io.Serializable;

/**
 * ${CARET}//TODO add class description here
 *
 * https://docs.mongodb.org/manual/tutorial/enable-authentication/
 * https://docs.mongodb.org/manual/tutorial/manage-users-and-roles/
 *
 * @author Ulrich Cech
 */
@Singleton
@Startup
public class DBConnection implements Serializable {

    private static final long serialVersionUID = 4575125557867859065L;

    private static MongoClient mongo;
    private static Datastore datastore;

    public DBConnection() {
    }

    @PostConstruct
    public void init() {
        if ((mongo == null) && (datastore == null)) {
            mongo = new MongoClient("localhost:27017");
            Morphia morphia = new Morphia();
            datastore = morphia.createDatastore(mongo, "jmm");
            morphia.getMapper().getConverters().addConverter(BigDecimalConverter.class);
        }
    }

    @Produces
    @RequestScoped
    public Datastore getDatastore() {
        return datastore;
    }

    public MongoClient getMongoClient() {
        return mongo;
    }

    public void closeConnection() {
        if (mongo != null) {
            mongo.close();
            mongo = null;
        }
        datastore = null;
    }

}
