package hospital.serviceemergency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * configure CORS
 */
@Configuration
public class FilterCorsConfig implements WebMvcConfigurer
{
	
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer()
		{
			@Override
			public void addCorsMappings(CorsRegistry registry)
			{
				
				registry
					.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("OPTIONS","GET", "POST","PUT", "DELETE","PATCH")
					.allowedHeaders("*")
					.allowCredentials(false)
					.maxAge(3600);
			}
		};
	}

}
