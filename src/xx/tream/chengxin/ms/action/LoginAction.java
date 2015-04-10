package xx.tream.chengxin.ms.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xx.tream.basepaltform.service.UserService;

@Controller
@RequestMapping({ "/login" })
public class LoginAction {

	@Autowired
	private UserService userService;

	@RequestMapping({ "/toLogin" })
	public String toLogin(HttpServletRequest request,
			HttpServletResponse response) {
		return "login";
	}

	@RequestMapping({ "/index" })
	public String index(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, String userName, String password) {
		Map<String, Object> user = this.userService.login(userName, password);
		if (user == null) {
			modelMap.put("error", "用户名或密码错误");
			return "login";
		}
		if (user.get("status") != null && user.get("status").equals("0")) {
			modelMap.put("error", "此用户已失效!");
			return "login";
		}
		HttpSession session = request.getSession();
		List<Map<String, Object>> roleList = this.userService
				.queryUserRole((Long) user.get("id"));
		if ((roleList != null) && (roleList.size() > 0)) {
			user.put("roleId",
					((Map<String, Object>) roleList.get(0)).get("roleId"));
			user.put("roleName",
					((Map<String, Object>) roleList.get(0)).get("roleName"));
		}
		session.setAttribute("user", user);
		Map<String, Object> menuCodeMap = this.userService
				.queryUserMenuCode((Long) user.get("id"));
		session.setAttribute("menuCodeMap", menuCodeMap);
		Map<String, Object> menuUrlMap = this.userService
				.queryUserMenuUrl((Long) user.get("id"));
		session.setAttribute("menuUrlMap", menuUrlMap);
		Map<String, Object> menuAllUrlMap = this.userService.queryAllURL();
		session.setAttribute("menuAllUrlMap", menuAllUrlMap);

		return "redirect:/train/toList.do";
	}

	@RequestMapping({ "/loginOut" })
	public String loginOut(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return "login";
	}
}