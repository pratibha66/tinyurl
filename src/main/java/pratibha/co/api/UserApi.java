package pratibha.co.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pratibha.co.repository.TinyUrlRepository;
import pratibha.co.repository.entity.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Path("/user")
public class UserApi {
    @Autowired
    private TinyUrlRepository tinyUrlRepository;

    @GET
    @Produces("application/json")
    public Response getUsers(){
        List<User> usersList = tinyUrlRepository.getAllUser();
        return Response.status(200).entity(usersList).build();
    }

}
