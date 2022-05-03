package com.unt.assignment.cracker.barrel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CrackerBarrel
{
    static ListOfSteps allSteps() 
    { return new ListOfSteps(); }

    

    static LinkedList<MoveBarrel> firstBarrelSolution(BarrelBoard b)
    {
        ArrayList<LinkedList<MoveBarrel>> out = new ArrayList<LinkedList<MoveBarrel>>();
        solve(b, out, 1);

        if (out.size() == 0) // sanity
            return null;

        return out.get(0);
    }

    static void solve(BarrelBoard b, ArrayList<LinkedList<MoveBarrel>> solutions, int count)
    {
        if (b.countOfPegs == 1)
        {
            solutions.add(new LinkedList<MoveBarrel>());
            return;
        }

        for (MoveBarrel m : allSteps()) 
        {
        	BarrelBoard boardAfter = b.movePegs(m);
            if (boardAfter == null) continue;

            ArrayList<LinkedList<MoveBarrel>> tailSolutions = new ArrayList<LinkedList<MoveBarrel>>();
            solve(boardAfter, tailSolutions, count);

            for (LinkedList<MoveBarrel> solution : tailSolutions)
            {
                solution.add(0, m);
                solutions.add(solution);

                if (solutions.size() == count)
                    return;
            }
        }
    }

    static void printBoard(BarrelBoard b)
    {
        System.out.print("(" + b.countOfPegs + ", [");
        for (int i = 0; i < b.boardCells.length; i++)
            System.out.print(i < b.boardCells.length-1 ? b.boardCells[i] + ", " : b.boardCells[i] + "])");
        System.out.println();
    }

    static void show(BarrelBoard b)
    {
        int[][] lines = { {4,0,0}, {3,1,2}, {2,3,5}, {1,6,9}, {0,10,14} };
        for (int[] l : lines)
        {
            int spaces = l[0];
            int begin  = l[1];
            int end    = l[2];

            String space = new String();
            for (int i = 0; i < spaces; i++)
                space += " ";

            System.out.print(space);
            for (int i = begin; i <= end; i++)
                System.out.print(b.boardCells[i] == 0 ? ". " : "x ");

            System.out.println();
        }

        System.out.println();
    }

    static void replayGame(List<MoveBarrel> moves, BarrelBoard b)
    {
        show(b);
        for (MoveBarrel m : moves)
        {
            b = b.movePegs(m);
            show(b);
        }
    }


    static void start()
    {
        for (int x = 0; x < 5; x++)
        {
            System.out.println("=== " + x + " ===");
            BarrelBoard b = new BarrelBoard(x);
            replayGame(firstBarrelSolution(b), b);
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        start();
    }
}

