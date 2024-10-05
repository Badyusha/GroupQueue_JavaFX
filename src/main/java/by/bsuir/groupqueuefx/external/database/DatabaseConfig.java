package by.bsuir.groupqueuefx.external.database;

public class DatabaseConfig {
	private static final String DATABASE_HOST_NAME = System.getenv("DATABASE_HOST_NAME");
	private static final String DATABASE_PORT =  System.getenv("DATABASE_PORT");
	private static final String DATABASE_NAME =  System.getenv("DATABASE_NAME");

	public static final String DATABASE_URL = "jdbc:mysql://" +
												DATABASE_HOST_NAME + ":" +
												DATABASE_PORT + "/" +
												DATABASE_NAME +
												"?useSSL=false";
	public static final String DATABASE_USERNAME = System.getenv("DATABASE_USERNAME");
	public static final String DATABASE_PASSWORD = System.getenv("DATABASE_PASSWORD");
}
