package com.example.demo;

import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 6/23/17.
 */
public class ActiveUserStore {

    @Bean
    public ActiveUserStore activeUserStore(){
        return new ActiveUserStore();
    }

     public List<String> users;

    public ActiveUserStore() {
        users = new ArrayList<String>();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }



    // standard getter and setter
}

