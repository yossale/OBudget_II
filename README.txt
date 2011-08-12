Working with the OBudget2 project :

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
4. You're probably missing the 3rdParty dependency of the smartGwt. You can download it from here : http://code.google.com/p/smartgwt/downloads/detail?name=smartgwt-2.4.zip&can=2&q=
5. add the dependency to the project , and see all the red lines compiled :) 
6. Compile the project for the first time by clicking on the gwt red tool box. 
7. You can now run the project :)
8. How to commit (in the svn sense of the word)
	- git add . //will register all the latest changes for commit
	- git status // not crucial , but will show you which files are regitered as changed
	- git commit -a -m "Your message" // -a = all , -m = message flag
	- git push origin master // the changes have been committed to your local repo , now send them to the remote one

Helpful links:
smartgwt showcase : http://www.smartclient.com/smartgwt/showcase/#main
HaSadna api website : http://api.yeda.us/#
