package setung.delivery.argumentresolver;

import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import setung.delivery.domain.user.model.User;
import setung.delivery.domain.user.service.UserLoginService;

@Component
@AllArgsConstructor
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserLoginService userLoginService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        User loginUser = userLoginService.getLoginUser();
        return loginUser.getId();
    }
}
