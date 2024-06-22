package com.example.TicTacToe.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class ActiveUserStore {

    private List<ActiveUserInfo> activeUsers = new ArrayList<>();

    public void addUser(String username, String token, String turn) {
        activeUsers.add(new ActiveUserInfo(username,token,turn));
    }

    public ActiveUserInfo getAtIndex(int index) {
        if(activeUsers.size() > index)
            return activeUsers.get(index);
        return null;
    }

    public void removeUser(String token) {
        activeUsers = activeUsers.stream()
                .filter(user -> !user.getToken().equals(token))
                .collect(Collectors.toList());
    }

    public boolean findUserByUsername(String username)
    {
        return activeUsers.stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }

    public int getNumberOfActiveUsers() {
        return activeUsers.size();
    }

}