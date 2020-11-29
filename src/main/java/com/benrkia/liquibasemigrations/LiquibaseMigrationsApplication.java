package com.benrkia.liquibasemigrations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.benrkia.liquibasemigrations.todo.Todo;
import com.benrkia.liquibasemigrations.todo.TodoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LiquibaseMigrationsApplication implements CommandLineRunner {

	private final TodoRepository todoRepository;

	public LiquibaseMigrationsApplication(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiquibaseMigrationsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Seeding the Database");
		if (todoRepository.count() == 0) {
			List<Todo> todos = Arrays.asList("Make Nimbleways great again", "PS5 gameplay, it's happening soon right Abbes?", "Have breakfast ofc :p")
				.stream()
				.map(content -> {
					Todo todo = new Todo();
					todo.setContent(content);
					return todo;
				})
				.collect(Collectors.toList());
			todoRepository.saveAll(todos);
			log.info("Seed the DB with {} todos", todos.size());
		}
	}
}
