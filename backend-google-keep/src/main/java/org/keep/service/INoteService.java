package org.keep.service;

import org.bson.types.ObjectId;
import org.keep.entity.Note;

import java.util.List;

public interface INoteService {
    String add(Note note); // adding a note

    void delete(String noteId); // delete a note

    void edit(Note note) throws Exception; // edit a note
}
