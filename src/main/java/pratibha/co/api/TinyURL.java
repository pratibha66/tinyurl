package pratibha.co.api;

import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Service
@Path("/tinyurl")
public class TinyURL {

    @GET
    @Produces("application/json")
    public Response getOriginalUrl(@QueryParam("smallUrl")final String smallUrl) {
        Map<String, String> map = new HashMap<>();
        map.put(smallUrl, "www.google.com/a/b/e/f/g/h?fkjdklfjdlkjfl" );


        return Response.status(200).entity(map).build();
    }


}
