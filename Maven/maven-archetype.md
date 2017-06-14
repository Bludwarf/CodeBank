- Installer maven et ajouter son dossier bin dans la variable d'env %PATH%
- Copier le projet modèle
- Se placer dans le dossier en ligne de commande
- Maven build... : archetype:create-from-project
- L'archetype est généré dans target/*-archetype
- Copier le fichier d:\.m2/archetype-catalog.xml dans : \\sf2coeur\RNS-BYTEL-EXS\200 - Qualité, Méthodes, Outils\220 - Outils\mvn-repository\archetype-catalog.xml

# Création d'un projet

	- cd exs-services
	- mvn archetype:generate -DarchetypeCatalog=local
		- taper le numéro de l'archetype `TemplateMPGW-archetype`
		- groupId : com.bytel.exs
		- artifactId : NewDomain
		- version : 16.0-1.0-SNAPSHOT
		- package : (vide)
	- cd NewDomain
	- pom.xml
		- Corriger le parent.relativePath
		- Changer également le name et description
		
# Création du packaging "zip"

Dans le plugin maven exs-conf, créer le Mojo ([guide-maven], [tuto packaging]) :

	/**
	* Goal which creates a custom package
	* @goal custom
	* @phase package
	*/
	public class CustomPackageMojo extends AbstractMojo
	{
	
		/**
		* The maven project.
		* @parameter expression="${project}"
		* @required
		* @readonly
		*/
		private MavenProject project;

		/**
		* The directory containing generated classes.
		* @parameter expression="${project.build.outputDirectory}"
		* @required
		* @readonly
		*/
		private File classesDirectory;

		/**
		* Build directory.
		* @parameter expression="${project.build.directory}"
		* @required
		*/
		private File buildDirectory;
		
		/**
		 * The Jar archiver needed for archiving.
		 * @parameter expression="${component.org.codehaus.plexus.archiver.Archiver#jar}"
		 * @required
		 */
		private JarArchiver jarArchiver;

		/**
		 * The maven archive configuration to use
		 * @parameter
		 */
		protected MavenArchiveConfiguration archive = new MavenArchiveConfiguration();
		
		/**
		 * Lib directory
		 *
		 * @parameter expression="${project.build.directory}/custom/libs"
		 * @required
		 */
		private File libDirectory;
		
		// execute, ...
		
	}
	
Créer le fichier [plexus] `src/main/resources/META-INF/plexus/components.xml` (lien maven -> Pojo).

	<?xml version="1.0" encoding="UTF-8"?>
	<component-set>
	  <components>
		<component>
		  <role>org.apache.maven.artifact.handler.ArtifactHandler</role>
		  <role-hint>cust</role-hint>
		  <implementation>org.apache.maven.artifact.handler.DefaultArtifactHandler</implementation>
		  <configuration>
			<type>cust</type>
			<extension>cust</extension>
			<language>java</language>
		  </configuration>
		</component>
		<component>
		  <role>org.apache.maven.lifecycle.mapping.LifecycleMapping</role>
		  <role-hint>cust</role-hint>
		  <implementation>org.apache.maven.lifecycle.mapping.DefaultLifecycleMapping</implementation>
		  <configuration>
			<phases>
			  <process-resources>org.apache.maven.plugins:maven-resources-plugin:resources</process-resources>
			  <compile>org.apache.maven.plugins:maven-compiler-plugin:compile</compile>
			  <process-test-resources>org.apache.maven.plugins:maven-resources-plugin:testResources</process-test-resources>
			  <test-compile>org.apache.maven.plugins:maven-compiler-plugin:testCompile</test-compile>
			  <test>org.apache.maven.plugins:maven-surefire-plugin:test</test>
			  <package>org.tartachuc.m2:my-custom-packaging:custom</package>
			  <install>org.apache.maven.plugins:maven-install-plugin:install</install>
			  <deploy>org.apache.maven.plugins:maven-deploy-plugin:deploy</deploy>
			</phases>
		  </configuration>
		</component>
	  </components>
	</component-set>
	
Modifier l'élément `/component-set/components/component/configuration/phases/package`

# Erreurs

## Maven executable not found at: C:\Program Files\Prog\Maven\apache-maven-3.3.9\bin\mvn.bat

Si l'erreur est 

	Maven executable not found at: C:\Program Files\Prog\Maven\apache-maven-3.3.9\bin\mvn.bat
	
alors copier simplement le fichier `mvn.cmd` dans `mvn.bat`

[guide-maven]: https://maven.apache.org/guides/plugin/guide-java-plugin-development.html
[tuto packaging]: https://blog.tartachuc.org/2008/07/07/creer-un-packaging-maven2/
[plexus]: https://codehaus-plexus.github.io/guides/developer-guide/configuration/component-descriptor.html