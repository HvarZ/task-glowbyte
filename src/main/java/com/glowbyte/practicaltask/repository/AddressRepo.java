package com.glowbyte.practicaltask.repository;

import com.glowbyte.practicaltask.enitity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {}
