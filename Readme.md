FindSeats Code Challenge
========================

Solution approach
-----------------
1) the airplane seats is modeled as a array of rows initialized as:
    "#,-1,-1,-1,-1,#".
2) the airplane limits are mapped as "#". This means that the window seats are at "#,-1" or "-1,#"
3) available seats for a group are found via the String contains method, for example:
    a set of available sets for the group "1,2,3" is search as "-1,-1,-1"
    a set of available sets for the group "W1,2,3" is search as "#,-1,-1,-1" or "-1,-1,-1,#"

Solution assumptions
--------------------
1) Once a optimal solution is found the algorithm stops paying attention to a traveller preference regarding window seat. 
This decision was made being that there was no information regarding how to calculate the percentage of satisfaction in these cases.

Run
---
```
$ mvn exec:java -Dexec.mainClass="com.findSeats.Main" -Dexec.args="src/test/resources/<testfie>"
```

Test
----
```
$ mvn test
```