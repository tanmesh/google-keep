package org.keep.resource;

import com.sun.tools.corba.se.idl.constExpr.Not;
import io.dropwizard.auth.Auth;
import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeType;
import org.bson.types.ObjectId;
import org.keep.authentication.UserSession;
import org.keep.entity.Note;
import org.keep.enums.NoteType;
import org.keep.service.AccessTokenService;
import org.keep.service.INoteService;
import org.keep.service.IUserService;
import org.keep.wsRequestModel.NoteData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/note")
public class NoteResource {

    private INoteService noteService;

    private IUserService userService;

    private AccessTokenService accessTokenService;

    public NoteResource(INoteService noteService, IUserService userService, AccessTokenService accessTokenService) {
        this.noteService = noteService;
        this.userService = userService;
        this.accessTokenService = accessTokenService;
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNote(@Auth UserSession userSession, NoteData noteData) {
        Note note;
        try {
            // 1. clean the data

            System.out.println(userSession.getEmailId());
            // 2. using service to store the data
            note = new Note(noteData);
            note.setNoteId(UUID.randomUUID().toString());
            note.setEmailId(userSession.getEmailId());

            noteService.add(note);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.ACCEPTED).entity(new NoteData(note)).build();
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
            note.setNoteId(noteData.getNoteId());
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
