package com.glowbyte.practicaltask.repository;

import com.glowbyte.practicaltask.enitity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {}
