package address.util;
import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;
/*
 * adapter for JAXB to convert 
 * between LocalDate and an ISO 8601 String
 * of date eg '2012-12-03'
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
	@Override
	public LocalDate unmarshal(String v) throws Exception{
		return LocalDate.parse(v);
	}
	
	@Override
	public String marshal(LocalDate v) throws Exception{
		return v.toString();
	}
	
}
