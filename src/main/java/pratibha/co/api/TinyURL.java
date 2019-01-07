package pratibha.co.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pratibha.co.repository.TinyUrlRepository;
import pratibha.co.repository.entity.Url;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;


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
    public Response deleteUrl(Url url){
        tinyUrlRepository.deleteUrl(url);
        return Response.status(Response.Status.ACCEPTED).build();
    }
    @POST
    @Produces("application/json")
    public Response createUrlMapping(Url url){
        tinyUrlRepository.createUrlMapping(url);
        return Response.status(Response.Status.CREATED).build();
    }
    @GET
    @Path("{shorturl}")
    public Response redirect(@PathParam("shorturl") String shorturl){
        String origUrl = tinyUrlRepository.getOriginalUrl(shorturl);
        URI uri = UriBuilder.fromUri(origUrl).build();
        return Response.temporaryRedirect(uri).build();
    }

}
