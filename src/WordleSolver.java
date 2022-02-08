import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The WordleSolver class contains the main functionality of the tool
 */
class WordleSolver {

    // Map with length of word as the key to list of words of that length
    private final Map<Integer, List<char[]>> wordLists = new HashMap<Integer, List<char[]>>();
    private final Map<Character, Integer> characterScores = new HashMap<>();

    /**
     * Read a .txt file of words into the wordLists map
     *
     * @param path of .txt file
     * @throws IOException
     */
    public void readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String currentWord;
        char[] currentWordChars;

        while ((currentWord = reader.readLine()) != null) {
            currentWordChars = currentWord.toCharArray();

            if (wordLists.containsKey(currentWordChars.length)) {
                wordLists.get(currentWordChars.length).add(currentWordChars);
            } else {
                List<char[]> newLengthList = new ArrayList<char[]>();

                newLengthList.add(currentWordChars);
                wordLists.put(currentWordChars.length, newLengthList);
            }

            for (int i = 0; i < currentWordChars.length; i++) {
                Character character = currentWordChars[i];
                if (characterScores.containsKey(character)) {
                    characterScores.put(character, characterScores.get(character) + 1);
                } else {
                    characterScores.put(character, 1);
                }
            }

        }

        reader.close();
    }

    /**
     * Gets all known matches of the given char[] to words of the same length as the char[] where "?" are unknowns
     *
     * @param knownChars
     * @return list of matching words
     */
    public List<String> matchWords(char[] knownChars) {
        List<String> matches = new ArrayList<String>();

        for (char[] word : this.wordLists.get(knownChars.length)) {
            boolean match = true;

            for (int i = 0; i < word.length; i++) {
                if (word[i] != knownChars[i] && knownChars[i] != '?') {
                    match = false;
                    break;
                }
            }

            if (match) {
                matches.add(String.valueOf(word));
            }
        }

        return matches;
    }

    public List<String> excludeWords(List<String> matches, String excludeCharacters) {
        List<String> retval = new ArrayList<String>();
        String[] letters = new String[excludeCharacters.length()];
        for (int x = 0; x < excludeCharacters.length(); x++) {
            letters[x] = excludeCharacters.substring(x, x + 1);
        }

        for (String word : matches) {
            if (excludeCharacters.length() > 0) {
                boolean found = false;
                while (!found) {
                    for (String letter : letters) {
                        if (word.contains(letter)) {
                            found = true;
                        }
                    }
                    break;
                }
                if (!found) retval.add(word);

            } else {
                retval.add(word);
            }

        }

        return retval;
    }


    public List<String> includeWords(List<String> matches, String includeCharacters) {
        List<String> retval = new ArrayList<String>();
        String[] letters = new String[includeCharacters.length()];
        for (int x = 0; x < includeCharacters.length(); x++) {
            letters[x] = includeCharacters.substring(x, x + 1);
        }

        for (String word : matches) {
            if (includeCharacters.length() > 0) {
                boolean allLettersMatch = true;
                while (allLettersMatch) {
                    for (String letter : letters) {
                        if (!word.contains(letter)) {
                            allLettersMatch = false;
                            break;
                        }
                    }
                    break;
                }
                if (allLettersMatch) retval.add(word);
            } else {
                retval.add(word);
            }
        }

        return retval;
    }

    /**
     * Returns the number of occurrences of each character in the given list of strings.
     *
     * @param strings
     * @return
     */
    public Map<Character, Integer> calculateCharacterScores(List<String> strings) {
        Map<Character, Integer> retval = new HashMap<Character, Integer>();
        if (strings != null) {
            for (String word : strings) {

                for (int i = 0; i < word.length(); i++) {
                    Character character = word.charAt(i);
                    if (retval.containsKey(character)) {
                        retval.put(character, retval.get(character) + 1);
                    } else {
                        retval.put(character, 1);
                    }
                }

            }
        }
        return retval;
    }

    /**
     * @param words
     * @return
     */
    public List<String> sortByCharacterScore(List<String> words) {
        List<String> retval = words;
        Map<String, Integer> scoreMap = new HashMap<>();

        if (words != null) {
            if (characterScores != null) {
                for (String word : words) {
                    scoreMap.put(word, scoreWord(word));
                }
            }
        }
        // sort scoreMap by descending value
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        scoreMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        // curious to see the worst scores // scoreMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.naturalOrder())).forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        retval = new ArrayList<>();
        for (String word : reverseSortedMap.keySet()) {
            retval.add(word);
        }

        return retval;
    }

    public Integer scoreWord(String word) {
        int wordScore = 0;
        if (word != null) {
            for (int i = 0; i < word.length(); i++) {
                Character character = word.charAt(i);
                wordScore += characterScores.getOrDefault(character, 0);
            }
        }
        return wordScore;
    }

}