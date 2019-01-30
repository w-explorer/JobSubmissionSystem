import com.cdtu.util.Md5Util;

public class CookieUtilTest {

	public static void main(String[] args) throws Exception {
		
		String cookieName = "coookie"+Md5Util.md5("2314创新中心");
		
		System.out.println(cookieName);
	}
}
