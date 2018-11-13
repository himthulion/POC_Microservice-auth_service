package com.poc.auth.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.sql.Date;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter   {
	
	// We use auth manager to validate the user credentials
    private AuthenticationManager authManager;
    
    private String _uri = "/auth/**";
    private String _header = "Authorization";
    private String _prefix = "Bearer ";
    private String _secret = "JwtSecretKey";
    private int _expire = 24*60*60;
    
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
		
		// By default, UsernamePasswordAuthenticationFilter listens to "/login" path. 
		// In our case, we use "/auth". So, we need to override the defaults.
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(this._uri, "POST"));
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			// 1. Get credentials from request
			UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
			
			// 2. Create auth object (contains credentials) which will be used by auth manager
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					creds.getUsername(), creds.getPassword(), Collections.emptyList());
			
			// 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
			return authManager.authenticate(authToken);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Upon successful authentication, generate a token.
	// The 'auth' passed to successfulAuthentication() is the current authenticated user.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		Long now = System.currentTimeMillis();
		KeyPair asymmetricKey = null;
		try {
			asymmetricKey = loadKeyStore();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String token = Jwts.builder()
			.setSubject(auth.getName())	
			// Convert to list of strings. 
			// This is important because it affects the way we get them back in the Gateway.
			.claim("authorities", auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(now + _expire * 1000))  // in milliseconds
//			.signWith(SignatureAlgorithm.HS512, _secret.getBytes()) 		//Symmetric key
			.signWith(SignatureAlgorithm.RS512, asymmetricKey.getPrivate()) //Asymmetric key
			.compact();
		
		// Add token to header
		response.addHeader(_header, _prefix + token);
		System.out.println("response====================>success");
		System.out.println("token=======================>"+response.getHeader(_header));
	}
	
	public static KeyPair loadKeyStore() throws Exception {
		final File keystoreFile = new ClassPathResource("mytest.jks").getFile();
		final String password = "mypass";
		final String alias = "mytest";
		final String keyStoreType = KeyStore.getDefaultType();
	    if (null == keystoreFile) {
	        throw new IllegalArgumentException("Keystore url may not be null");
	    }
	    final KeyStore keystore = KeyStore.getInstance(keyStoreType);
	    InputStream is = null;
	    try {
	        is = new FileInputStream(keystoreFile);
	        keystore.load(is, null == password ? null : password.toCharArray());
	    } finally {
	        if (null != is) {
	            is.close();
	        }
	    }
	    final PrivateKey key = (PrivateKey) keystore.getKey(alias, password.toCharArray());
	    final Certificate cert = keystore.getCertificate(alias);
	    final PublicKey publicKey = cert.getPublicKey();
	    return new KeyPair(publicKey, key);
	}
	
	// A (temporary) class just to represent the user credentials
	private static class UserCredentials {
	    private String username, password;
        
        public String getUsername(){
            return this.username;
        }
        public String getPassword(){
            return this.password;
        }
	}
	
}