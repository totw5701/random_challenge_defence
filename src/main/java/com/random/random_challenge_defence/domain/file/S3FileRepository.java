package com.random.random_challenge_defence.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface S3FileRepository extends JpaRepository<S3File, Long> {
}
