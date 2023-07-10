package com.random.random_challenge_defence.domain.challengecardcategory;

import com.random.random_challenge_defence.api.dto.challenge.ChallengeCardCategoryDetailDto;
import com.random.random_challenge_defence.api.dto.challenge.ChallengeCardCategoryUpdateDto;
import com.random.random_challenge_defence.domain.file.S3File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChallengeCardCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    public void update(ChallengeCardCategoryUpdateDto form) {
        this.title = form.getTitle();
        this.description = form.getDescription();
    }

    public ChallengeCardCategoryDetailDto toDetailDto() {
        return ChallengeCardCategoryDetailDto.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .build();
    }
}
