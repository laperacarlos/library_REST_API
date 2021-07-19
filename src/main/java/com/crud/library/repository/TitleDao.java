package com.crud.library.repository;

import com.crud.library.domain.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleDao extends JpaRepository<Title, Long> {
}
