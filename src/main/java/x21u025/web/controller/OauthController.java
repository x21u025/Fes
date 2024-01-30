package x21u025.web.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.thymeleaf.util.StringUtils;

import com.azure.identity.AuthorizationCodeCredential;
import com.azure.identity.AuthorizationCodeCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.Request;
import x21u025.web.service.MicrosoftService;

@Controller
@Validated
@RequestMapping("oauth")
public class OauthController {

	private String CLIENT_ID = "";
	private String CLIENT_SECRET = "";
	@Value("https://${app.subdomain}.example.jp/oauth/redirect")
	private String REDIRECT_URL = "https://dev.example.jp/oauth/redirect";
	private String DIRECTORY_ID = "";

	@Autowired
	private MicrosoftService microsoftService;

	private static HashMap<String, User> users = new HashMap<>();
	final public static String COOKIE_NAME = "Xfs-Connection";
	final public static String COOKIE_NAME_REDIRECT = "Xfs-RedirectUrl";

	public static boolean isUser(String cookie) {
		return users.containsKey(cookie);
	}
	public static User getUser(String cookie) {
		return users.get(cookie);
	}

	@GetMapping("redirect")
	public String code(HttpServletResponse response, @RequestParam("code") String code, @CookieValue(value=COOKIE_NAME_REDIRECT, required=false, defaultValue = "") String url) throws NoSuchAlgorithmException {
		final AuthorizationCodeCredential authCodeCredential = new AuthorizationCodeCredentialBuilder()
				.clientId(CLIENT_ID)
				.clientSecret(CLIENT_SECRET)
				.authorizationCode(code)
				.redirectUrl(REDIRECT_URL)
				.build();

		ArrayList<String> scopes = new ArrayList<>();
		scopes.add("User.Read");
		final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, authCodeCredential);

		final GraphServiceClient<Request> graphClient = GraphServiceClient
			.builder()
			.authenticationProvider(tokenCredentialAuthProvider)
			.buildClient();

		final User me = graphClient.me().buildRequest().get();

		boolean isLogin = microsoftService.isEmployeeByEmail(me.userPrincipalName);

		System.out.printf("[SEC] [LOGIN] [%s] %s(%s)\n", isLogin ? "OK" : "NO", me.displayName, me.userPrincipalName);

		if(isLogin) {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md5_result = md5.digest(new Date().toString().getBytes());

			String hash = me.hashCode() + String.format("%020x", new BigInteger(1, md5_result));
			users.put(hash, me);
			Cookie cookie = new Cookie(COOKIE_NAME, hash);
			cookie.setHttpOnly(true);
			cookie.setSecure(true);
			cookie.setMaxAge(604800);
			cookie.setPath("/");
			response.addCookie(cookie);
			return "redirect:" + (StringUtils.isEmptyOrWhitespace(url) ? "/e/" : url);
		}
		return "redirect:/";
	}

	@GetMapping("is_login")
	public ModelAndView isLogin(@CookieValue(value=COOKIE_NAME, required=false, defaultValue = "") String cookie, ModelAndView mv) {
		mv.setStatus(HttpStatus.UNAUTHORIZED);
		mv.setView(new View() {
			@Override
			public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {}
		});
		if(users.containsKey(cookie)) {
			mv.setStatus(HttpStatus.OK);
		}
		return mv;
	}

	@GetMapping("check_login")
	public String checkLogin(@CookieValue(value=COOKIE_NAME, required=false, defaultValue = "") String cookie, @RequestParam("url") String redirectUrl, HttpServletResponse response) throws UnsupportedEncodingException {
		Cookie c = new Cookie(COOKIE_NAME_REDIRECT, redirectUrl);
		c.setHttpOnly(true);
		c.setSecure(true);
		c.setMaxAge(600);
		c.setPath("/");
		response.addCookie(c);

		String url = "https://login.microsoftonline.com/" + DIRECTORY_ID + "/oauth2/v2.0/authorize?client_id=" + CLIENT_ID + "&response_type=code&redirect_uri=" + URLEncoder.encode(REDIRECT_URL, "UTF-8") + "&scope=User.Read";
		if(users.containsKey(cookie)) {
			return "redirect:/oauth/is_login";
		} else {
			return "redirect:" + url;
		}
	}

}
