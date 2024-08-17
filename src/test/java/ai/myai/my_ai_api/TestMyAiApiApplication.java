package ai.myai.my_ai_api;

import org.springframework.boot.SpringApplication;

public class TestMyAiApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(MyAiApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
