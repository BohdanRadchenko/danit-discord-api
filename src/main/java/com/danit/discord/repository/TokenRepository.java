package com.danit.discord.repository;

import com.danit.discord.entities.Token;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends AppRepository<Token> {
    @Query(value = """
            select t from tokens t inner join users u\s
            on t.user.id = u.id\s
            where u.id = :id and (t.expired = false or t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByRefreshTokenHash(String token);
}
