import java.text.SimpleDateFormat;
import java.util.Date;

public class CookieUtilTest {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat mySDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		Date oldDate1 = mySDF.parse("2012-05-12 15:16:00"); //这里时间可以自己定
		Date nowDate1 = mySDF.parse(mySDF.format(date));
		System.out.println(nowDate1.compareTo(oldDate1));
	}
}
