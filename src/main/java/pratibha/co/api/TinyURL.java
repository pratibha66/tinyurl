package pratibha.co.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pratibha.co.repository.TinyUrlRepository;
import pratibha.co.repository.entity.Url;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@RestController
@Path("/tinyurl")
public class TinyURL {
    @Autowired
    private TinyUrlRepository tinyUrlRepository;

    @GET
    @Produces("application/json")
    public Response getAllUrls(@QueryParam("smallUrl")final String smallUrl){
        return Response.status(200).entity(tinyUrlRepository.getAllUrls()).build();
    }
    @DELETE
    @Produces("application/json")
    @Path("{userId}/{shortUrl}")
    public Response deleteUrl(@PathParam("userId") String userId, @PathParam("shortUrl") String shortUrl){
        tinyUrlRepository.deleteUrl(userId, shortUrl);
        return Response.status(Response.Status.ACCEPTED).build();
    }
    @POST
    @Produces("application/json")
    public Response createUrlMapping(Url url){
        final String res = tinyUrlRepository.createUrlMapping(url);
        Map<String, String> map = new HashMap<>();
        map.put("tinyUrl", res);
        return Response.status(Response.Status.CREATED).header("Location", res).entity(map).build();
    }
    @GET
    @Path("{shorturl}")
    public Response redirect(@PathParam("shorturl") String shorturl){
        String origUrl = tinyUrlRepository.getOriginalUrl(shorturl);
        URI uri = UriBuilder.fromUri(origUrl).build();
        return Response.temporaryRedirect(uri).build();
    }

}
