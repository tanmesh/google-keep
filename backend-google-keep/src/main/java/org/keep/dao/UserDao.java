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
            throw new Exception("User already exists");
        }

        User user = new User();
        user.setEmailId(emailId);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        this.getDatastore().save(user);
    }

    public User getUser(String emailId) throws Exception {
        User user = this.getDatastore().createQuery(User.class).field("emailId").equal(emailId).get();

        if (user == null) {
            throw new Exception("User dosnt exist");
        }
        return user;
    }

    public void verifyUser(String emailId, String password) throws Exception {
        Query q = this.getDatastore().createQuery(User.class).field("emailId").equal(emailId);

        if (q.get() == null) {
            System.out.println("User doesn't exists");
            throw new Exception("Issue with credentials");
        }

        User user = (User) q.get();

        if (!BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("password doesn't match");
            throw new Exception("Issue with credentials");
        }
    }

    public List<Note> getAllNotes(String emailId) throws Exception {
        Query q = this.getDatastore().createQuery(User.class).field("emailId").equal(emailId);

        if (q.get() == null) {
            throw new Exception("User doesn't exists");
        }

        User user = (User) q.get();

        return user.getNoteIds();
    }

    public void addNote(String emailId, Object noteId) throws Exception {
        User user = this.getDatastore().createQuery(User.class).field("emailId").equal(emailId).get();

        if (user == null) {
            throw new Exception("User doesn't exists");
        }

        try {
            Note note = new Note();
            note.setNoteId((ObjectId) noteId);

            List<Note> noteIds = user.getNoteIds();
            noteIds.add(note);

            user.setNoteIds(noteIds);

            this.getDatastore().save(user);
        } catch (Exception e) {
            throw new Exception("unable to store in User DB");
        }
    }
}
