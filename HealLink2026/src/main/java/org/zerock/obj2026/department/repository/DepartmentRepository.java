package org.zerock.obj2026.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.obj2026.department.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
