package t5750.springboot2.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompletableFutureController {
	private final TaskExecutor taskExecutor;

	public CompletableFutureController(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	@GetMapping(value = "/testCompletableFuture")
	public CompletableFuture<String> testCompletableFuture() {
		return CompletableFuture.supplyAsync(() -> {
			randomDelay();
			return "Test CompletableFuture !!";
		}, taskExecutor);
	}

	private void randomDelay() {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}