package org.matthenry87.nativetesting.api;

import lombok.extern.slf4j.Slf4j;

import org.matthenry87.nativetesting.api.ApiApplication.HintRegistrar;
import org.matthenry87.nativetesting.api.db1.Db1Service;
import org.matthenry87.nativetesting.api.db2.Db2Service;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;

@Slf4j
@SpringBootApplication
@ImportRuntimeHints(HintRegistrar.class)
public class ApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Db1Service db1Service, Db2Service db2Service) {

        return args -> {

            log.info("Injected bean: " + db1Service.toString());
            log.info("Injected bean: " + db2Service.toString());
        };
    }

	static class HintRegistrar implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

			hints.reflection().registerType(TypeReference.of("com.zaxxer.hikari.HikariConfig"),
					builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));
		}
	}

}
