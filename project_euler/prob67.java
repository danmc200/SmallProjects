import java.util.*;
import java.io.*;

public class prob67
{
/* example:           
              75 
             95 64
            17 47 82
           18 35 87 10
          20 04 82 47 65
         19 01 23 75 03 34
        88 02 77 73 07 63 67
       99 65 04 28 06 16 70 92
      41 41 26 56 83 40 80 70 33
     41 48 72 33 47 32 37 16 94 29
    53 71 44 65 25 43 91 52 97 51 14
   70 11 33 28 77 73 17 78 39 68 17 57
  91 71 52 38 17 14 91 43 58 50 27 29 48
 63 66 04 68 89 53 67 30 73 16 69 87 40 31
04 62 98 27 23 09 70 98 73 93 38 53 60 04 23

*/
    public static Integer [][] TRIANGLE_NUMS = {
    };
    public static List<Binary> binaries = new ArrayList<Binary>();
    
    public prob67()
    {
        readFile();
        Map<Integer[], Binary> binaries = new HashMap<Integer[], Binary>();
        Integer[] startIndex = new Integer[]{0,0};
        Binary top = new Binary(TRIANGLE_NUMS[startIndex[0]][startIndex[1]], startIndex[0], startIndex[1]);
        binaries.put(startIndex, top);
        buildBinaryTree(binaries, startIndex);
        for(int i = TRIANGLE_NUMS.length-2; i >=0; i--)
        {
            Integer[] startRowCol = getMatching(binaries, new Integer[]{i, 0});
            findHappyPathForRow(binaries, TRIANGLE_NUMS[i].length, startRowCol);
        }
        //long largestSum = getLargestSum(top, 0, 0);
        System.out.println(top.sum);
    }

    public void readFile()
    {
        try {
           BufferedReader br = new BufferedReader(new FileReader("triangle.txt"));
           String line = br.readLine();
           List<List<Integer>> allRows = new ArrayList<List<Integer>>();
           while (line != null) 
           {
               String [] text = line.split(" ");
               List<Integer> row = new ArrayList<Integer>();
               for(String t : text)
               {
                    int i = Integer.valueOf(t);
                    row.add(i);
               }
               allRows.add(row);
               line = br.readLine();
           }
           TRIANGLE_NUMS = new Integer[allRows.size()][];
           for(int i = 0; i < allRows.size(); i++)
           {
                List<Integer> row = allRows.get(i);
                TRIANGLE_NUMS[i] = row.toArray(new Integer[row.size()]);
           }
           br.close();
        } 
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void findHappyPathForRow(Map<Integer[], Binary> binaries, int lenOfRow, Integer[] rowCol)
    {
        Binary b = binaries.get(rowCol);
        if(b.nodeNextLeft.sum > b.nodeNextRight.sum)
        {
            b.sum += b.nodeNextLeft.sum;
        }
        else
        {
            b.sum += b.nodeNextRight.sum;
        }
        int colNext = rowCol[1] + 1;
        if(colNext >= lenOfRow)
        {
            return;
        }
        rowCol = getMatching(binaries, new Integer[]{rowCol[0], colNext});
        findHappyPathForRow(binaries, lenOfRow, rowCol);
    }

    public void buildBinaryTree(Map<Integer[], Binary> binaries, Integer[] rowCol)
    {
            Integer [] level = TRIANGLE_NUMS[rowCol[0]];
            int node = level[rowCol[1]];
            Binary binary = binaries.get(rowCol);

            if(rowCol[0] + 1 >= TRIANGLE_NUMS.length)
            {
                return;
            }
            Integer [] levelNext = TRIANGLE_NUMS[rowCol[0] + 1];
            int nodeNextLeft = levelNext[rowCol[1]];
            int nodeNextRight = levelNext[rowCol[1] + 1];
            Binary binaryLeft = new Binary(nodeNextLeft, rowCol[0]+1, rowCol[1]);
            Binary binaryRight = new Binary(nodeNextRight, rowCol[0]+1, rowCol[1]+1);
            
            if(rowCol[1] == 0)
            {
                binaries.put(new Integer[]{binaryLeft.row, binaryLeft.col}, binaryLeft);
            }
            else
            {
                binaryLeft = binaries.get(getMatching(binaries, new Integer[]{binaryLeft.row, binaryLeft.col}));
            }
            binaries.put(new Integer[]{binaryRight.row, binaryRight.col}, binaryRight);

            binary.setNextLeft(binaryLeft);
            binary.setNextRight(binaryRight);
            int nextRow = rowCol[0];
            int nextCol = rowCol[1];
            if(nextCol + 1 < level.length)
            {
                nextCol++;
            }
            else
            {
                nextRow++;
                nextCol = 0;
            }
            Integer[] tmp = new Integer[]{nextRow, nextCol};
            tmp = getMatching(binaries, tmp);
            buildBinaryTree(binaries, tmp);
    }

    public Integer[] getMatching(Map<Integer[], Binary> binaries, Integer[] toMatch)
    {
            Integer[] rowCol = null;
            for(Integer[] key : binaries.keySet())
            {
                if(integerArraysEqual(key, toMatch))
                {
                    rowCol = key;
                }
            }
            return rowCol;
    }

    public boolean integerArraysEqual(Integer[] x, Integer[] y)
    {
        for(int i = 0; i < x.length; i++)
        {
            if((int)x[i] != (int)y[i])
            {
                return false;
            }
        }
        return true;
    }
     
    public static void main(String [] args)
    {
        prob67 prob = new prob67();
    }

    private class Binary
    {
        public Binary 
            happy,
            nodeNextLeft,
            nodeNextRight;

        public int 
            sum = 0,
            node = -1,
            row = -1,
            col = -1;

        public Binary(int node, int row, int col) 
        {
            this.node = node;
            this.sum = node;
            this.row = row;
            this.col = col;
        }

        public void setNextRight(Binary nodeNextRight)
        {
            this.nodeNextRight = nodeNextRight;
        }

        public void setNextLeft(Binary nodeNextLeft)
        {
            this.nodeNextLeft = nodeNextLeft;
        }

        @Override
        public String toString()
        {
            String left = "";
            String right = "";
            if(nodeNextLeft != null)
            {
                left = new Integer(nodeNextLeft.node).toString();
            }

            if(nodeNextRight != null)
            {
                right = new Integer(nodeNextRight.node).toString();
            }

            return "node: " + node + " node left: " + left + 
                " node right: " + right;
        }
    }
}
