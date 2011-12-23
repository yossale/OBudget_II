Working with the OBudget2 project :
0. Install Git on your computer. http://help.github.com/win-set-up-git/

1. On github, fork the project from git@github.com:yossale/OBudget_II.git

2. Create a git client and download the code:
	- cd <new dir>
	- git init
	- git clone git@github.com:<your username>/OBudget_II.git
	- cd OBudget_II/
	- git remote add upstream git://github.com/yossale/OBudget_II.git
	- git fetch upstream
	- git push origin master
	
3. Open the project via eclipse 
	- File -> Import -> General -> Existing projects -> Go to the folder that contains the .project file
	- FIx the smart-gwt dependency : 
		- Download the smart-gwt package 2.5 (or up) (http://code.google.com/p/smartgwt/downloads/detail?name=smartgwt-2.5.zip&can=2&q=)
		- Download the gwt visualization dependency - http://code.google.com/p/gwt-google-apis/downloads/detail?name=gwt-visualization-1.1.1.zip&can=2&q=
		- In eclipse : right click on the project -> Properties -> Java build path -> Libraries -> 
			- Remove the old smartget jar (it has a red !) 
			- Add external Jars -> Choose the smartgwt.jar file.		
			- Add external Jars -> Choose the gwt-visualization.jar file
		
		

6. Compile the project for the first time by clicking on the gwt red tool box. 

7. You can now run the project :)

8. How to commit (in the svn sense of the word)
	- git add . //will register all the latest changes for commit
	- git status // not crucial , but will show you which files are regitered as changed
	- git commit -a -m "Your message" // -a = all , -m = message flag
	- git push origin master // the changes have been committed to your local repo , now send them to the remote one
	
9. How to update (in the svn sense of the word)
	- git pull upstream master // brings the changes from git://github.com/yossale/OBudget_II.git to the current branch and merges them to your current code
	
	
	
Helpful links:
smartgwt showcase : http://www.smartclient.com/smartgwt/showcase/#main
HaSadna api website : http://api.yeda.us/#
