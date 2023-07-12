package platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platform.entity.CodeExamples;

import java.util.UUID;

@Repository
public interface CodeExamplesRepository extends JpaRepository<CodeExamples, UUID> {
}
