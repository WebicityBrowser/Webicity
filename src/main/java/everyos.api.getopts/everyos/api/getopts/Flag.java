package everyos.api.getopts;

import everyos.api.getopts.imp.FlagBuilderImp;

public interface Flag {
	public static final int INFINITE_ARGUMENTS = -1;
	public static final int ID_UNSET = -1;
	public static final int NO_FLAG = -2;
	
	boolean isMandatory();
	boolean allowDuplicates();
	int getArguments();
	int getOptionalArguments();
	int getID();
	String getName();
	String[] getAlias();
	String getDescription();
	
	static FlagBuilder createBuilder(String name) {
		return new FlagBuilderImp(name);
	}
}
