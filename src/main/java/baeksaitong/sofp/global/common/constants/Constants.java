package baeksaitong.sofp.global.common.constants;

public class Constants {
    public static final String OAUTH_GRANT_TYPE = "authorization_code";
    public static final String EMAIL_REGEXP = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
    public static final String PW_REGEXP = "^(?=.*[a-zA-z])(?=.*[0-9]).{8,16}$";
    public static final String PHONE_NUM_REGEXP = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
}
