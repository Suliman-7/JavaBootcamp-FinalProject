package com.example.rekazfinalproject.Config;

import com.example.rekazfinalproject.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class ConfigSecurity  {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()

                // Admin
                .requestMatchers("/api/v1/user/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/user/update").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/user/delete").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/user/discount").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/user/activate").hasAuthority("ADMIN")



                // Owner
                .requestMatchers("/api/v1/owner/register-owner").permitAll()
                .requestMatchers("/api/v1/owner/get-all-owners").permitAll()
                .requestMatchers("/api/v1/owner/get-my-projects").hasAuthority("OWNER")
                .requestMatchers("/api/v1/owner/approve-bid").hasAuthority("OWNER")
                .requestMatchers("/api/v1/owner/reject-bid").hasAuthority("OWNER")
                .requestMatchers("/api/v1/owner/add-Question").hasAuthority("OWNER")
                .requestMatchers("/api/v1/owner/delete-owner").hasAuthority("OWNER")
                .requestMatchers("/api/v1/owner/update-owner/").hasAuthority("OWNER")














                //Investor
                .requestMatchers("/api/v1/investor/get").permitAll()
                .requestMatchers("/api/v1/investor/register").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/investor/update/{id}").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/investor/delete/{id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/investor/add-bid/{projectId}").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/investor/edit-bid/{bidId}").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/investor/add-available-date").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/investor/get-my-projects").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/investor/get-owner-projects").hasAuthority("OWNER")
                .requestMatchers("/api/v1/investor/show-highest-investors-rate").permitAll()
                .requestMatchers("/api/v1/investor/add-Question").hasAnyAuthority("OWNER","INVESTOR")
                .requestMatchers("api/v1/investor/get-investor-by-owner").hasAuthority("OWNER")


                // Project
                // CRUD :
                .requestMatchers("/api/v1/project/get-all-projects").permitAll()
                .requestMatchers("/api/v1/project/add-project").hasAuthority("OWNER")
                .requestMatchers("/api/v1/project/update-project").hasAuthority("OWNER")
                .requestMatchers("/api/v1/project/delete-project").hasAuthority("OWNER")
                .requestMatchers("/api/v1/project/get-closest-projects").permitAll()
                .requestMatchers("/api/v1/project/get-highest-projects").permitAll()
                .requestMatchers("/api/v1/project/get-projects-by-budget").permitAll()
                .requestMatchers("/api/v1/project/get-projects-by-type").permitAll()
                .requestMatchers("/api/v1/project/get-owner-projects").permitAll()
                .requestMatchers("/api/v1/project/get-project-by-city").permitAll()
                .requestMatchers("/api/v1/project/get-projects-by-date").permitAll()

                // Bid
                // CRUD
                .requestMatchers("/api/v1/bid/get-all-bids").permitAll()
                .requestMatchers("/api/v1/bid/add-bid").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/bid/edit-bid").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/bid/delete-bid").hasAuthority("INVESTOR")

                // Contract
                .requestMatchers("/api/v1/contract/get-all-contracts").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/contract/add-contract").hasAuthority("OWNER")
                .requestMatchers("/api/v1/contract/update-contract").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/contract/delete-contract").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/contract/approve-contract/").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/contract/reject-contract/").hasAuthority("INVESTOR")

                // Question
                .requestMatchers("/api/v1/question/get-all-questions").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/question/add-question").permitAll()
                .requestMatchers("/api/v1/question/update-question").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/question/delete-question").hasAuthority("ADMIN")




                //Property
                .requestMatchers("/api/v1/property/get-all-properties").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/property/get-my-properties").hasAuthority("OWNER")
                .requestMatchers("/api/v1/property/add-property").hasAuthority("OWNER")
                .requestMatchers("/api/v1/property/update-property").hasAuthority("OWNER")
                .requestMatchers("/api/v1/property/delete-property").hasAnyAuthority("OWNER","ADMIN")

                // Subscription
                .requestMatchers("/api/v1/subscription/get-all-subscriptions").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/subscription/subscribe").hasAuthority("OWNER")
                .requestMatchers("/api/v1/subscription/update-subscription").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/subscription/delete-subscription").hasAuthority("ADMIN")



                //Rating
                .requestMatchers("/api/v1/rating/add-rating").hasAuthority("OWNER")
                .requestMatchers("/api/v1/rating/get-all-ratings").permitAll()
                .requestMatchers("/api/v1/rating/update-rating").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/rating/delete-rating").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/rating/get-average-rating").permitAll()
                .requestMatchers("/api/v1/rating/get-my-ratings").hasAuthority("OWNER")



                //Consultation
                .requestMatchers("/api/v1/consultation/get-all-consultations").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/consultation/get-my-consultations").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/consultation/book-consultation").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/consultation/update-consultation").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/consultation/delete-consultations").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/consultation/extend-duration").hasAuthority("OWNER")
                .requestMatchers("/api/v1/consultation/investor-cancel-consultation").hasAuthority("OWNER")
                .requestMatchers("/api/v1/consultation/owner-cancel-consultation").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/consultation/get-available-consultation/{investor}").hasAuthority("OWNER")
                .requestMatchers("/api/v1/consultation/get-investor-consultation").hasAuthority("INVESTOR")
                .requestMatchers("/api/v1/consultation/get-owner-consultation").hasAuthority("OWNER")
















                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/user/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();


    }

}
