package org.keep;

import com.mongodb.Mongo;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.keep.authentication.AccessTokenAuthenticator;
import org.keep.authentication.AccessTokenSecurityProvider;
import org.keep.dao.NoteDao;
import org.keep.dao.UserDao;
import org.keep.resource.NoteResource;
import org.keep.resource.UserResource;
import org.keep.service.*;
import org.keep.utils.MongoUtils;
import org.mongodb.morphia.Datastore;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class App extends Application<Configurations> {
    public static void main(String[] args) {
        App app = new App();
        try {
            app.run(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(Bootstrap<Configurations> bootstrap) {

    }

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_HEADERS_HEADER, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_METHODS_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,x-access-token");
        filter.setInitParameter("allowCredentials", "true");
    }

    @Override
    public void run(Configurations configurations, Environment environment) throws Exception {
        Datastore ds = MongoUtils.createDatastore(configurations.getDBConfig());

        AccessTokenService accessTokenService = new RedisAccessTokenService();

        // Note layer
        NoteDao noteDao = new NoteDao(ds);
        INoteService noteService = new NoteService(noteDao);

        // User layer
        UserDao userDao = new UserDao(ds);
        IUserService userService = new UserService(userDao, noteDao, accessTokenService);

        NoteResource noteResource = new NoteResource(noteService, userService, accessTokenService);
        UserResource userResource = new UserResource(userService, noteService, accessTokenService);


        AccessTokenAuthenticator accessTokenAuthenticator = new AccessTokenAuthenticator(accessTokenService);

        environment.jersey().register(noteResource);
        environment.jersey().register(userResource);
        environment.jersey().register(new AccessTokenSecurityProvider<>(accessTokenAuthenticator));
        configureCors(environment);
    }
}
