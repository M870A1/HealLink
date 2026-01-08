package org.zerock.obj2026.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
