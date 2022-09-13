# github-repos

Hi! Here is my solution of the recruitment task. I've used Java 17 with Maven (for the dependencies), for the purposes of performing operations on the JSON format, I used a simple org.json library. 


A bit about my approach to the solution...
At first I used Google's Gson library, but I wasn't sure if I really wanted to create classes for specific objects in JSON like "Owner" or "Commit". So I used org.json instead and thought, that putting together specific strings would be the best solution. Over time, I realized that creating a list consisting of Repository objects with components: name - String, owner - Owner (login - String) and list of Branches (name - String, commit - Commit (sha - String)) could be a pretty good approach to this problem. So... here I am with my solution. I hope it is good enough for me to become a member of your team. :)

A simple guide for anyone on how to run my solution:
1. Download this repository and unzip.
2. Open the project in IntelliJ or another IDE.
3. Run Maven and compile... or you can just type "mvn compile" in terminal.
4. Click run and follow the instructions. 
