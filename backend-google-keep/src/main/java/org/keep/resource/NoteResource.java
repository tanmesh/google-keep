package org.keep.resource;

import com.sun.tools.corba.se.idl.constExpr.Not;
import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeType;
import org.bson.types.ObjectId;
import org.keep.entity.Note;
import org.keep.enums.NoteType;
import org.keep.service.INoteService;
import org.keep.service.IUserService;
import org.keep.wsRequestModel.NoteData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/note")
public class NoteResource {

    private INoteService noteService;

    private IUserService userService;

    public NoteResource(INoteService noteService, IUserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNote(NoteData noteData) {
        try {
            // 1. clean the data

            // 2. using service to store the data
            Note note = new Note();
            note.setTitle(noteData.getTitle());
            note.setNoteContent(noteData.getNoteContent());
            note.setEmailId(noteData.getEmailId());

            Object noteId = noteService.add(note);
            System.out.println(noteId);

            userService.addNote(noteData.getEmailId(), noteId);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.ACCEPTED).entity(noteData).build();
    }

    @DELETE
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteNote(NoteData noteData) {
        try {
            noteService.delete(noteData.getNoteId());
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editNote(NoteData noteData) {
        try {
            Note note = new Note();
            note.setNoteId(new ObjectId(noteData.getNoteId()));
            note.setTitle(noteData.getTitle());
            note.setNoteContent(noteData.getNoteContent());
            note.setEmailId(noteData.getEmailId());
            noteService.edit(note);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.ACCEPTED).entity(noteData).build();
    }
}
