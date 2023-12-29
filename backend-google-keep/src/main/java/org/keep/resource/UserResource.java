package org.keep.resource;

import org.keep.entity.Note;
import org.keep.service.INoteService;
import org.keep.service.IUserService;
import org.keep.wsRequestModel.NoteData;
import org.keep.wsRequestModel.UserData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
public class UserResource {
    private IUserService userService;
    private INoteService noteService;

    public UserResource(IUserService userService, INoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @POST
    @Path("signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(UserData userData) {
        try {
            String emailId = userData.getEmailId();
            if (emailId == null || emailId.isEmpty()) {
                throw new Exception("Issue with credentials");
            }

            String password = userData.getPassword();
            if (password == null || password.isEmpty()) {
                throw new Exception("Issue with credentials");
            }

            userService.signup(emailId, password);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserData userData) {
        try {
            String emailId = userData.getEmailId();
            if (emailId == null || emailId.isEmpty()) {
                throw new Exception("email cannot be null");
            }

            String password = userData.getPassword();
            if (password == null || password.isEmpty()) {
                throw new Exception("password cannot be null");
            }

            userService.login(emailId, password);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Path("getAllNotes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNote(UserData userData) {
        UserData outUserData = new UserData();
        try {
            List<NoteData> notes = userService.getAllNotes(userData.getEmailId());
            outUserData.setNotes(notes);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.ACCEPTED).entity(outUserData).build();
    }
}
