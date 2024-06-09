# Design choices:

- first of all I chose to abstract out the case logic to Library class functions, because I think it's
  way cleaner that way. Also it makes the code a lot easier to change in the future (even though it will not, I think it's a good habit to write easily changable
  code always.)
- I decided to not write the Author class because the only functionality author brings in this app is a search filter, so for that author name property in the Book
  class is enough, I think.
- Chose to make classes extend the Serializable object, because its easy to load and save the serializable classes.

# Challenges

- the only challenges I faced during making this app was stupid little logic mistakes due to my unfamiliarness to java.
- for example when I tried to write b.email==email and for some reason it didn't work so I switched it with b.email.equals(email) and it worked
  I had couple of those problems, but nothing serious.

# Run Instructions

- I just use VSCode to run it, but If that doesn't work I downloaded java toolpack from here: https://code.visualstudio.com/docs/languages/java.
