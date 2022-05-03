package com.unt.assignment.cracker.barrel;
import java.util.*;

class MoveBarrel
{
    public int from; 
    public int over; 
    public int to; 

    public MoveBarrel(int from, int over, int to)
    {
        this.from = from;
        this.over = over;
        this.to   = to;
    }

    public MoveBarrel reversed() 
    { return new MoveBarrel(to, over, from); }

    @Override
    public String toString()
    {
        return "(" + from + ", " + over + ", " + to + ")";
    }
}

class BarrelBoard
{
    public int pegCount;
    public int[] cells;

    public BarrelBoard(int emptyCell)
    {
        cells = new int[15];
        pegCount = 14;
        for (int i = 0; i < 15; i++)
            cells[i] = i == emptyCell ? 0 : 1;
    }

    public BarrelBoard(int pegCount, int[] cells)
    {
        this.pegCount = pegCount;
        this.cells    = cells.clone();
    }

    public BarrelBoard move(MoveBarrel m)
    {
        if (cells[m.from] == 1 && 
            cells[m.over] == 1 && 
            cells[m.to]   == 0) 
        {
        	BarrelBoard boardAfter = new BarrelBoard(pegCount-1, cells.clone());
            boardAfter.cells[m.from] = 0;
            boardAfter.cells[m.over] = 0;
            boardAfter.cells[m.to]   = 1;

            return boardAfter;
        }

        return null;
    }
}

class StepIterator implements Iterator<MoveBarrel>
{
    private MoveBarrel[] moves;
    private MoveBarrel   reversed;
    private int    i;

    public StepIterator(MoveBarrel[] moves)
    {
        this.moves = moves;
        this.i     = 0;
    }

    @Override
    public boolean hasNext() 
    { return i < moves.length || (i == moves.length && reversed != null); }

    @Override
    public MoveBarrel next() 
    { 
        if (reversed != null)
        {
        	MoveBarrel result = reversed;
            reversed = null;
            return result;
        }

        MoveBarrel m = moves[i++];
        reversed = m.reversed();

        return m;
    }
}

class StepList implements Iterable<MoveBarrel>
{
    public static final MoveBarrel[] moves = 
    {
        new MoveBarrel(0, 1, 3),
        new MoveBarrel(0, 2, 5),
        new MoveBarrel(1, 3, 6),
        new MoveBarrel(1, 4, 8),
        new MoveBarrel(2, 4, 7),
        new MoveBarrel(2, 5, 9),
        new MoveBarrel(3, 6, 10),
        new MoveBarrel(3, 7, 12),
        new MoveBarrel(4, 7, 11),
        new MoveBarrel(4, 8, 13),
        new MoveBarrel(5, 8, 12),
        new MoveBarrel(5, 9, 14),
        new MoveBarrel(3, 4, 5),
        new MoveBarrel(6, 7, 8),
        new MoveBarrel(7, 8, 9),
        new MoveBarrel(10, 11, 12),
        new MoveBarrel(11, 12, 13),
        new MoveBarrel(12, 13, 14)
    };

    @Override
    public StepIterator iterator()
    { return new StepIterator(moves); }
}

