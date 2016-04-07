## Victoria Xiao's Khan Academy Project (Infection)

Algorithms in Java that simulate the process of infecting KA users. :)

## Description 

The project consists of two classes. The relationships between users are represented in the form of an undirected graph. Users are represented as nodes in the graph and a coach-student relationship between two users is represented by an edge between the two users.

The UserGraph class is a graph that represents these relations between KA users. My implementations for total_infection, limited_infection and perfect_limited_infection (the optional algorithm that infects exactly the number of users specified) are methods for this class. I also implemented a User class, which contains info about the user's id, the version of the site that the user can access and the user's neighbors.

## Tests

Tests for the total_infection, limited_infection and perfect_limited_infection are located in the appriopriately named classes. To run all tests at once, run the TestRunner java application.