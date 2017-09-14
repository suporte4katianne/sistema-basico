package br.com.hsi.util.mail;

import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.util.Properties;

public class MailConfigProducer {

	@Produces
	@ApplicationScoped
	public SessionConfig getMailConfig() throws IOException{
		Properties propertiesMail = new Properties();
		propertiesMail.load(getClass().getResourceAsStream("/mail.properties"));
		
		
		SimpleMailConfig config = new SimpleMailConfig();
		config.setServerHost(propertiesMail.getProperty("mail.server.host"));
		config.setServerPort(Integer.parseInt(propertiesMail.getProperty("mail.server.port")));
		config.setEnableSsl(Boolean.parseBoolean(propertiesMail.getProperty("mail.enable.ssl")));
		config.setAuth(Boolean.parseBoolean(propertiesMail.getProperty("mail.auth")));
		config.setUsername(propertiesMail.getProperty("mail.username"));
		config.setPassword(propertiesMail.getProperty("mail.password"));
		
		
		return config;
	}
	
}
