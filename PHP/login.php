<HTML>
	<!-- Formulaire de connexion par login et password -->
	<?php
		/**
		* Script de connexion avec login et password.
		* A inclure dans la page qui contient le formulaire de connexion
		* Le formulaire doit contenir au moins :
		*   <INPUT TYPE=text NAME=login>
		*   <INPUT TYPE=password NAME=password>
		*/
		
		// R�cup du login,password
		$login = isset($_POST['login']) ? $_POST['login']	: null;
		$pass = isset($_POST['pass'])	? $_POST['pass']	: null;
		
		// Demande de d�connexion	
		if ($login == "logout") {
			sql_logout();
			$login = null;
		}
		
		if ($login && $pass) {
			
			// Validation TODO
			if($login != "logout" && isset($login) && isset($pass)) {
				$user_id = sql_login($login,$pass);
				
				if ($user_id) {
					$_SESSION['user_id'] = $user_id;
					$_SESSION['login'] = $login;
					$_SESSION['pass'] = $pass;
				} else {
					echo "Login ou mot de passe incorrect !";
				}
			} else {
				if (isset($pass)) echo "Attention nom d'utilisateur incorrect !";
			}
		}
	?>
	
	<form id=connexion name=connexion action="#" method=POST>
		<table>
		<tr>
			<td>
				<?php
					// Tente de r�cup�rer login et password
					$login = isset($_POST['login']) ? $_POST['login'] : "";
					
					if (!isset($_SESSION['user_id'])) {
						// Pas encore connect�
						echo "<input type=text name=login id=connexion_login value=\"$login\" />";
					}
				?>
			<td rowspan=2>
				<?php
					if (!isset($_SESSION['user_id'])) {
						// Pas connect�
						echo "<input type=submit value=\"Connexion\"/>";
					}
				?>
		<tr>
			<td>
			<?php
				if (isset($_SESSION['user_id'])) {
					// Connect�
					echo "<input type=hidden name=login value=\"logout\">";
					echo "<label name=login_label>$login</label>";
					echo "<input type=submit name=logout value=\"D�connexion\" onclick=\"click_logout()\"/>";
				} else {
					// Pas connect�
					echo "<input type=\"password\" name=\"pass\" id=\"connexion_pass\" value=\"\" />";
				}
			?>
		</table>
	</form>
</HTML>

<?php
	// sql.php
	
	/**
	 * Connexion d'un utilisateur
	 * @return : L'id de l'utilisateur si la connexion � r�ussie, null sinon
	 */
	function sql_login($login,$pass) {
		// Fonction pour se connect� � la base sql
		connect();
		
		$query = mysql_query("
			SELECT id
			FROM svn_users
			WHERE name='$login' and pass='$pass'
			LIMIT 1
		");
		
		// User inconnu
		if (!$query) return null;
		
		$array = mysql_fetch_array($query);		
		
		mysql_close();
		
		return $array['id'];
	}
	
	/**
	 * D�connexion d'un utilisateur
	 */
	function sql_logout() {
		// On appelle la session
		//session_start();

		// On �crase le tableau de session
		$_SESSION = array();

		// On d�truit la session
		session_destroy();
	}
?>