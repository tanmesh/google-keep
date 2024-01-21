package org.keep.dao;

import org.bson.types.ObjectId;
import org.keep.entity.Note;
import org.keep.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class UserDao extends BasicDAO<User, String> {
    public UserDao(Datastore ds) {
        super(ds);
    }

    public void addUser(String emailId, String password) throws Exception {
        Query q = this.getDatastore().createQuery(User.class).field("emailId").equal(emailId);
        if (q.get() != null) {
            throw new Exception("Use new EmailId");
        }

        User user = new User();
        user.setEmailId(emailId);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        this.getDatastore().save(user);
    }

    public User getUser(String emailId) throws Exception {
        User user = this.getDatastore().createQuery(User.class).field("emailId").equal(emailId).get();

        if (user == null) {
            throw new Exception("User doesn't exist");
        }
        return user;
    }
}
