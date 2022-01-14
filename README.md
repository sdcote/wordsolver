# Wordle Solver

A tool to find words containing certain letter combinations.

Not everyone has a dictionary in their head and this tool simply makes all words available to the user and allows you to filter those words.

BTW - Crossword puzzle players will find this tool useful as well.

## How to Use

Enter partially completed words using "?" for unknown letters. If wanting multiple words in one input, use "-" to separate the words. Once filled out, click the "Search" button.

e.g. wo??le

If there are more than 1000 matches for an input word, the tool will only output the first 1000 matches. If this happens consider filling in more unknown letters of the word to make the query more specific.

### Exclude Letters

Enter the letters words should not have in this field. These are the grey letters in your guesses. The tool will remove all words with _**any**_ of these letters from the list of words that match the above pattern.

### Include Letters

Enter the letters the must be included in the word. These are the yellow/gold letters in your guesses. The tool will remove all words from the match list that do not contain _**all**_ these letters.

### The Process

1. Enter your guess into Wordle.
2. Enter `?????` as your search pattern (a 5-letter word) replacing the corresponding `?` with any green letters you get in the proper position.
   1. Enter any grey letters into the "Exclude Letters" field.
   2. Enter any gold/yellow letters in the "Include Letters" field.
3. Press the search button.
4. Select a word from the list to make your next guess.
5. Go to step #1.

With each guess, the list of possible words should get smaller. Even wrong guesses are valuable.

My favorite first guesses are "atone" and then "birds". That hits most of the most common letters. 
