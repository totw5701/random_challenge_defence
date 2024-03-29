package com.random.random_challenge_defence.domain.challengecardcategory.entity;

import com.random.random_challenge_defence.domain.challengecardcategory.dto.ChallengeCardCategoryDetailDto;
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

    public ChallengeCardCategoryDetailDto toDetailDto() {
        return ChallengeCardCategoryDetailDto.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .build();
    }
}
