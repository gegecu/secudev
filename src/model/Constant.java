package model;

public class Constant {

	public class UserTable {
		public static final String table = "`user`";
		public static final String firstname = "`user`.firstname";
		public static final String lastname = "`user`.lastname";
		public static final String sex = "`user`.sexid";
		public static final String description = "`user`.description";
		public static final String salutation = "`user`.salutationid";
		public static final String username = "`user`.username";
		public static final String password = "`user`.password";
		public static final String birthday = "`user`.birthdate";
		public static final String joindate = "`user`.joindate";
	}
	
	public class AdminTable {
		public static final String table = "`admin`";
		public static final String username = "`admin`.username";
	}
	
	public class SexTable {
		public static final String table = "`sex`";
		public static final String sexid = "`sex`.sexid";
		public static final String name = "`sex`.name";
	}
	
	public class SalutationTable {
		public static final String table = "`salutation`";
		public static final String salutationid = "`salutation`.salutationid";
		public static final String name = "`salutation`.name";
	}
}
