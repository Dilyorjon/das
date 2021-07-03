package uz.polito.das.config;

public class Constants {

  public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
  public static final String SIGNING_KEY = "devglan123r";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";

  public static final String AUTHORITIES_KEY = "scopes";
  //   public static final String HTTP_LINK =  "http://localhost:80"; //deploy link
  public static final String HTTP_LINK_ANGULAR = "http://localhost:4200"; // connect angular localhost
  public static final String HTTP_LINK_REACT = "http://localhost:3000"; // connect react localhost
  public static final String HTTP_LINK = "*";

}

