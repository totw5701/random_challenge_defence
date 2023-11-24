package com.random.random_challenge_defence.domain.memberpersonality;

import com.random.random_challenge_defence.api.dto.memberpersonality.MemberPersonalityDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

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

    public void update(String title, String description) {
        if(title != null) {
            this.title = title;
        }
        if(description != null) {
            this.description = description;
        }
    }
}
