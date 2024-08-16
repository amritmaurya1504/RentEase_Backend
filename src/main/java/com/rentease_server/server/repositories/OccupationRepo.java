package com.rentease_server.server.repositories;

import com.rentease_server.server.entities.Occupation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OccupationRepo {
    @Query("SELECT o FROM occupation_table o WHERE o.tenant.userId = :userId")
    Occupation findByTenantUserId(@Param("userId") String userId);
}
