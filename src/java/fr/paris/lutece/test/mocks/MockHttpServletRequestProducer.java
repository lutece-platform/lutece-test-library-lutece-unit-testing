package fr.paris.lutece.test.mocks;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@ApplicationScoped
public class MockHttpServletRequestProducer {

	@Produces
    @RequestScoped
    public HttpServletRequest produceRequest() {
        ServletContext servletContext = new MockServletContext( );
        return new  MockHttpServletRequest( servletContext );
    }
}
