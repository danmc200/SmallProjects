import java.util.*;

public class prob18
{
/*            
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
    public static int [][] TRIANGLE_NUMS = {
        new int [] {75},
        new int [] {95, 64},
        new int [] {17, 47, 82},
        new int [] {18, 35, 87, 10},
        new int [] {20, 4, 82, 47, 65},
        new int [] {19, 1, 23, 75, 3, 34},
        new int [] {88, 2, 77, 73, 7, 63, 67},
        new int [] {99, 65, 4, 28, 6, 16, 70, 92},
        new int [] {41, 41, 26, 56, 83, 40, 80, 70, 33},
        new int [] {41, 48, 72, 33, 47, 32, 37, 16, 94, 29},
        new int [] {53, 71, 44, 65, 25, 43, 91, 52, 97, 51, 14},
        new int [] {70, 11, 33, 28, 77, 73, 17, 78, 39, 68, 17, 57},
        new int [] {91, 71, 52, 38, 17, 14, 91, 43, 58, 50, 27, 29, 48},
        new int [] {63, 66, 4, 68, 89, 53, 67, 30, 73, 16, 69, 87, 40, 31},
        new int [] {4, 62, 98, 27, 23, 9, 70, 98, 73, 93, 38, 53, 60, 4, 23}
    };
    public static List<Binary> binaries = new ArrayList<Binary>();
    
    public prob18()
    {
        Map<Integer[], Binary> binaries = new HashMap<Integer[], Binary>();
        Integer[] startIndex = new Integer[]{0,0};
        Binary top = new Binary(TRIANGLE_NUMS[startIndex[0]][startIndex[1]], startIndex[0], startIndex[1]);
        binaries.put(startIndex, top);
        buildBinaryTree(binaries, startIndex);
        printTree(binaries);
    }

    public long getLargestSum(List<Binary> binaries, long sum, long largestSum)
    {
        return largestSum;
    }

    public void buildBinaryTree(Map<Integer[], Binary> binaries, Integer[] rowCol)
    {
            int [] level = TRIANGLE_NUMS[rowCol[0]];
            int node = level[rowCol[1]];
            Binary binary = binaries.get(rowCol);

            if(rowCol[0] + 1 >= TRIANGLE_NUMS.length)
            {
                return;
            }
            int [] levelNext = TRIANGLE_NUMS[rowCol[0] + 1];
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
     
    public void printTree(Map<Integer[], Binary> binaries)
    {
        for(int i = 0; i < TRIANGLE_NUMS.length; i++)
        {
            int [] level = TRIANGLE_NUMS[i];
            for(int j = 0; j < level.length; j++)
            {
                Integer[] key = new Integer[]{i, j};
                key = getMatching(binaries, key);
                Binary b = binaries.get(key);
                try{
                    System.out.println(b);
                }catch(Exception e)
                {
                    System.out.println(i + " " + j);
                    System.out.println(e);
                }
            }
        }
    }

    public static void main(String [] args)
    {
        prob18 prob = new prob18();
    }

    private class Binary
    {
        public Binary 
            nodeNextLeft,
            nodeNextRight;

        public int 
            node = -1,
            row = -1,
            col = -1;

        public Binary(int node, int row, int col) 
        {
            this.node = node;
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
