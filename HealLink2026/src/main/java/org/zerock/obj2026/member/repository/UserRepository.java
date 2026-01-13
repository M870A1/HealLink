package org.zerock.obj2026.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.member.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    // SpringSecurity가 비밀번호 확인을 대신함
    Optional<User> findByEmail(String email);

}









