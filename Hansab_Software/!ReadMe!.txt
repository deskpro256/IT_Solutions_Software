*[!]*-=WIP=-*[!]*

If this project(Hansab_Software) is imported, there should be 3 Java packages:
   |__> src/main/java
	* com.Hansab.WebAppEmail
		|__> HansabSoftwareApplication.java (with main() spring application)

	* com.Hansab.WebAppEmail
		|__> Controllers.java

	* com.Hansab.WebAppEmail
		|__> Email.java


Email.java does the email downloading. If you need to change the email address, there are variables with "//<<"" comments stating what should be changed. The currently used address works and has test emails sent to it. I have disabled the deleting of them because its a pain sending them.

This isn't the prettiest solution as I'm generating an .HTML file each time the application is run, but that will change.
Also, the linked sortByCols.js doesn't like to work, but if the whole script is pasted in the html generating String htmlStart, then it works, but the Java file looks like even worse than it already is. But works.

The emails get added to an html table format, then the html file is created, saved and then open with all the data and colorful table.
Also, the emails are then stored in a .csv file and saved.

The html_table.PNG contains an image of the table that gets generated. Also when clicking on the column names, the table gets sorted by the column you click on.

There is a test folder with a test.html that uses the sortByCols.js
You can see how it currently looks like.