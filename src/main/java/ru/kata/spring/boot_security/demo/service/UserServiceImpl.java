package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {
   BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
   private final UserDao userDao;

   public UserServiceImpl(UserDao userDao) {
      this.userDao = userDao;
   }

   @Transactional
   @Override
   public boolean addUser(User user) {
      User userFromDB = userDao.findByUsername(user.getUsername());

      if (userFromDB != null) {
         return false;
      }

      user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userDao.save(user);
      return true;
   }

   @Override
   public List<User> getListUsers() {
      return userDao.findAll();
   }

   @Override
   public User getUser(Long id) {
      return userDao.getById(id);
   }

   @Transactional
   @Override
   public void updateUser(Long id, User user) {
       user.setId(id);
       userDao.saveAndFlush(user);
   }

   @Transactional
   @Override
   public void deleteUser(Long id) {
      userDao.deleteById(id);
   }

   public User findByUsername(String username) {
      return userDao.findByUsername(username);
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = findByUsername(username);

      if (user == null) {
         throw new UsernameNotFoundException("User not found: " + username);
      }

      return new org.springframework.security.core.userdetails.User(
              user.getUsername(),
              user.getPassword(),
              mapRolesToAuthorities(user.getRoles()));
   }

   private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
      List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
      for (Role role : roles) {
         grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
      }
      return grantedAuthorityList;
   }
}
