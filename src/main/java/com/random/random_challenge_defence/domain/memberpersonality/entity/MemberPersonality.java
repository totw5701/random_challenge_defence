package com.random.random_challenge_defence.domain.memberpersonality.entity;

import com.random.random_challenge_defence.domain.memberpersonality.dto.MemberPersonalityDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberPersonality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    public MemberPersonalityDetailDto toDetailDto() {
        return MemberPersonalityDetailDto.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .build();
    }
}
