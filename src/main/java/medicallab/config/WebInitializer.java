package medicallab.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class, WebSecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebMvcConfig.class, HibernateConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		
		return dispatcherServlet;
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		MultipartConfigElement multiPartConfigElement = new MultipartConfigElement(
			System.getProperty("java.io.tmpdir"),
			2097152,
			4194304,
			0
		);
		
		registration.setMultipartConfig(multiPartConfigElement);
	}
	
}
