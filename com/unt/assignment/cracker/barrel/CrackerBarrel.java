package com.unt.assignment.cracker.barrel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CrackerBarrel
{
    static StepList steps() 
    { return new StepList(); }

    static ArrayList<LinkedList<MoveBarrel>> solve(Board b)
    {
        ArrayList<LinkedList<MoveBarrel>> out = new ArrayList<LinkedList<MoveBarrel>>();
        solve(b, out, 0);

        return out;
    }

    static LinkedList<MoveBarrel> firstSolution(Board b)
    {
        ArrayList<LinkedList<MoveBarrel>> out = new ArrayList<LinkedList<MoveBarrel>>();
        solve(b, out, 1);

        if (out.size() == 0) // sanity
            return null;

        return out.get(0);
    }

    static void solve(Board b, ArrayList<LinkedList<MoveBarrel>> solutions, int count)
    {
        if (b.pegCount == 1)
        {
            solutions.add(new LinkedList<MoveBarrel>());
            return;
        }

        for (MoveBarrel m : steps()) 
        {
            Board boardAfter = b.move(m);
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

    static void printBoard(Board b)
    {
        System.out.print("(" + b.pegCount + ", [");
        for (int i = 0; i < b.cells.length; i++)
            System.out.print(i < b.cells.length-1 ? b.cells[i] + ", " : b.cells[i] + "])");
        System.out.println();
    }

    static void show(Board b)
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
                System.out.print(b.cells[i] == 0 ? ". " : "x ");

            System.out.println();
        }

        System.out.println();
    }

    static void replay(List<MoveBarrel> moves, Board b)
    {
        show(b);
        for (MoveBarrel m : moves)
        {
            b = b.move(m);
            show(b);
        }
    }

    static void terse()
    {
        for (int i = 0; i < 15; i++)
        {
            Board b = new Board(i);
            printBoard(b);
            List<MoveBarrel> moves = firstSolution(b);
            for (MoveBarrel m : moves) 
            {
                System.out.println(m);
                b = b.move(m);
            }
            printBoard(b);
            System.out.println();
        }
    }

    static void go()
    {
        for (int i = 0; i < 5; i++)
        {
            System.out.println("=== " + i + " ===");
            Board b = new Board(i);
            replay(firstSolution(b), b);
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        go();
        terse();

        // This is how you can get all solutions for a particular board.

        //List<LinkedList<Move>> sols = solve(new Board(0));
        //System.out.println(sols.size());
    }
}

