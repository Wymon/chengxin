package xx.tream.chengxin.ms.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import xx.tream.basepaltform.model.FormMap;
import xx.tream.basepaltform.model.User;
import xx.tream.basepaltform.model.UserRole;
import xx.tream.basepaltform.service.RoleService;
import xx.tream.basepaltform.service.UserRoleService;
import xx.tream.basepaltform.service.UserService;
import xx.tream.basepaltform.util.BeanUtil;
import xx.tream.basepaltform.util.Md5EncryptUtil;
import xx.tream.chengxin.ms.model.Param;
import xx.tream.chengxin.ms.model.PasswordBack;
import xx.tream.chengxin.ms.model.UserBack;
import xx.tream.chengxin.ms.service.ParamService;
import xx.tream.chengxin.ms.service.PasswordBackService;
import xx.tream.chengxin.ms.service.UserBackService;
import xx.tream.chengxin.ms.util.EmailUtil;
import xx.tream.chengxin.ms.util.ParamUtil;

@Controller
@RequestMapping({ "/user" })
public class UserAction {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ParamService paramService;
	@Autowired
	private UserBackService userBackService;
	@Autowired
	private PasswordBackService passwordBackService;
	String[] userBack = {"userName","realName","roleId","email","canEmail"};
	@RequestMapping({ "/toAuditDelete" })
	public String toAuditDelete(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,FormMap formMap,Long id) {
		Map<String, Object> userMap = this.userService.find(id);
		modelMap.put("userMap", userMap);
		return "user/auditDelete";
	}
	@RequestMapping({ "/auditDelete" })
	public String auditDelete(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,FormMap formMap,Long id) {
		Map<String, Object> userMap = this.userService.find(id);
		Map<String,Object> auditMap = formMap.getFormMap();
		User user = new User();
		BeanUtil.copyBeanProperties(user, userMap);
		if(auditMap.get("updateStatus")!=null&&auditMap.get("updateStatus").equals("1")){//表示通过
			user.setStatus("0");
		}else{
			user.setCreateUser(0);
			user.setStatus("1");
		}
		this.userService.update(user);
		modelMap.put("msg", "succeed");
		return "user/auditDelete";
	}
	/**
	 * 进入审核增加用户页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param formMap
	 * @return
	 */
	@RequestMapping({ "/toAuditAddUser" })
	public String toAuditAddUser(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,FormMap formMap,Long id) {
		Map<String, Object> userMap = this.userService.find(id);
		modelMap.put("userMap", userMap);
		return "user/auditAddUser";
	}
	@RequestMapping({ "/auditAddUser" })
	public String auditAddUser(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,FormMap formMap,Long id) {
		Map<String, Object> userMap = this.userService.find(id);
		Map<String,Object> auditMap = formMap.getFormMap();
		User user = new User();
		BeanUtil.copyBeanProperties(user, userMap);
		if(auditMap.get("updateStatus")!=null&&auditMap.get("updateStatus").equals("1")){//表示通过
			user.setStatus("1");
		}else{
			user.setStatus("0");
		}
		this.userService.update(user);
		modelMap.put("msg", "succeed");
		return "user/auditAddUser";
	}
	@RequestMapping({ "/saveUserBack" })
	public String saveUserBack(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,FormMap formMap){
		Map<String,Object> userMap = formMap.getFormMap();
		Long backId = Long.parseLong(userMap.get("backId")+"");
		Map<String,Object> m = userBackService.findBackByBackId(backId);
		if(m==null){//直接添加 back记录
			//判断是否存在相同用户名的记录
			Map<String,Object> u = userService.findByUserName((String)userMap.get("userName"));
			if(u!=null&&!u.get("id").equals(backId)){
				modelMap.put("msg", "已存在相同的登录名,请修改!");
			}else{
				UserBack back = new UserBack();
				BeanUtil.copyBeanProperties(back, userMap);
				back.setCreateUser(ParamUtil.getUserId(request));
				back.setCreateTime(new Date());
				back.setUpdateStatus("0");
				back.setCreateUserName(ParamUtil.getRealName(request));
				this.userBackService.save(back);
				modelMap.put("msg", "succeed");
			}
			
		}else{
			modelMap.put("msg", "已存在修改记录了,请进行审核操作");
		}
		return "user/update";
	}
	/**
	 * 修改审核
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/toUpdateAudit" })
	public String toUpdateAudit(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,long id){
		Map<String,Object> backMap = this.userBackService.find(id);
		if(backMap!=null){
			if(backMap.get("updateStatus")!=null&&!backMap.get("updateStatus").equals("0")){
				modelMap.put("msg", "状态已变更,不能进行审核操作,请刷新再操作");
			}else{
				modelMap.put("backMap", backMap);
				Map<String,Object> oldUser = this.userService.find(Long.parseLong(backMap.get("backId")+""));
				modelMap.put("oldUser", oldUser);
			}
		}else{
			modelMap.put("msg", "找不到相关记录,请刷新再操作");
		}
		return "user/updateAudit";
	}
	/**
	 * 审核操作
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param id
	 * @param formMap
	 * @return
	 */
	@RequestMapping({ "/updateAudit" })
	public String updateAudit(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,long id,FormMap formMap){
		Map<String,Object> backMap = this.userBackService.find(id);
		if(backMap!=null){
			Long backId = Long.parseLong(backMap.get("backId")+"");
			if(backMap.get("updateStatus")!=null&&!backMap.get("updateStatus").equals("0")){
				modelMap.put("msg", "状态已变更,不能进行审核操作,请刷新再操作");
			}else{
				//判断是否存在相同用户名的记录
				Map<String,Object> u = userService.findByUserName((String)backMap.get("userName"));
				if(u!=null&&!u.get("id").equals(backId)){
					//直接更新back记录
					UserBack back = new UserBack();
					BeanUtil.copyBeanProperties(back, backMap);
					back.setUpdateStatus("2");
					back.setUpdateUserName(ParamUtil.getRealName(request));
					modelMap.put("msg", "已存在相同的登录名,请重新修改!");
				}else{
					Map<String,Object> auditMap = formMap.getFormMap();
					if(auditMap.get("updateStatus")!=null&&auditMap.get("updateStatus").equals("1")){//表示通过
						Map<String,Object> user = this.userService.find(backId);
						for(int i =0 ; i <userBack.length;i++){
							user.put(userBack[i], backMap.get(userBack[i]));
						}
						User nu = new User();
						BeanUtil.copyBeanProperties(nu, user);
						this.userService.update(nu);
						//修改角色权限
						userRoleService.deleteByUserId(backId);
						UserRole ur = new UserRole();
						ur.setUserId(backId);
						ur.setRoleId(Long.parseLong(backMap.get("roleId").toString()));
						this.userRoleService.save(ur);
					}
					//直接更新back记录
					UserBack back = new UserBack();
					BeanUtil.copyBeanProperties(back, backMap);
					back.setAuditNote((String)auditMap.get("auditNote"));
					back.setUpdateStatus((String)auditMap.get("updateStatus"));
					back.setUpdateUser(ParamUtil.getUserId(request));
					back.setUpdateTime(new Date());
					back.setUpdateUserName(ParamUtil.getRealName(request));
					this.userBackService.update(back);
					modelMap.put("msg", "succeed");
				}
				
			}
		}else{
			modelMap.put("msg", "找不到相关记录,请刷新再操作");
		}
		return "user/updateAudit";
	}
	@RequestMapping({ "/toCanAddOldTrain" })
	public String toCanAddOldTrain(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> canAddOldTrainMap = this.paramService.findByCode("canAddOldTrain");
		modelMap.put("canAddOldTrainMap", canAddOldTrainMap);
		return "user/canAddOldTrain";
	}
	
