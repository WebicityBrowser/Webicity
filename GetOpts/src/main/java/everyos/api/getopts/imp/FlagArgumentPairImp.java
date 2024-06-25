package everyos.api.getopts.imp;

import everyos.api.getopts.Argument;
import everyos.api.getopts.Flag;
import everyos.api.getopts.FlagArgumentPair;

public class FlagArgumentPairImp implements FlagArgumentPair {

	private final Flag flag;
	private final Argument[] arguments;

	public FlagArgumentPairImp(Flag flag, Argument[] arguments) {
		this.flag = flag;
		this.arguments = arguments;
	}

	@Override
	public Flag getFlag() {
		return flag;
	}

	@Override
	public Argument[] getArguments() {
		return arguments;
	}

}
