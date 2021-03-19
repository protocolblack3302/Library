package com.coderme.Library.Repository;

import com.coderme.Library.Domains.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authorities, String> {
}
