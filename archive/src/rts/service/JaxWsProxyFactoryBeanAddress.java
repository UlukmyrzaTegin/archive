package rts.service;

import java.util.ResourceBundle;

public class JaxWsProxyFactoryBeanAddress {
	private static ResourceBundle bundle = ResourceBundle.getBundle("rts.data.JaxWsProxyFactoryBeanAddress");
	
	public static String getAddress(){
		return bundle.getString("address");
	}

	
}