	@RequestMapping({ "/saveCanAddOldTrain" })
	public String saveCanAddOldTrain(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,FormMap formMap) {
		Param param = new Param();
		Map<String,Object> paramMap = formMap.getFormMap();
		Map<String,Object> canAddOldTrainMap = this.paramService.findByCode("canAddOldTrain");
		if(canAddOldTrainMap!=null){
			BeanUtil.copyBeanProperties(param, canAddOldTrainMap);
			param.setValue((String)paramMap.get("value"));
			param.setCreateTime(new Date());
			param.setCreateUserName(ParamUtil.getRealName(request));
			this.paramService.update(param);
		}else{
			BeanUtil.copyBeanProperties(param, paramMap);
			param.setCreateTime(new Date());
			param.setCreateUserName(ParamUtil.getRealName(request));
			this.paramService.save(param);
		}
		modelMap.put("msg", "succeed");
		return "user/canAddOldTrain";
	}
	
	@RequestMapping({ "/toAdd" })
	public String toAdd(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, Object>> roleList = this.roleService.queryList();
		modelMap.put("roleList", roleList);
		return "user/add";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping({ "/add" })
	public String add(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap) {
		Map<String, Object> userMap = formMap.getFormMap();
		HttpSession session = request.getSession();
		Map<String, Object> user = this.userService
				.findByUserName((String) userMap.get("userName"));
		if (user == null || user.get("status").equals("0")) {
			// Map<String,Object> uMap =
			// this.userService.find(Long.parseLong(userMap.get("id").toString()));
			User u = new User();
			// BeanUtil.copyBeanProperties(u, uMap);
			u.setUserName((String) userMap.get("userName"));
			u.setRealName((String) userMap.get("realName"));
			u.setEmail((String) userMap.get("email"));
			u.setCanEmail((String) userMap.get("canEmail"));
			u.setCreateTime(new Date());
			//设置状态为２表示是添加记录需要审核
			u.setStatus("2");
			u.setPassword(Md5EncryptUtil.encrypt((String) userMap
					.get("password")));
			Map<String, Object> um = (Map<String, Object>) session
					.getAttribute("user");
			u.setCreateUser((Long) um.get("id"));
			long id = this.userService.save(u);
			if (userMap.get("roleId") != null
					&& !userMap.get("roleId").toString().equals("")) {
				UserRole ur = new UserRole();
				ur.setUserId(id);
				ur.setRoleId(Long.parseLong(userMap.get("roleId").toString()));
				this.userRoleService.save(ur);
			}
			modelMap.put("msg", "succeed");
		} else {
			List<Map<String, Object>> roleList = this.roleService.queryList();
			modelMap.put("roleList", roleList);
			modelMap.put("msg", "此登录名已存在！");
		}
		modelMap.put("userMap", userMap);
		return "user/add";
	}

	@RequestMapping({ "/toUpdatePW" })
	public String toUpdatePW(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		return "user/updatePW";
	}

	@RequestMapping({ "/updatePW" })
	public String updatePW(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, String oldPassword,
			String newPassword, Long id) {
		Map<String, Object> user = this.userService.find(id.longValue());
		String op = Md5EncryptUtil.encrypt(oldPassword);
		if (op.equals(user.get("password"))) {
			String np = Md5EncryptUtil.encrypt(newPassword);
			User u = new User();
			BeanUtil.copyBeanProperties(u, user);
			u.setPassword(np);
			this.userService.update(u);
			modelMap.put("msg", "succeed");
		} else {
			modelMap.put("oldPassword", oldPassword);
			modelMap.put("newPassword", newPassword);
			modelMap.put("msg", "旧密码不正确请从新输入旧密码");
		}
		return "user/updatePW";
	}

	@RequestMapping({ "/toUserManager" })
	public String toUserManager(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, Object>> list = this.userBackService.queryList();
		modelMap.put("userList", list);
		return "user/userManager";
	}

	@RequestMapping({ "/toUpdate" })
	public String toUpdate(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, Long id) {
		Map<String, Object> userMap = this.userService.find(id.longValue());
		modelMap.put("userMap", userMap);
		List<Map<String, Object>> roleList = this.roleService.queryList();
		modelMap.put("roleList", roleList);
		return "user/update";
	}

	@RequestMapping({ "/update" })
	public String update(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, FormMap formMap) {
		Map<String, Object> userMap = formMap.getFormMap();
		Map<String, Object> user = this.userService
				.findByUserName((String) userMap.get("userName"));
		if ((user == null)
				|| (user.get("id").toString().equals(userMap.get("id")
						.toString()))) {
			Map<String, Object> uMap = this.userService.find(Long
					.parseLong(userMap.get("id").toString()));
			User u = new User();
			BeanUtil.copyBeanProperties(u, uMap);
			u.setUserName((String) userMap.get("userName"));
			u.setRealName((String) userMap.get("realName"));
			u.setEmail((String) userMap.get("email"));
			u.setCanEmail((String) userMap.get("canEmail"));
			this.userService.update(u);
			this.userRoleService.deleteByUserId(u.getId());
			if (userMap.get("roleId") != null
					&& !userMap.get("roleId").toString().equals("")) {
				UserRole ur = new UserRole();
				ur.setUserId(u.getId());
				ur.setRoleId(Long.parseLong(userMap.get("roleId").toString()));
				this.userRoleService.save(ur);
			}
			modelMap.put("msg", "succeed");
		} else {
			List<Map<String, Object>> roleList = this.roleService.queryList();
			modelMap.put("roleList", roleList);
			modelMap.put("msg", "此登录名已存在！");
		}
		modelMap.put("userMap", userMap);
		return "user/update";
	}

	@RequestMapping({ "/toResetPW" })
	public String toResetPW(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, Long id) {
		modelMap.put("id", id);
		return "user/resetPW";
	}

	@RequestMapping({ "/delete" })
	public String delete(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, Long id) {
		Map<String, Object> userMap = this.userService.find(id);
		User user = new User();
		BeanUtil.copyBeanProperties(user, userMap);
		user.setStatus("3");
		user.setDeleteTime(new Date());
		user.setDeleteUser(ParamUtil.getUserId(request));
		user.setDeleteUserName(ParamUtil.getRealName(request));
		userService.update(user);
		modelMap.put("msg", "succeed");
		return "redirect:/user/toUserManager.do";
	}

	@RequestMapping({ "/updateResetPW" })
	public String updateResetPW(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, Long id, String newPassword,String updateNote) {
		Map<String,Object> b = passwordBackService.findByUserId(id);
		if(b==null){
			PasswordBack back = new PasswordBack();
			back.setUserId(id);
			back.setCreateTime(new Date());
			back.setCreateUser(ParamUtil.getUserId(request));
			back.setCreateUserName(ParamUtil.getRealName(request));
			back.setUpdateStatus("0");
			back.setUpdateNote(updateNote);
			back.setPassword(newPassword);
			passwordBackService.save(back);
			modelMap.put("msg", "succeed");
		}else{
			modelMap.put("msg", "此用户已存在密码修改,请进行审核操作!");
		}
		return "user/resetPW";
	}
	/**
	 * 进入审核密码修改页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/toAuditUserPW" })
	public String toAuditUserPW(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, Long id) {
		Map<String,Object> backMap = passwordBackService.find(id);
		if(backMap.get("updateStatus")!=null&&backMap.get("updateStatus").equals("0")){
			modelMap.put("backMap", backMap);
		}else{
			modelMap.put("msg", "状态已变更,不能进行审核操作了");
		}
		return "user/updateAuditPW";
	}
	/**
	 * 审核密码修改
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping({ "/auditUserPW" })
	public String auditUserPW(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response, Long id, FormMap formMap) {
		Map<String,Object> backMap = passwordBackService.find(id);
		if(backMap.get("updateStatus")!=null&&backMap.get("updateStatus").equals("0")){
			Map<String,Object> auditMap = formMap.getFormMap();
			PasswordBack back = new PasswordBack();
			BeanUtil.copyBeanProperties(back, backMap);
			if(auditMap.get("updateStatus").equals("1")){//审核通过
				Map<String, Object> uMap = this.userService.find((Long)backMap.get("userId"));
				User u = new User();
				BeanUtil.copyBeanProperties(u, uMap);
				u.setPassword((Md5EncryptUtil.encrypt(back.getPassword())));
				this.userService.update(u);
				if ((u.getEmail() != null) && (!u.getEmail().equals(""))) {
					EmailUtil email = new EmailUtil();
					try {
						email.simpleSend(u.getEmail(), "您的密码已重置", "您好！您的密码已重置为："
								+ back.getPassword() + ",请尽快登录系统修改密码。");
						
					} catch (Exception e) {
						modelMap.put("msg", "密码重置成功，但发送密码到邮箱失败,请转告此用户!");
						e.printStackTrace();
					}
				} else {
					modelMap.put("msg", "密码重置成功，由于没有设置邮箱，所以发送密码失败,请转告此用户!");
				}
			}
			back.setAuditNote((String)auditMap.get("auditNote"));
			back.setUpdateStatus((String)auditMap.get("updateStatus"));
			back.setAuditUser(ParamUtil.getUserId(request));
			back.setAuditTime(new Date());
			back.setAuditUserName(ParamUtil.getRealName(request));
			passwordBackService.update(back);
			modelMap.put("msg", "succeed");
		}else{
			modelMap.put("msg", "状态已变更,不能进行审核操作了");
		}
		return "user/updateAuditPW";
	}
}