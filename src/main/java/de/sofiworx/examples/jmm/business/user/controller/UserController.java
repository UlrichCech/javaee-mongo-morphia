package de.sofiworx.examples.jmm.business.user.controller;

import de.sofiworx.examples.jmm.business.user.entity.User;
import de.sofiworx.examples.jmm.platform.cdi.Current;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author Ulrich Cech
 */
@SessionScoped
@Named
public class UserController implements Serializable {

    private static final long serialVersionUID = -6281169311696802773L;

    @Inject
    @RequestScoped
    private Datastore datastore;

    private User userToAdd;

    private List<User> usersToShow;

    private String searchString;

    private User currentSelectedUser;


    public UserController() {
    }

    @PostConstruct
    private void init() {
        showAllUsers();
    }

    public void setCurrentSelectedUser(User currentSelectedUser) {
        this.currentSelectedUser = currentSelectedUser;
    }

    @Produces @Current
    @Named
    @Dependent
    public User getCurrentSelectedUser() {
        return currentSelectedUser;
    }

    public long getCurrentAmountOfUsers() {
        return datastore.find(User.class).getCollection().getCount();
    }

    private void showAllUsers() {
        usersToShow = datastore.createQuery(User.class).order("lastname,firstname").asList();
    }

    public void prepareNewUser() {
        userToAdd = new User();
    }

    public String addNewUser() {
        datastore.save(userToAdd);
        userToAdd = null;
        showAllUsers();
        return null;
    }

    public void removeUser(User userToDelete) {
        datastore.delete(userToDelete);
        showAllUsers();
    }

    public void searchUsers(String stringToSearch) {
        final Query<User> query = datastore.createQuery(User.class);
        if (stringToSearch != null) {
            query.or(query.criteria("firstname").contains(stringToSearch), query.criteria("lastname").contains(stringToSearch));
        }
        usersToShow = query.asList();
    }

    @Produces
    @Named
    public User getUserToAdd() {
        return userToAdd;
    }

    public List<User> getUsersToShow() {
        return usersToShow;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
