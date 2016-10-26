package base.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginAdminException extends AuthenticationException {
	private static final long serialVersionUID = -3333012976129153127L;

	public LoginAdminException(String msg) {
		super(msg);
	}
}
