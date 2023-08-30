package utils;

public class JUtil {



    public static boolean isLinux(){
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

}
