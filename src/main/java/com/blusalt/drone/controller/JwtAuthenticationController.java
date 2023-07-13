package com.blusalt.drone.controller;

import com.blusalt.drone.config.JwtTokenUtil;
import com.blusalt.drone.dto.request.JwtRequest;
import com.blusalt.drone.dto.response.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class JwtAuthenticationController {

	Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Value("${jwt.token.validity}")
	private String jwtTokenValidity;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		try {
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

			final UserDetails userDetails = jwtInMemoryUserDetailsService
					.loadUserByUsername(jwtRequest.getUsername());

			final String token = jwtTokenUtil.generateToken(userDetails);

			return ResponseEntity.ok(new JwtResponse(token, Integer.valueOf(jwtTokenValidity),"00","success"));
		} catch(Exception ex) {

			log.info("error: " + ex.getMessage());

			if(ex.getMessage().contains("INVALID_CREDENTIALS")) {

				return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse("", 0,"99","invalid credential."));

			}


			return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JwtResponse("", 0,"99","internal server error."));


		}
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}