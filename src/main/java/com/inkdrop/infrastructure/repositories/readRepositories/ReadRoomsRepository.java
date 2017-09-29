package com.inkdrop.infrastructure.repositories.readRepositories;

import com.inkdrop.domain.room.readModel.ReadRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadRoomsRepository extends CrudRepository<ReadRoom, Long> {

}
