package org.dcg.repository;

import org.dcg.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {
    List<Logs> findByApplication_ApplicationId(Long applicationId);
}
