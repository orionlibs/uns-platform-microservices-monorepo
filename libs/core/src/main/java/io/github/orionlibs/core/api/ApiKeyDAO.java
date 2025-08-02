package io.github.orionlibs.core.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyDAO extends JpaRepository<ApiKeyModel, String>
{
}
