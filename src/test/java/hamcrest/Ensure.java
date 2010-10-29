package hamcrest;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;

public abstract class Ensure {
	
	public static <T> void ensureThat(T actual, Matcher<T> matcher) {
		MatcherAssert.assertThat(actual, matcher);
	}

	public static <T> Matcher<T> shouldBe(T expected) {
		return new IsEqual<T>(expected);
	}
	
}
