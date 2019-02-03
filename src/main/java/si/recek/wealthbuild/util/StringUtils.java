package si.recek.wealthbuild.util;

public class StringUtils {

    public static String removeIrrelevantCharacters(String string) {
        return string.replace("\n","").replace("\t", "").replace(" ", "" );
    }
}
