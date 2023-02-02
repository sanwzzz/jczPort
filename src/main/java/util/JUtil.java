package util;

public class JUtil {


	public static boolean platformIsLinux(){
		return System.getProperty("os.name").toLowerCase().contains("linux");
	}

}
