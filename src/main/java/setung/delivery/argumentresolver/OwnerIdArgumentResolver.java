package setung.delivery.argumentresolver;

import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import setung.delivery.domain.owner.Owner;
import setung.delivery.service.owner.OwnerLoginService;

@Component
@AllArgsConstructor
public class OwnerIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final OwnerLoginService ownerLoginService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginOwnerId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Owner loginOwner = ownerLoginService.getLoginOwner();

        if(loginOwner == null)
            throw new RuntimeException("로그인이 필요합니다.");

        return loginOwner.getId();
    }
}
