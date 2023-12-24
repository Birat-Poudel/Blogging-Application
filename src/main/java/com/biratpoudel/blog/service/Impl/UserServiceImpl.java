package com.biratpoudel.blog.service.Impl;

import com.biratpoudel.blog.config.CustomUserDetailsService;
import com.biratpoudel.blog.config.JwtProvider;
import com.biratpoudel.blog.dto.AuthResponse;
import com.biratpoudel.blog.dto.UserRequest;
import com.biratpoudel.blog.dto.UserResponse;
import com.biratpoudel.blog.dto.mapper.UserMapper;
import com.biratpoudel.blog.dto.mapper.UserResponseMapper;
import com.biratpoudel.blog.exception.ResourceNotFoundException;
import com.biratpoudel.blog.model.Role;
import com.biratpoudel.blog.model.User;
import com.biratpoudel.blog.repository.UserRepository;
import com.biratpoudel.blog.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public UserServiceImpl(UserRepository userRepository, UserResponseMapper userResponseMapper, UserMapper userMapper, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.userResponseMapper = userResponseMapper;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                             .stream()
                             .map(userResponseMapper)
                             .collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(Long userId) {
        return userRepository.findById(userId)
                             .map(userResponseMapper)
                             .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
    }

    @Override
    @Transactional
    public AuthResponse register(UserRequest userRequest) throws Exception {

        User userExist = userRepository.findByUsername(userRequest.username());

        if (userExist != null) {
            throw new Exception("User with this username already exists!");
        }

        User user = userMapper.apply(userRequest);

        Set<Role> role = new HashSet<>();
        role.add(new Role("ROLE_USER"));
        user.setRoles(role);

        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        String token = JwtProvider.generateToken(authentication);

        return new AuthResponse(token, "Register successful!");
    }

    @Override
    public AuthResponse login(UserRequest userRequest) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userRequest.username());

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username!");
        }

        if (!passwordEncoder.matches(userRequest.password(), userDetails.getPassword())){
            throw new BadCredentialsException("Password didn't match!");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        String token = JwtProvider.generateToken(authentication);
        return new AuthResponse(token, "Login successful!");
    }

    @Override
    @Transactional
    public UserResponse update(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        user.setUsername(userRequest.username());
        user.setPassword(userRequest.password());
        userRepository.save(user);

        return userResponseMapper.apply(user);
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        userRepository.delete(user);
    }

    @Override
    public User findUserByToken(String jwt) {
        String username = JwtProvider.getUsernameFromJwtToken(jwt);
        return userRepository.findByUsername(username);
    }
}
