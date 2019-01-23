package util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import util.HttpRequestUtils.Pair;

public class HttpRequestUtilsTest {
	@Test
	public void parseQueryString() {
		String queryString = "userId=tester";
		Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
		assertThat(parameters.get("userId"), is("tester"));
		assertThat(parameters.get("password"), is(nullValue()));

		queryString = "userId=tester&password=password2";
		parameters = HttpRequestUtils.parseQueryString(queryString);
		assertThat(parameters.get("userId"), is("tester"));
		assertThat(parameters.get("password"), is("password2"));
	}

	@Test
	public void parseQueryString_null() {
		Map<String, String> parameters = HttpRequestUtils.parseQueryString(null);
		assertThat(parameters.isEmpty(), is(true));

		parameters = HttpRequestUtils.parseQueryString("");
		assertThat(parameters.isEmpty(), is(true));

		parameters = HttpRequestUtils.parseQueryString(" ");
		assertThat(parameters.isEmpty(), is(true));
	}

	@Test
	public void parseQueryString_invalid() {
		String queryString = "userId=tester&password";
		Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
		assertThat(parameters.get("userId"), is("tester"));
		assertThat(parameters.get("password"), is(nullValue()));
	}

	@Test
	public void parseCookies() {
		String cookies = "logined=true; JSessionId=1234";
		Map<String, String> parameters = HttpRequestUtils.parseCookies(cookies);
		assertThat(parameters.get("logined"), is("true"));
		assertThat(parameters.get("JSessionId"), is("1234"));
		assertThat(parameters.get("session"), is(nullValue()));
	}

	@Test
	public void getKeyValue() throws Exception {
		Pair pair = HttpRequestUtils.getKeyValue("userId=tester", "=");
		assertThat(pair, is(new Pair("userId", "tester")));
	}

	@Test
	public void getKeyValue_invalid() throws Exception {
		Pair pair = HttpRequestUtils.getKeyValue("userId", "=");
		assertThat(pair, is(nullValue()));
	}

	@Test
	public void parseHeader() throws Exception {
		String header = "Content-Length: 59";
		Pair pair = HttpRequestUtils.parseHeader(header);
		assertThat(pair, is(new Pair("Content-Length", "59")));
	}
}
