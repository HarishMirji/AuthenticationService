package com.scaler.machinecoding.services;

import com.scaler.machinecoding.exceptions.UserAlreadyExistedException;
import com.scaler.machinecoding.exceptions.UserNotFoundException;
import com.scaler.machinecoding.exceptions.WrongPasswodException;
import com.scaler.machinecoding.models.Role;
import com.scaler.machinecoding.models.Session;
import com.scaler.machinecoding.models.User;
import com.scaler.machinecoding.repositories.SessionRepository;
import com.scaler.machinecoding.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SessionRepository sessionRepository;
//    private SecretKey secretKey = Jwts.SIG.HS256.key().build();
    private SecretKey secretKey = Keys.hmacShaKeyFor("heyhithisisharisheveryoneofyougowareyoudoing"
            .getBytes(StandardCharsets.UTF_8));

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public boolean signUp(String email, String password) throws UserAlreadyExistedException {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistedException("user with email "+ email + " aleady existed");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

    @Override
    public String login(String email, String password) throws UserNotFoundException {
        Optional<User> optionalUseruser = userRepository.findByEmail(email);

        if(optionalUseruser.isEmpty()) {
            throw new UserNotFoundException("User with email "+ email + " not found");
        }

        boolean matches = bCryptPasswordEncoder.matches(password, optionalUseruser.get().getPassword());

        if(matches) {
            String jwtToken = createJwtToken(optionalUseruser.get().getId(),
                    new ArrayList<>(),
                    optionalUseruser.get().getEmail());

            Session session = new Session();
            session.setToken(jwtToken);
            session.setUser(optionalUseruser.get());
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();

            calendar.add(Calendar.DAY_OF_MONTH, 30);
            Date expiration = calendar.getTime();
            session.setExpiringAt(expiration);

            sessionRepository.save(session);

            return jwtToken;
        }
        else {
            throw new WrongPasswodException("Entered password is incorrect, please enter the correct password");
        }
    }

    private String createJwtToken(Long userId, List<Role> roles, String email){
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userId);
        claims.put("roles", roles);
        claims.put("email", email);

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date expiration = calendar.getTime();

        String token = Jwts.builder()
                .claims(claims)
                .expiration(expiration)
                .issuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }

    @Override
    public boolean validateJwtToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            Date expiration = claims.getBody().getExpiration();
            Long userId = claims.getPayload().get("userId", Long.class);
        }catch (Exception e) {
            return false;
        }

        return true;
    }
}
