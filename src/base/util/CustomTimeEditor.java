/**
 * 
 */
package base.util;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;

/**
 * @author nijie
 *
 */
public class CustomTimeEditor  extends CustomDateEditor{
	/**
	 * @param dateFormat
	 * @param allowEmpty
	 * @param exactDateLength
	 */
	public CustomTimeEditor(DateFormat dateFormat, boolean allowEmpty,
			int exactDateLength) {
		super(dateFormat, allowEmpty, exactDateLength);
		// TODO Auto-generated constructor stub
	}
	
	public CustomTimeEditor(DateFormat dateFormat, boolean allowEmpty) {
		super(dateFormat, allowEmpty);
		// TODO Auto-generated constructor stub
	}
	
	public Object getValue(){
       return new Time(((Date)super.getValue()).getTime());
	}


}
