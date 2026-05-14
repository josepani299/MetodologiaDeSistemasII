package MetodologiaDeSistema.Proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Securityconfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/clientes/**").permitAll()
            .requestMatchers("/api/clientes/registro").permitAll()
            .requestMatchers("/api/productos/**").permitAll()
            .requestMatchers("/api/producto/registro").permitAll()
            .anyRequest().authenticated()
        );

    return http.build();


}

@Bean
public WebMvcConfigurer corsConfigurer() {

    return new WebMvcConfigurer() {

        @Override
        public void addCorsMappings(CorsRegistry registry) {

            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedMethods("*");
        }
    };
}
}
