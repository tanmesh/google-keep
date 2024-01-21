package org.keep.resource;

import io.dropwizard.auth.Auth;
import org.keep.authentication.UserSession;
import org.keep.entity.Note;
import org.keep.service.AccessTokenService;
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

    private AccessTokenService accessTokenService;

    public UserResource(IUserService userService, INoteService noteService, AccessTokenService accessTokenService) {
        this.userService = userService;
        this.noteService = noteService;
        this.accessTokenService = accessTokenService;
    }

    @POST
    @Path("signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(UserData userData) {
        try {
            String emailId = userData.getEmailId();
            if (emailId == null || emailId.isEmpty()) {
                throw new Exception("EmailId cannot be null");
            }

            String password = userData.getPassword();
            if (password == null || password.isEmpty()) {
                throw new Exception("Password cannot be null");
            }

            userService.signup(emailId, password);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserData userData) {
        UserSession userSession;
        try {
            String emailId = userData.getEmailId();
            if (emailId == null || emailId.isEmpty()) {
                throw new Exception("EmailId cannot be null");
            }

            String password = userData.getPassword();
            if (password == null || password.isEmpty()) {
                throw new Exception("Password cannot be null");
            }

            userSession = userService.login(emailId, password);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
        return Response.status(Response.Status.ACCEPTED).entity(userSession).build();
    }

    @GET
    @Path("getAllNotes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNote(@Auth UserSession userSession) {
        List<NoteData> outNotes;
        try {
            outNotes = userService.getAllNotes(userSession.getEmailId());
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.ACCEPTED).entity(outNotes).build();
    }
}
