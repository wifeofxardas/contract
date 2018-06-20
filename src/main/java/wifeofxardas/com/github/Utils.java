package wifeofxardas.com.github;

import org.neo.smartcontract.framework.Helper;

public class Utils {
    public static String stringConcat (String a, String b) {
        return Helper.asString(Helper.concat(Helper.asByteArray(a), Helper.asByteArray(b)));
    }
}
