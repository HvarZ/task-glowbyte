package com.glowbyte.practicaltask.repository;

import com.glowbyte.practicaltask.entity.db.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, Long> {}
