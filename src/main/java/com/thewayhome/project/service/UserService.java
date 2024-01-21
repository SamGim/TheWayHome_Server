package com.thewayhome.project.service;


import com.auth0.jwk.JwkException;
import com.thewayhome.project.domain.User;
import com.thewayhome.project.dto.user.UserRegisterRequestDto;
import com.thewayhome.project.dto.user.UserResponseDto;
import com.thewayhome.project.exception.CustomError;
import com.thewayhome.project.exception.CustomException;
import com.thewayhome.project.repository.UserRepository;
import com.thewayhome.project.security.UserDetails;
import com.thewayhome.project.utils.CommonTokenUtils;
import com.thewayhome.project.utils.JwtTokenUtils;
import com.thewayhome.project.utils.KakaoTokenUtils;
import com.thewayhome.project.utils.OauthInfo;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Transactional
@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class UserService {

    // use env to get kakao native key from application.properties
    @Autowired
    private Environment env;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private KakaoTokenUtils kakaoTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    //
    public List<User> findUsers() throws Exception {
        if (userRepository.findAll().isEmpty()){
            throw new Exception("result set null");
        }
        return userRepository.findAll();
    }

    public void storeToken(String tokens) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject tokenObj = (JSONObject) parser.parse(tokens);
        String idToken = (String) tokenObj.get("IdToken");

        String[] jwt = idToken.split("\\.");

        byte[] decodedBytes = Base64.getDecoder().decode(jwt[1]);
        String payload = new String(decodedBytes);

//        sessions.put("", idToken);
        System.out.println("payload = " + payload);
    }

    public boolean checkKakaoToken(String token) throws ParseException, JwkException {
        return kakaoTokenUtils.validate(token);
    }

    public void join(UserRegisterRequestDto request) throws ParseException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userBefore = userRepository.findByUsernameAndProvider(userDetails.getUsername(), userDetails.getProvider()).orElseThrow(() -> new RuntimeException("can not find user!"));

        User user = request.toEntity(null);
        user.setProvider(userBefore.getProvider());
        user.setUsername(userBefore.getUsername());
        user.setRefreshToken(userBefore.getRefreshToken());
        user.setId(userBefore.getId());

        userRepository.save(user);
    }



    public UserResponseDto findUserByToken(String token) throws Exception {
        User user = userRepository.findByRefreshToken(token).orElseThrow(() -> new Exception("error find by refreshToken"));
        return new UserResponseDto().fromEntity(user);
    }

    public void saveToken(String username, String provider, String refreshToken) throws  Exception{
        User user = userRepository.findByUsernameAndProvider(username, provider).isPresent() ?
                userRepository.findByUsernameAndProvider(username, provider).orElseThrow(() -> new Exception("error find by nickname")) :
                User.builder()
                        .username(username)
                        .provider(provider)
                        .build();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    public User registerUser(UserRegisterRequestDto userRegisterRequestDto) throws ParseException, JwkException {
        String jwtToken = userRegisterRequestDto.getToken().split(" ")[1];
        log.info("jwtToken = {}", jwtToken);
        if (!jwtTokenUtils.validate(jwtToken)){
            throw new BadCredentialsException("token not valid");
        }
        OauthInfo oauthInfo = jwtTokenUtils.getOauthInfo(jwtToken);
        String userId = oauthInfo.getUserId();
        String provider = oauthInfo.getProvider();
        UserDetails user = userDetailsService.findUser(userId, provider);

        if (userDetailsService.isMember(user)){
            throw new BadCredentialsException("already member");
        }

        User userBefore = userRepository.findByUsernameAndProvider(user.getUsername(), user.getProvider()).orElseThrow(() -> new RuntimeException("can not find user!"));
        userBefore.setNickname(userRegisterRequestDto.getNickname());
        userBefore.setPhoneNumber(userRegisterRequestDto.getPhoneNumber());
        return userRepository.save(userBefore);
    }

    public void test() {
        throw new CustomException(CustomError.TEST_ERROR);
    }
}
