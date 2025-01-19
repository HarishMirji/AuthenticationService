package com.scaler.machinecoding.repositories;

import com.scaler.machinecoding.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestBody;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session save(Session session);
}
