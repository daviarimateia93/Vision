package vision;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerEndpointConfig;

import japp.model.ModelApp;
import japp.model.ModelAppConfiguration;
import japp.model.business.BusinessFactory;
import japp.model.business.BusinessFactoryImpl;
import japp.model.jpa.repository.RepositoryFactory;
import japp.model.jpa.repository.RepositoryFactoryImpl;
import japp.model.jpa.repository.RepositoryManager;
import japp.model.jpa.repository.RepositoryManagerImpl;
import japp.model.service.ServiceFactory;
import japp.model.service.ServiceFactoryImpl;
import japp.web.WebApp;
import japp.web.WebAppConfiguration;
import japp.web.controller.ws.WsControllerFactory;
import japp.web.controller.ws.WsControllerFactoryImpl;
import japp.web.dispatcher.http.HttpDispatcher;
import japp.web.dispatcher.http.HttpDispatcherImpl;
import japp.web.dispatcher.http.parser.FormDataDispatcherParser;
import japp.web.dispatcher.http.parser.HttpDispatcherParserManagerImpl;
import japp.web.dispatcher.http.parser.JsonHttpDispatcherParser;
import japp.web.dispatcher.http.parser.TextHttpDispatcherParser;

public class AppServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4995707578234243087L;
	
	private static HttpDispatcher httpDispatcher = null;
	private static WsControllerFactory wsControllerFactory = null;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		ModelApp.setModelAppConfiguration(new ModelAppConfiguration() {
			
			@Override
			public ServiceFactory getServiceFactory() {
				return ServiceFactoryImpl.getInstance();
			}
			
			@Override
			public RepositoryFactory getRepositoryFactory() {
				return RepositoryFactoryImpl.getInstance();
			}
			
			@Override
			public RepositoryManager getRepositoryManager() {
				return RepositoryManagerImpl.getInstance();
			}
			
			@Override
			public BusinessFactory getBusinessFactory() {
				return BusinessFactoryImpl.getInstance();
			}
		});
		
		WebApp.setWebAppConfiguration(new WebAppConfiguration() {
			
			@Override
			public void init() {
				getHttpDispatcher().register(AppController.class);
			}
			
			@Override
			public void end() {
				
			}
			
			@Override
			public String getAppName() {
				return "Vision";
			}
			
			@Override
			public String getAppVersion() {
				return "1.0.0";
			}
			
			@Override
			public String getViewResolverPrefix() {
				return "/WEB-INF/view/";
			}
			
			@Override
			public String getViewResolverSuffix() {
				return ".jsp";
			}
			
			@Override
			public String getLayoutResolverPrefix() {
				return "/WEB-INF/layout/";
			}
			
			@Override
			public String getLayoutResolverSuffix() {
				return ".jsp";
			}
			
			@Override
			public HttpDispatcher getHttpDispatcher() {
				if (httpDispatcher == null) {
					HttpDispatcherParserManagerImpl.getInstance().addHttpDispatcherParser(new FormDataDispatcherParser());
					HttpDispatcherParserManagerImpl.getInstance().addHttpDispatcherParser(new JsonHttpDispatcherParser());
					HttpDispatcherParserManagerImpl.getInstance().addHttpDispatcherParser(new TextHttpDispatcherParser());
					
					httpDispatcher = new HttpDispatcherImpl();
				}
				
				return httpDispatcher;
			}
			
			@Override
			public WsControllerFactory getWsControllerFactory() {
				if (wsControllerFactory == null) {
					wsControllerFactory = WsControllerFactoryImpl.getInstance();
				}
				
				return wsControllerFactory;
			}
			
			@Override
			public String getNonViewDefaultContentType() {
				return "application/json";
			}
			
			@Override
			public String getPersistenceUnitName(final HttpServletRequest httpServletRequest) {
				return null;
			}
			
			@Override
			public Map<?, ?> getPersistenceProperties(final HttpServletRequest httpServletRequest) {
				return null;
			}
			
			@Override
			public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses) {
				return null;
			}
			
			@Override
			public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
				return null;
			}
			
			@Override
			public boolean isOpenSessionView() {
				return false;
			}
		});
	}
}
