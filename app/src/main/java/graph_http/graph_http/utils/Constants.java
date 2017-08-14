package graph_http.graph_http.utils;


import org.json.JSONArray;

public class Constants {
    private static String Token;
    private static int MerchantId;
    private static JSONArray arrayLocations;
    private static String Login = "null";
    private static String Password = "null";

    public static String getToken() {
        return Token;
    }

    public static void setToken(String Token) {
        Constants.Token = Token;
    }

    public static int getMerchantId() {
        return MerchantId;
    }

    public static void setMerchantId(int MerchantId) {
        Constants.MerchantId = MerchantId;
    }

    public static JSONArray getArrayLocations() {
        return arrayLocations;
    }

    public static void setArrayLocations(JSONArray arrayLocations) {
        Constants.arrayLocations = arrayLocations;
    }

    public static String getLogin() {
        return Login;
    }

    public static void setLogin(String Login) {
        Constants.Login = Login;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String Password) {
        Constants.Password = Password;
    }
}
