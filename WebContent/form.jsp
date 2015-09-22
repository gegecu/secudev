<script src="javascript/form.js"></script>

<div>
	Firstname: <input id="firstname" name="firstname" type="text" maxlength="50" required><br>
	<span id="s_firstname" style="color:red" hidden><small>Firstname only accepts alphanumeric and spaces only.</small></span>
</div>

<div>
	Lastname: <input id="lastname" name="lastname" type="text" maxlength="50" required><br>
	<span id="s_lastname" style="color:red" hidden><small>Lastname only accepts alphanumeric and spaces only.</small></span>
</div>

<div>
	Gender:
	<select id="sex" name="sex" required>
		<option value="Male">Male</option>
		<option value="Female">Female</option>
	</select>
</div>
			
<div>
	Salutation:
	<select id="salutations" name="salutations" required>
	</select>
</div>
			
<div>
	Birthdate:
	<input type="date" id="birthday" name="birthday" required> <br>
	<span id="s_birthday" style="color:red" hidden><small>Age is less than 19.</small></span>
</div>
	
<div>	
	Username: <input id="username" name="username" type="text" maxlength="50" required><br>
	<span id="s_username" style="color:red" hidden><small>Username only accepts alphanumeric and underscores only.</small></span>
</div>

<div>
	Password: <input id="password" name="password" type="password" maxlength="50" required><br>
	<span id="s_password" style="color:red" hidden><small>Password does not accept spaces.</small></span> 
</div>

<div>
About me: <input id="description" name="description" type="text" maxlength="100" required> <br>
</div>