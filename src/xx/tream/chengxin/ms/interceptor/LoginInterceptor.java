package xx.tream.chengxin.ms.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import xx.tream.chengxin.ms.util.SystemParamUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		String actionName = request.getRequestURI().replace(
				request.getContextPath(), "");
		if ((session.getAttribute("user") == null)
				&& (!actionName.equals(SystemParamUtil.LOGIN_URL))
				&& (!actionName.equals(SystemParamUtil.LOGIN))) {
			response.getWriter().print(
					"<script>alert('状态失效，重新登录!');window.parent.location.href='"
							+ request.getContextPath() + "/login/toLogin.do';"
							+ "</script>");
			return false;
		}
		if (!actionName.equals(SystemParamUtil.LOGIN)) {
			Map<String, Object> menuAllUrlMap = (Map<String, Object>) session
					.getAttribute("menuAllUrlMap");
			if ((menuAllUrlMap != null)
					&& (menuAllUrlMap.containsKey(actionName))) {
				Map<String, Object> menuUrlMap = (Map<String, Object>) session
						.getAttribute("menuUrlMap");
				if (menuUrlMap == null) {
					response.getWriter().print(
							"<script>alert('状态失效，重新登录!');window.parent.location.href='"
									+ request.getContextPath()
									+ "/login/toLogin.do';" + "</script>");
					return false;
				}
				if (menuUrlMap.get(actionName) == null) {
					response.getWriter().print(
							"<script>alert('状态失效，重新登录!');window.parent.location.href='"
									+ request.getContextPath()
									+ "/login/toLogin.do';" + "</script>");
					return false;
				}

			}

		}

		return super.preHandle(request, response, handler);
	}
}