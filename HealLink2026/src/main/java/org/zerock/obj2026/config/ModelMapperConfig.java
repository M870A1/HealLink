// ModelMapper를 스프링 컨테이너에 올바르게 등록하여 주입 오류를 해결하고
// Spring Boot 프로젝트 구조의 일관성과 코드 품질을 유지하기 위해
//  `ModelMapperConfig.java` 파일을 생성

package org.zerock.obj2026.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        return modelMapper;
    }
}
