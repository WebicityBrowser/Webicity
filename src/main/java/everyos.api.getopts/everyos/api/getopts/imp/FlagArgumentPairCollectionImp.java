package everyos.api.getopts.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import everyos.api.getopts.Flag;
import everyos.api.getopts.FlagArgumentPair;
import everyos.api.getopts.FlagArgumentPairCollection;

public class FlagArgumentPairCollectionImp implements FlagArgumentPairCollection {

	private final Map<Integer, List<FlagArgumentPair>> arguments;

	public FlagArgumentPairCollectionImp(Map<Integer, List<FlagArgumentPair>> arguments) {
		this.arguments = arguments;
	}

	@Override
	public FlagArgumentPair[] get(int id) {
		List<FlagArgumentPair> flagArguments = arguments.getOrDefault(id, List.of());
		
		return flagArguments.toArray(new FlagArgumentPair[flagArguments.size()]);
	}

	@Override
	public FlagArgumentPair[] filter(Function<Flag, Boolean> filter) {
		List<FlagArgumentPair> fin = new ArrayList<>();
		
		for (Integer key: arguments.keySet()) {
			for (FlagArgumentPair pair: arguments.get(key)) {
				if (!filter.apply(pair.getFlag())) {
					continue;
				}
				
				fin.add(pair);
			}
		}
		
		return fin.toArray(new FlagArgumentPair[fin.size()]);
	}

}
