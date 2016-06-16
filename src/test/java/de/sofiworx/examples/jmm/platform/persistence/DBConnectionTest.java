package de.sofiworx.examples.jmm.platform.persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import de.sofiworx.examples.jmm.business.user.entity.User;
import org.bson.BsonDocument;
import org.bson.Document;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Ulrich Cech
 */
public class DBConnectionTest {

    @Test
    public void establishDBConnection() {
        DBConnection dbConnection = new DBConnection();
        dbConnection.init();
        dbConnection.checkConnection();
        dbConnection.closeConnection();
    }

    @Test
    public void persistSimpleDocument() {
        DBConnection dbConnection = new DBConnection();
        dbConnection.init();
        final MongoClient mongoClient = dbConnection.getMongoClient();
        final MongoCollection<Document> testdataCollection = mongoClient.getDatabase("jmm").getCollection("testdata");
        try {
            Document storeDocument = new Document();
            storeDocument.put("field1", "value1");
            storeDocument.put("field2", "value2");
            storeDocument.put("field3", "value3");
            testdataCollection.insertOne(storeDocument);
            assertThat(testdataCollection.count(), is(1L));
            final Document retrievedDocument = mongoClient.getDatabase("jmm").getCollection("testdata").find().first();
            assertThat(retrievedDocument.containsKey("field2"), is(true));
            System.out.println(retrievedDocument.toJson());
            testdataCollection.deleteOne(retrievedDocument);
            assertThat(testdataCollection.count(), is(0L));
        } finally {
            testdataCollection.deleteMany(new BsonDocument());
        }
    }


    @Test
    public void persistEntityWithMorphia() {
        DBConnection dbConnection = new DBConnection();
        User testUser = new User();
        try {
            dbConnection.init();
            testUser.setFirstname("Max");
            testUser.setLastname("Mustermann");
            testUser.setBirthdate(new GregorianCalendar(1974, 10, 12, 0, 0).getTime());
            testUser.setEmail("max.mustermann@example.com");
            dbConnection.getDatastore().save(testUser);
            assertThat(testUser.getId(), notNullValue());
            assertThat(testUser.getCreated(), notNullValue());
            assertThat(testUser.getLastUpdate(), notNullValue());
            final User retrievedUser = dbConnection.getDatastore().find(User.class).filter("firstname", "Max").get();
            assertThat(retrievedUser.getLastname(), is("Mustermann"));
            assertThat(testUser.getId(), is(retrievedUser.getId()));
        } finally {
//            dbConnection.getDatastore().delete(testUser);
        }
    }

    @Test
    public void showUsageOfIndexes() {
        DBConnection dbConnection = new DBConnection();
        dbConnection.init();
        final Datastore datastore = dbConnection.getDatastore();
        for (int i = 0; i < 1000000; i++) {
            User user = new User();
            user.setFirstname("Firstname" + i);
            user.setLastname("Lastname" + i);
            datastore.save(user);
        }
        long start = System.currentTimeMillis();
        final User user = datastore.find(User.class).filter("firstname", "Firstname900000").get();
        System.out.println("User found in " + (System.currentTimeMillis() - start) + "ms");
        assertThat(user, notNullValue());
    }


}