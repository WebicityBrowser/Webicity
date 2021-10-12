package everyos.api.getopts;

import java.util.function.Function;

public interface FlagArgumentPairCollection {
	FlagArgumentPair[] get(int id);
	FlagArgumentPair[] filter(Function<Flag, Boolean> filter);
}
