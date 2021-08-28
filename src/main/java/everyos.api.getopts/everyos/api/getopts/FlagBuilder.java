package everyos.api.getopts;

public interface FlagBuilder {
	FlagBuilder setID(int id);
	FlagBuilder setAlias(String... alias);
	FlagBuilder setDescription(String description);
	
	Flag build();
}
