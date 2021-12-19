package setung.delivery.argumentresolver;

import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import setung.delivery.domain.rider.model.Rider;
import setung.delivery.domain.rider.service.RiderLoginService;

@Component
@AllArgsConstructor
public class RiderIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final RiderLoginService riderLoginService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginRiderId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Rider loginRider = riderLoginService.getLoginRider();
        return loginRider.getId();
    }
}
