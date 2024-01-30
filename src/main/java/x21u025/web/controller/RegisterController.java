package x21u025.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import x21u025.web.Exception.NotStudentException;
import x21u025.web.dto.DetailListParam;
import x21u025.web.entity.Menu;
import x21u025.web.entity.Register;
import x21u025.web.service.MenuService;
import x21u025.web.service.RegisterService;

@Controller
@RequestMapping("/r")
public class RegisterController {

	public static List<String> mockEndPoint = new ArrayList<String>();

	@Autowired
	RegisterService registerService;

	@Autowired
	MenuService menuService;

	@GetMapping({"/", ""})
	public String index(@CookieValue(value=OauthController.COOKIE_NAME, required=false, defaultValue = "") String cookie, Model model) {
		checkRegister(cookie);

		List<Menu> menuList = menuService.getAll();

		model.addAttribute("menuList", menuList);
		return "r/index";
	}

	@PostMapping("/register")
	public ModelAndView registerRegister(@RequestParam("cart") String cart) {
		Register register = new Register();
		register.setCart(cart);
		registerService.addRegister(register);

		DetailListParam detailListParam = new DetailListParam();
		detailListParam.setDetailList(Stream.of(register).toList(), menuService);

		PushNotification.sendMessageToAllUsers(String.format("購入されました： %,d円(%d)", detailListParam.getDetailList().get(0).getTotal(), register.getId()));

		ModelAndView mv= new ModelAndView();
		mv.setStatus(HttpStatus.CREATED);
		mv.setView(new View() {
			@Override
			public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {}
		});
		return mv;
	}

	@PostMapping("/notification")
	public ModelAndView registerNotification(@RequestParam("endpoint") String endpoint) {
		mockEndPoint.add(endpoint);
		System.out.println(endpoint);

		ModelAndView mv= new ModelAndView();
		mv.setStatus(HttpStatus.CREATED);
		mv.setView(new View() {
			@Override
			public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {}
		});
		return mv;
	}


	/**
	 * ログインしているか
	 * @throws NotStudentException
	 */
	private void checkRegister(String cookie) throws NotStudentException {
		boolean isLogin = OauthController.isUser(cookie);
		if(!isLogin) {
			throw new NotStudentException("レジ未ログイン");
		}
	}
	/**
	 * Exception発生時の処理メソッド.
	 */
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, HttpServletRequest request) {
		if(e instanceof NotStudentException) {
			return "redirect:/oauth/check_login?url=" + request.getRequestURI();
		}
		return "redirect:/";
	}

}
