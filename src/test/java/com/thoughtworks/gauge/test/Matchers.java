package com.thoughtworks.gauge.test;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.LinkedList;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class Matchers {
    public static <T> Matcher<? super T[]> aslist(final Matcher<? super Collection<T>> matcher) {
        return new BaseMatcher<T[]>() {
            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(Object item) {
                return matcher.matches(asList((T[]) item));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" as list ").appendDescriptionOf(matcher);
            }
        };
    }

    public static <T> Matcher<? super Collection<T>> containsOnly(final Matcher<T>... matchers) {
        return new BaseMatcher<Collection<T>>() {
            private Boolean tooMuchElements = null;

            @SuppressWarnings("unchecked")
            @Override
            public boolean matches(Object item) {
                if (!(item instanceof Collection)) {
                    return false;
                }

                return everyItemMatches((Collection<T>) item, matchers);
            }

            private boolean everyItemMatches(Collection<T> collection, final Matcher<T>... matchers) {
                if (collection.size() != matchers.length) {
                    tooMuchElements = collection.size() > matchers.length;
                    return false;
                }

                return allItemMatches(collection, matchers);
            }

            private boolean allItemMatches(Collection<T> collection, final Matcher<T>... matchers) {
                LinkedList<T> unmatched = new LinkedList<T>(collection);

                for (Matcher<T> matcher : matchers) {
                    for (T element : unmatched) {
                        if (matcher.matches(element)) {
                            unmatched.remove(element);
                        }
                    }
                }

                return unmatched.isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                if (tooMuchElements == null) {
                    description.appendText(" not all elements matched ");
                    for (int i = 0; i < matchers.length; i++) {
                        description.appendText(format(" [Matcher %s]: ", i)).appendDescriptionOf(matchers[i]);
                    }
                    return;
                }

                description.appendText(
                        (tooMuchElements ? " too much" : " not enough") + "elements for the given amount of matchers ");
            }
        };
    }
}
