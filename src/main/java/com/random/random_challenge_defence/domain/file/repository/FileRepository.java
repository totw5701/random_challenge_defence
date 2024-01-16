package com.random.random_challenge_defence.domain.file.repository;

import com.random.random_challenge_defence.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByIdIn(List<Long> ids);
}
