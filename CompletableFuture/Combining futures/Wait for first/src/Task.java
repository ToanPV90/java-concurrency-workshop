import java.util.concurrent.CompletableFuture;

public class Task {

	<T> CompletableFuture<T> firstOf(CompletableFuture<T> f1, CompletableFuture<T> f2) {
//		throw new UnsupportedOperationException("TODO");
		return f1.applyToEither(f2, x -> x);
	}

}