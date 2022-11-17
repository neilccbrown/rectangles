package org.example;

import java.util.ArrayList;

public class Main
{
    public record Rectangle(boolean[][] data)
    {
        public Rectangle(int width, int height)
        {
            this(new boolean[height][width]);
        }
        
        public int getWidth()
        {
            return data[0].length;
        }
        public int getHeight()
        {
            return data.length;
        }
        public Rectangle clone()
        {
            // Deep clone array:
            boolean[][] c = data.clone();
            for (int i = 0; i < data.length; i++)
            {
                c[i] = c[i].clone();
            }
            return new Rectangle(c);
        }
        // Returns null if it doesn't fit
        public Rectangle hasSpace(int targetX, int targetY, int width, int height)
        {
            Rectangle r = clone();
            for (int qy = targetY; qy < targetY + height; qy++)
            {
                for (int qx = targetX; qx < targetX + width; qx++)
                {
                    if (qx >= r.getWidth() || qy >= r.getHeight())
                        return null;
                    
                    if (r.data()[qy][qx])
                        return null;
                    
                    r.data()[qy][qx] = true;
                }
            }
            return r;
        }
    }
    
    public static void main(String[] args)
    {
        // The crucial insight is that for every position in the overall grid
        // that is occupied directly to the left and directly above, there must
        // be a rectangle that has its top-left corner at that position, because
        // otherwise it would be vacant (because the rectangle above doesn't extend
        // down, and the rectangle to the right doesn't extend to the right).
        
        // So we find the next location by scanning across each row, then down the rows,
        // at each vacant position (which must be filled above and to the left because
        // of the way we scan), we try all new sizes of rectangle:
        if (args.length == 0)
        {
            for (int w = 1; w < 5; w++)
            {
                for (int h = w; h < 5; h++)
                {
                    System.out.println("" + w + "x" + h + ": " + countAllFor(w, h));
                }
            }
        }
        else
        {
            int width = Integer.parseInt(args[0]);
            int height = Integer.parseInt(args[1]);
            System.out.println(countAllFor(width, height));
        }
    }

    public static int countAllFor(int width, int height)
    {
        ArrayList<Rectangle> results = new ArrayList<>();
        makeAll(new Rectangle(width, height), results);
        return results.size();
    }

    public static void makeAll(Rectangle soFar, ArrayList<Rectangle> addResultsTo)
    {
        // Find next free location:
        int x = 0, y = 0;
        outer: for (y = 0; y < soFar.getHeight(); y++)
            for (x = 0; x < soFar.getWidth(); x++)
                if (!soFar.data()[y][x])
                    break outer;
        // If rectangle full, no further results except this one:
        if (y == soFar.getHeight())
        {
            addResultsTo.add(soFar);
            return;
        }

        for (int height = 1; height <= soFar.getHeight() - y; height++)
        {
            for (int width = 1; width <= soFar.getWidth() - x; width++)
            {
                Rectangle r = soFar.hasSpace(x, y, width, height);
                if (r != null)
                    makeAll(r, addResultsTo);
            }
        }
    }
}