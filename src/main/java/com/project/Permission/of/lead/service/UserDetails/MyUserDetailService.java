package com.project.Permission.of.lead.service.UserDetails;

import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;


@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user1=userRepo.findByUsername(username);

        if(user1==null){
            System.out.println("user not exits" +user1);
            throw new UsernameNotFoundException(username);
        }
        return new UserPrinciple(user1);




    }
}
