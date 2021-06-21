package com.crud.library.repository;

import com.crud.library.domain.RentalEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalEntryDao extends CrudRepository<RentalEntry, Long> {
}
