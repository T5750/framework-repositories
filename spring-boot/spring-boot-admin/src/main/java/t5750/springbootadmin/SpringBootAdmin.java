package t5750.springbootadmin;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.codecentric.boot.admin.config.EnableAdminServer;
import de.codecentric.boot.admin.notify.LoggingNotifier;
import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import de.codecentric.boot.admin.notify.filter.FilteringNotifier;

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
public class SpringBootAdmin {
	private final static Logger LOGGER = LoggerFactory.getLogger(SpringBootAdmin.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAdmin.class, args);
		LOGGER.info("Enter main()");
	}

	@Profile("secure")
	// tag::configuration-spring-security[]
	@Configuration
	public static class SecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// Page with login form is served as /login.html and does a POST on
			// /login
			http.formLogin().loginPage("/login.html")
					.loginProcessingUrl("/login").permitAll();
			// The UI does a POST on /logout on logout
			http.logout().logoutUrl("/logout");
			// The ui currently doesn't support csrf
			http.csrf().disable();
			// Requests for the login page and the static assets are allowed
			http.authorizeRequests()
					.antMatchers("/login.html", "/**/*.css", "/img/**",
							"/third-party/**").permitAll();
			// ... and any other request needs to be authorized
			http.authorizeRequests().antMatchers("/**").authenticated();
			// Enable so that the clients can authenticate via HTTP basic for
			// registering
			http.httpBasic();
		}
	}

	// end::configuration-spring-security[]
	@Configuration
	public static class NotifierConfig {
		@Bean
		@Primary
		public RemindingNotifier remindingNotifier() {
			RemindingNotifier notifier = new RemindingNotifier(
					filteringNotifier(loggerNotifier()));
			notifier.setReminderPeriod(TimeUnit.SECONDS.toMillis(10));
			return notifier;
		}

		@Scheduled(fixedRate = 1_000L)
		public void remind() {
			remindingNotifier().sendReminders();
		}

		@Bean
		public FilteringNotifier filteringNotifier(Notifier delegate) {
			return new FilteringNotifier(delegate);
		}

		@Bean
		public LoggingNotifier loggerNotifier() {
			return new LoggingNotifier();
		}
	}
}