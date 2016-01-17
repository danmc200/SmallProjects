import java.io.*;
import java.util.*;

//Finds words in a given Boggle puzzle
public class BoggleSolver
{
    //key: wordlen, values: words
    private static final Map<Integer, List<String>> dictionaries = new HashMap<Integer, List<String>>();
    private static final int DICTIONARY_MAX_WORD_LENGTH = 13;
    

    private static boolean isIntegerArraysRowColEqual(Integer[] a, Integer[] b)
    {
        for(int i = 0; i < a.length; i++)
            if((int)a[i] != (int)b[i])
                return false;
        return true;
    }

    private static Integer[][] findAdjacentLetter(
        Map<Integer, List<Character>> rowAndContents, 
        List<Integer[]> blackList,
        int row,
        int column,
        String part)
    {
        List<Integer[]> adjacent = new ArrayList<Integer[]>();
        List<Integer> 
            columns = new ArrayList<Integer>(),
            rows = new ArrayList<Integer>();

        if(column - 1 >= 0)
            columns.add(column - 1);
        columns.add(column);
        if(column + 1 < 4)
            columns.add(column + 1);

        if(row - 1 >= 0)
            rows.add(row - 1);
        rows.add(row);
        if(row + 1 < 4)
            rows.add(row + 1);

        for(Integer r : rows)
        {
            outer:
            for(Integer c : columns)
            {
                List<String> par = getPartialMatches(part + rowAndContents.get(r).get(c));
                if(par.isEmpty() && !getMatch(part, par)) 
                    continue;
                Integer [] rowAndCol = new Integer[]{r, c};
                for(Integer [] black : blackList)
                    if(isIntegerArraysRowColEqual(black, rowAndCol))
                        continue outer;
                adjacent.add(rowAndCol);
            }
        }
        return adjacent.toArray(new Integer[adjacent.size()][]);
    }

    private static void addMatches(
        Map<Integer, List<String>> matches,
        Integer[][] adjacent,
        final Map<Integer, List<Character>> rowAndContents, 
        List<Integer[]> blackList,
        String part)
    {
        for(Integer [] rowAndCol : adjacent)
        {
            blackList.add(rowAndCol);
            part += "" + rowAndContents.get(rowAndCol[0]).get(rowAndCol[1]);
            boolean isMatch = getMatch(part);
            if(isMatch)
            {
                if(matches.containsKey(part.length()))
                {
                    matches.get(part.length()).add(part);
                }
                else
                {
                    List<String> words = new ArrayList<String>();
                    words.add(part);
                    matches.put(new Integer(part.length()), words);
                }
            }
            Integer[][] tmpAdjacent = findAdjacentLetter(
                rowAndContents,
                blackList,
                rowAndCol[0], 
                rowAndCol[1],
                part);
            if(tmpAdjacent.length != 0)
            {
                addMatches(matches, tmpAdjacent, rowAndContents, blackList, part);
            }
            part = part.substring(0, part.length() -1);
            blackList.remove(rowAndCol);
        }
    }

    private static List<String> getPartialMatches(String part)
    {
        List<String> matches = new ArrayList<String>();
        int partLen = part.length();
        if(!dictionaries.keySet().contains(partLen))
            return matches;
        for(String word : dictionaries.get(partLen))
        {
            if(word.length() < partLen)
                continue;
            String compare = word.substring(0, partLen);
            if(part.equals(compare))
                matches.add(word);
        }
        return matches;
    }

    private static boolean getMatch(String part)
    {
        if(!dictionaries.keySet().contains(part.length()))
            return false;
        for(String word : dictionaries.get(part.length()))
            if(word.equals(part))
                return true;
        return false;
    }

    private static boolean getMatch(String part, List<String> partialMatches)
    {
        for(String word : partialMatches)
            if(word.equals(part))
                return true;
        return false;
    }

    private static Map<Integer, List<String>> getWordsForRow(Map<Integer, List<Character>> rowAndContents, int row)
    {
        Map<Integer, List<String>> words = new TreeMap<Integer, List<String>>();    
        for(int firstCol = 0; firstCol < 4; firstCol++)
        {
            List<Integer[]> blackList = new ArrayList<Integer[]>();           
            blackList.add(new Integer[]{row, firstCol});
            String part = "" + rowAndContents.get(row).get(firstCol);
            int col = firstCol;
            Integer[][] adjacent = findAdjacentLetter(rowAndContents, blackList, row, col, part);
            addMatches(words, adjacent, rowAndContents, blackList, part);
        }
        return words;
    }
    
    private static void addToMasterList(Map<Integer, List<String>> list, Map<Integer, List<String>> listMaster)
    {
        for(Integer l : list.keySet())
        {
            if(!listMaster.containsKey(l))
                listMaster.put(l, new ArrayList<String>());

            for(String s : list.get(l))
            {
                if(!listMaster.get(l).contains(s))
                    listMaster.get(l).add(s);
            }
        }
    }

    /**
    * only call if want to load dictionary before user input 
    **/
    public static void loadDictionary()
    {
        if(!dictionaries.isEmpty())
            return;   

        try {
           BufferedReader br = new BufferedReader(new FileReader("dict.txt"));
           String line;

           while ((line = br.readLine()) != null) 
           {
               String [] text = line.split(" ");
               int wordLen = text[0].length();
               if(wordLen > DICTIONARY_MAX_WORD_LENGTH)
                    break;
               if(dictionaries.get(wordLen) == null)
                    dictionaries.put(wordLen, new ArrayList<String>());
               dictionaries.get(wordLen).add(text[0]);
           }
           br.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, List<String>> getBoggleWords(String ruzzle)
    {
        loadDictionary();
        Map<Integer, List<String>> allWords = new TreeMap<Integer, List<String>>();
        Map<Integer, List<Character>> rowAndContents = new HashMap<Integer, List<Character>>();
        for(int i = 0; i < 4; i++)
        {
            List<Character> contents = new ArrayList<Character>();
            for(int j = 0; j < 4; j++)
                contents.add(ruzzle.charAt(j+(i*4)));
            rowAndContents.put(i, contents);
        }
        for(int i = 0; i < 4; i++)
        {
            Map<Integer, List<String>> tmpList = getWordsForRow(rowAndContents, i);
            addToMasterList(tmpList, allWords);
        }
        return allWords;
    }

    public static void validateInput(String puzzle)
    {
        if(puzzle.length() != 16)
        {
            System.out.println("puzzle length should be 16 characters");
            System.exit(0);
        }
    }

    /*Example Puzzle:
    **bnsd
    **eelt
    **hwas
    **idne
    **puzzle = "bnsdeelthwasidne";
    */
    public static void main(String [] args)
    {
        BoggleSolver.loadDictionary();
        String puzzle = "";
        if(args.length == 1)
            puzzle = args[0];
        else
        {
            try{
                System.out.println("Enter puzzle characters: top to bottom, left to right");
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                puzzle = bufferRead.readLine();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        BoggleSolver.validateInput(puzzle);
        puzzle = puzzle.toUpperCase();

        Map<Integer, List<String>> words = BoggleSolver.getBoggleWords(puzzle);
        int size = 0;
        for(Integer key : words.keySet())
        {
            List<String> ws = words.get(key);
            System.out.println("**************" + key + " letter words**************");
            for(String s : ws)
            {
                System.out.println(s);
                size++;
            }
        }
        System.out.println("**************(" + size + ") total words**************");
    }
}
