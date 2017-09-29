package com.inkdrop.infrastructure.repositories.readRepositories;

import com.inkdrop.domain.user.readModel.ReadUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadUsersRepository extends CrudRepository<ReadUser, Long> {

}
