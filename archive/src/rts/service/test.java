package rts.service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class test {
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

        factory.setServiceClass(DocumentService.class);
        factory.setAddress("http://localhost:7070/storage/" + DocumentService.class.getSimpleName());

        DocumentService service = (DocumentService) factory.create();
        System.out.println(service.create());
	}

}
