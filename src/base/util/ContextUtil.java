/**
 * 
 */
package base.util;

import org.springframework.context.ApplicationContext;

/**
 * @author nijie
 *
 */
public class ContextUtil {
	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext ctx) {
		context = ctx;
	}

}
