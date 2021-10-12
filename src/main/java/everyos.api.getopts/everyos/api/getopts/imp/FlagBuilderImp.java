package everyos.api.getopts.imp;

import everyos.api.getopts.Flag;
import everyos.api.getopts.FlagBuilder;

public class FlagBuilderImp implements FlagBuilder {

	private final String name;
	
	private int id = Flag.ID_UNSET;
	private boolean mandatory = false;
	private int numberRequiredArguments = 0;
	private int numberOptionalArguments = 0;
	private String[] alias = new String[0];
	private String description = "Undocumented";
	private boolean allowDuplicates = false;

	public FlagBuilderImp(String name) {
		this.name = name;
	}
	
	@Override
	public FlagBuilder setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
		
		return this;
	}

	@Override
	public FlagBuilder setAllowDuplicates(boolean allowDuplicates) {
		this.allowDuplicates = allowDuplicates;
		
		return this;
	}

	@Override
	public FlagBuilder setNumberRequiredArguments(int numberArguments) {
		this.numberRequiredArguments = numberArguments;
		
		return this;
	}

	@Override
	public FlagBuilder setNumberOptionalArguments(int numberOptionalArguments) {
		this.numberOptionalArguments = numberOptionalArguments;
		
		return this;
	}
	
	@Override
	public FlagBuilder setID(int id) {
		this.id = id;
		
		return this;
	}

	@Override
	public FlagBuilder setAlias(String... alias) {
		this.alias = alias;
		
		return this;
	}

	@Override
	public FlagBuilder setDescription(String description) {
		this.description = description;
		
		return this;
	}

	@Override
	public Flag build() {
		final boolean mandatory = this.mandatory;
		final int arguments = this.numberRequiredArguments;
		final int optionalArguments = this.numberOptionalArguments;
		final int id = this.id;
		final String[] alias = this.alias;
		final String description = this.description;
		final boolean allowDuplicates = this.allowDuplicates;
		
		return new Flag() {
			@Override
			public boolean isMandatory() {
				return mandatory;
			}
		
			@Override
			public int getNumberRequiredArguments() {
				return arguments;
			}
		
			@Override
			public int getID() {
				return id;
			}
		
			@Override
			public String getName() {
				return name;
			}
		
			@Override
			public String[] getAlias() {
				return alias;
			}
		
			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public int getNumberOptionalArguments() {
				return optionalArguments;
			}

			@Override
			public boolean getAllowDuplicates() {
				return allowDuplicates;
			}
		};
	}
	
}
