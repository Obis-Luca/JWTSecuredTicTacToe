package com.example.TicTacToe.Repositories;

import com.example.TicTacToe.Entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Query("""
    SELECT t from Token t
    INNER JOIN User u on t.user.uid = u.uid
    WHERE u.uid = :id and (t.expired = false OR t.revoked = false)
""")
    List<Token> findAllValidTokensByUser(Integer id);
}
