/**
 * 
 */
package base.util;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;





/**
 * @author nijie
 *
 */
public class ShellUtil {
	
	public static void main(String[] args){
		String script="x = (age-weight)*0.2;"+
       "foo='';"+
       "if (x>-1){ "+
       "  foo='低风险';"
       + "}"+
       "else {"+
       "  if (x<-4) {"+
       "   foo='高风险（即时就医）';"
       + "}"+
       "  else {"+
       "   foo='中等风险 （需要关注）';"
       + "}"+
       "}";
      // +
       //"return foo";
		
		Binding binding = new Binding();
		binding.setVariable("age", new Integer(23));
		binding.setVariable("weight", new Double(56.78));
		
		
		GroovyShell shell = new GroovyShell(binding);
		Object value = shell.evaluate(script);
		System.out.println(binding.getVariable("x"));
		System.out.println(value);
		
	}

}
