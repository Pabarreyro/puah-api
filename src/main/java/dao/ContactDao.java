package dao;

import models.Contact;

import java.util.List;

public interface ContactDao {

    // CREATE
    void add(Contact contact);

    // READ
    List<Contact> getAll();
    Contact findById(int id);

    // UPDATE
    void update(Contact contact);

    // DELETE
    void deleteById(int contactId);
    void clearAll();

}
