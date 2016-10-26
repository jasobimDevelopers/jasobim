package base.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginLockException extends AuthenticationException {
	private static final long serialVersionUID = -3333012976129153127L;

	public LoginLockException(String msg) {
		super(msg);
	}
}
