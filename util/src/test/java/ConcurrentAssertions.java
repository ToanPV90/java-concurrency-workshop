import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.base.Throwables;

import static org.assertj.core.api.Assertions.assertThat;

public class ConcurrentAssertions {

	public static void waitFor(CountDownLatch latch, String waitingForWhat) throws InterruptedException {
		assertThat(latch.await(3, TimeUnit.SECONDS))
				.describedAs("Waiting for " + waitingForWhat)
				.isTrue();
	}

	public static void acquire(Semaphore semaphore, String tooManyOfWhat) throws InterruptedException {
		assertThat(semaphore.tryAcquire())
				.describedAs("Too many " + tooManyOfWhat)
				.isTrue();
	}

	public static <T> void waitForFuture(Future<T> future) {
		try {
			future.get(5, TimeUnit.SECONDS);
		} catch (ExecutionException e) {
			Throwables.throwIfUnchecked(e.getCause());
			throw new RuntimeException(e);
		} catch (TimeoutException e) {
			throw new RuntimeException("Waited too long", e);
		} catch (InterruptedException e) {
			throw new RuntimeException("Task was interrupted", e);
		}
	}

	public static <T> void waitForFutures(Iterable<Future<T>> futures) throws InterruptedException {
        futures.forEach(ConcurrentAssertions::waitForFuture);
    }

}
