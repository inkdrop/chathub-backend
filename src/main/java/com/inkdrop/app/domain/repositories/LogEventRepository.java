package com.inkdrop.app.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.app.domain.models.LogEvent;

public interface LogEventRepository extends CrudRepository<LogEvent, Long> {}
