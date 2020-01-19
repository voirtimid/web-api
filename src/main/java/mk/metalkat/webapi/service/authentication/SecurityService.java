package mk.metalkat.webapi.service.authentication;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
