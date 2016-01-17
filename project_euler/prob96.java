import java.io.*;
import java.util.*;
class prob96
{
 public static final List<List<String>> suDokuGrids = new ArrayList<List<String>>();

 public prob96()
 {
  readFile();
 }

 public static void readFile()
 {
  BufferedReader br = null;
  try {
   String sCurrentLine;
   br = new BufferedReader(new FileReader("sudoku.txt"));
   List<String> grid = new ArrayList<String>();
   while ((sCurrentLine = br.readLine()) != null)
   {
    if(sCurrentLine.contains("Grid"))
    {
     if(!grid.isEmpty())
      suDokuGrids.add(grid);
     grid = new ArrayList<String>();
    }
    else
    {
     grid.add(sCurrentLine);
    }
   }
   suDokuGrids.add(grid);
  }
         catch (IOException e) {
   e.printStackTrace();
  }
  finally {
   try {
    if (br != null)
    {
     br.close();
    }
   } catch (IOException ex) {
    ex.printStackTrace();
   }
  }
 }

 public static List<List<Integer>> getGrid(List<String> grid)
 {
  List<List<Integer>> gridInts = new ArrayList<List<Integer>>();
  for(int i = 0; i < 9; i++)
  {
   String s = grid.get(i);
   List<Integer> ints = new ArrayList<Integer>();
   for(int j = 0; j < 9; j++)
   {
    char c = s.charAt(j);
    int val = Integer.valueOf("" + c);
    ints.add(val);
   }
   gridInts.add(ints);
  }
  return gridInts;
 }

 public static Integer[] incKey(Integer[] keyO)
 {
  Integer[] key = new Integer[keyO.length];
  for(int i = 0; i< keyO.length; i++)
  {
   key[i] = new Integer(keyO[i]);
  }
  if(key[1] == 8)
  {
   if(key[0] == 8)
   {
    return new Integer[0];
   }
   else
   {
    key[0] = ++key[0];
    key[1] = 0;
   }
  }
  else
  {
   key[1] = ++key[1];
  }
  return key;
 }

 public static List<Integer> getColNums(List<List<Integer>> grid, int col)
 {
  List<Integer> values = new ArrayList<Integer>();
  for(int i = 0; i < 9; i++)
  {
   int val = grid.get(i).get(col);
   if(val != 0)
   {
    values.add(val);
   }
  }
  return values;
 }
 
 public static List<Integer> getRowNums(List<List<Integer>> grid, int row)
 {
  List<Integer> values = new ArrayList<Integer>();
  for(int i = 0; i < 9; i++)
  {
   int val = grid.get(row).get(i);
   if(val != 0)
   {
    values.add(val);
   }
  }
  return values;

 }

 public static List<Integer> getQuadrantNums(List<List<Integer>> grid, Integer[] key)
 {
  List<Integer> values = new ArrayList<Integer>();
  int row = key[0] / 3;
  int col = key[1] / 3;
  row *= 3;
  col *= 3;
  for(int i = 0; i < 3; i++)
  {
   for(int j = 0; j < 3; j++)
   {
    int val = grid.get(i).get(j);
    if(val != 0)
    {
     values.add(val);
    }
   }
  }
  return values;
 }

 public static List<Integer> getWhiteList(List<List<Integer>> grid, Integer[] key)
 {
  List<Integer> options = Arrays.asList(
   new Integer[] {
    1, 2, 3, 4, 5, 6, 7, 8, 9
   }
  );
  List<Integer> whiteList = new ArrayList<Integer>();
  List<Integer> allAdjacent = getQuadrantNums(grid, key);
  allAdjacent.addAll(getRowNums(grid, key[0]));
  allAdjacent.addAll(getColNums(grid, key[1]));
  for(int o : options)
  {
   if(!allAdjacent.contains(o))
   {
    whiteList.add(o);
   }
  }
  return whiteList;
 }

 public static boolean containsNeg(List<List<Integer>> grid)
 {
  for(List<Integer> g : grid)
  {
   for(int val : g)
   {
    if(val < 0)
    {
     return true;
    }
   }
  }
  return false;
 }

 public static boolean gridFilled(List<List<Integer>> grid)
 {
  for(List<Integer> g : grid)
  {
   for(int val : g)
   {
    if(val == 0)
    {
     return false;
    }
   }
  }
  return true;
 }

 public static boolean gridsEqual(List<List<Integer>> gridA, List<List<Integer>> gridB)
 {
  for(int i = 0; i < 9; i++)
  {
   for(int j = 0; j < 9; j++)
   {
    if(gridA.get(i).get(j) != gridB.get(i).get(j))
    {
     return false;
    }
   }
  }
  return true;
 }

 public boolean arraysEqual(Integer[] a, Integer[] b)
 {
    for(int i = 0; i < a.length; i++)
    {
        if(a[i] != b[i])
        {
            return false;
        }
    }
    return true;
 }

    public List<List<Integer>> solveGrid(List<List<Integer>> grid, Integer [] key, int lo, List<Integer[]> blacklist)
    {
        if(key.length == 0)
        {
            return grid;
        }
        else
        {
            int val = grid.get(key[0]).get(key[1]);
            if(val == 0)
            {
                List<Integer> whiteList = getWhiteList(grid, key);
                if(whiteList.size() <= lo)
                {
                    grid.get(key[0]).set(key[1], whiteList.get(0));
                    grid = solveGrid(grid, incKey(key), lo, blacklist);
                }
            }
            else
            {
                grid = solveGrid(grid, incKey(key), lo, blacklist);
            }
        }
        return grid;
    }

 public static void main(String [] args)
 {
  prob96 prob = new prob96();
  for(List<String> grid : suDokuGrids)
  {
   System.out.println(grid);
   List<List<Integer>> gridInts = getGrid(grid);
   gridInts = prob.solveGrid(gridInts, new Integer[]{0,0}, 1, new ArrayList<Integer[]> ());
   System.out.println("solved: ");
   for(List<Integer> g : gridInts)
    System.out.println(g);
   break;
  }
 }
}
