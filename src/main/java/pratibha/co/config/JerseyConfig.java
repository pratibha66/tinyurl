package pratibha.co.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import pratibha.co.api.TinyURL;

@Component
public class JerseyConfig extends ResourceConfig
{
    public JerseyConfig()
    {
        register(TinyURL.class);
    }
}