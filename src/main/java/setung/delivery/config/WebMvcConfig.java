package setung.delivery.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import setung.delivery.argumentresolver.OwnerIdArgumentResolver;
import setung.delivery.argumentresolver.RiderIdArgumentResolver;
import setung.delivery.argumentresolver.UserIdArgumentResolver;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserIdArgumentResolver userIdArgumentResolver;
    private final OwnerIdArgumentResolver ownerIdArgumentResolver;
    private final RiderIdArgumentResolver riderIdArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userIdArgumentResolver);
        resolvers.add(ownerIdArgumentResolver);
        resolvers.add(riderIdArgumentResolver);
    }
}
