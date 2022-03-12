package app;

import app.display.BoundingBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Metrics
{
    public static double[] compare(ArrayList<BoundingBox> list1, ArrayList<BoundingBox> list2)
    {
        ArrayList<Double> output = new ArrayList<>();
        for(BoundingBox b1: list1)
        {
            double IoU = bestMatch(list2, b1);
            if(IoU >= 0)
                output.add(IoU);
        }

        return toArray(output);
    }

    private static double bestMatch(ArrayList<BoundingBox> list, BoundingBox box1)
    {
        double max = 0;
        for (BoundingBox box2: list)
        {
            double IoU = box1.IoU(box2);
            if(IoU > max)
                max = IoU;
        }
        return max;
    }

    public static ArrayList<BoundingBox> loadBoxes(int fileID)
    {
        try
        {
            File file = new File("src/main/resources/ans_" + fileID + ".txt");
            Scanner scanner = new Scanner(file);

            ArrayList<BoundingBox> boxes = new ArrayList<>();
            while (scanner.hasNext())
            {
                String line = scanner.nextLine();
                String[] subS = line.split(" ");
                boxes.add(new BoundingBox(d(subS[0]), d(subS[1]), d(subS[2]), d(subS[3])));
            }
            return boxes;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return new ArrayList<BoundingBox>();
        }
    }

    private static double d(String s)
    {
        return Double.valueOf(s);
    }

    private static double[] toArray(ArrayList<Double> d)
    {
        double[] array = new double[d.size()];
        for(int i = 0; i < d.size(); i++)
        {
            array[i] = d.get(i);
        }
        return array;
    }
}
