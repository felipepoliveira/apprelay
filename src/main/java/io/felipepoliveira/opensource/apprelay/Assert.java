package io.felipepoliveira.opensource.apprelay;

public final class Assert {
	
	/**
	 * Throw an {@link AssertionException} if the given expression is false
	 * @param expression
	 */
	public static void is(boolean expression, String errorMessage) {
		if (!expression) {
			throw new AssertionException(errorMessage);
		}
	}
}
