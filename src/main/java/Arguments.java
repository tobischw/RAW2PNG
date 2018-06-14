import java.util.ArrayList;

public class Arguments {

    public static String getOptArg(String longName, String shortName, String[] args, String defaultValue) {

        String prev = null;
        for (String token : args) {
            if (longName.equals(prev) || shortName.equals(prev)) {
                if (token == null || token.trim().length() == 0 || token.startsWith("-")) {
                    return defaultValue;
                }
                return token.trim();
            }
            prev = token;
        }
        return defaultValue;
    }

    public static ArrayList<String> getOptNArg(String longName, String shortName, String[] args) {

        ArrayList<String> tokens = new ArrayList<String>();
        for (int i = 0; i < args.length; ++i) {
            String token = args[i];
            if (longName.equals(token) || shortName.equals(token)) {
                for (int j = i + 1; j < args.length; ++j) {
                    token = args[j];
                    if (token == null || token.trim().length() == 0 || token.startsWith("-")) {
                        return tokens;
                    }
                    tokens.add(token.trim());
                }
                return tokens;
            }
        }
        return tokens;
    }


    public static boolean hasArg(String longName, String shortName, String[] args) {
        for(String token : args) {
            if(longName.equals(token) || shortName.equals(token)) {
                return true;
            }
        }
        return false;
    }
}
