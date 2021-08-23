# sellics-assignment

a. What assumptions did I made?

While testing autocomplete API, I noticed that I got autocomplete results for every letter that I type inside search box.
My assumption was that most of the users will not do copy/paste of desired keyword into search box,
but instead will type one letter after another until the entire word is typed.

Based on that, I concluded that keyword should have the larger score if it is returned as autocomplete result with 
smaller number of letters typed in search box.

b. How does my algorithm work?

First I divided keyword into sub words, that are contained from the letters of that keyword. 

Example: iphone -> [i, ip, iph, ipho, iphon, iphone]

I had to take into consideration that one can send keyword that contains two or more words, separated by space. In this case, 
when I separate keyword into sub words, I am skipping sub words that ends with space, to avoid searching for same sub word twice.

Example: iphone+12 -> [i, ip, iph, ipho, iphon, iphone, iphone 1, iphone 12]. I am skipping "iphone ".

I defined local variable named "scoreIndex", for every keyword sent, that represent length of keyword
without spaces. I am using this variable, to calculate score for every sub word and later to calculate 
max score that some keyword can have, based on its length.

Every call to autocomplete API returns 10 autocomplete result. Score index is the highest for sub word with the smallest number of letters,
and as number of letters increases, score index for the sub word decreases. 

Example: iphone -> scoreIndex = 6
         sub words:scoreIndex -> [i:6, ip:5, iph:4, ipho:3, iphon:2, iphone:1]

Eventually, I calculated max score that some keyword can have, to be able to get estimated search volume represented in percents.

c. Do I think the (*hint) that you gave you earlier is correct and if so - why?

I think it's true, since Amazons's algorithm will probably return a really huge number of suggestions, 
sort them by search-volume and returns top 10 so difference in search volume between them could be small, if there are any at all.

d. How precise do I think my outcome is and why?

I was looking at the way to calculate score based on some maximum value that some keyword could have. The assumption 
that I made on how autocomplete API works, seemed reasonable from that point of view. Regarding the scores that I got for keywords, 
they look pretty realistic for me although there could be some other mathematical model for calculating score.



