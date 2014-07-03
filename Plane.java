//----------------------------------------------------------------------------------
//Author:      Jason McMullen and Adam Reese
//Due Date:    11/20/13
//
//Program:     Plane
//Description: The Plane class keeps track of all the planes that are making
//             requests to the runway. The class has instance variables that keep
//             track of the amount of planes that have made requests, the current
//             number the plane has made a request, the minute the plane made the
//             request during the simulation, and the type of request the plane is
//             asking for. 
//----------------------------------------------------------------------------------

public class Plane
{
   
   /**
   * Variable to keep track of the total amount of planes that have requested use 
   * of the runway
   */
   static private int planeCount = 1
   ;
   
   /**
   * Variable to note when the plane has made a request to the tower during the
   * course of the simulation
   */
   private int time;
   
   /**
   * Variable to hold the request the plane is making to the runway. The requests
   * can be: 'T' - Takeoff, 'L' - Landing.
   */
   private char operation;
   
   /**
   * Variable to hold the number of which the plane entered the que in
   */
   private int planeNo;
   
   
   /**
   * The default constructor, sets the instance variables to the current time
   * duration of the simulator and the request the plane is making to the 
   * runway. planeNo will be equal to the increment of planeCount, this will 
   * number the planes in the order that their requests have been given.
   */
   public Plane(int aTime, char landingOrTakeoff)
   {
   
      time = aTime;
      operation = landingOrTakeoff;
      
      planeNo = planeCount++;
   
   }//end non-default constructor
   
   
   /**
   * This method simply returns the current amount of time the simulation
   * has been running.
   */
   public int getTime()
   {
   
    return time;
   
   }//end getTime method
   
   
   /**
   * This method simply returns the identification number for the plane.
   */
   public int getPlaneNo()
   {
   
      return planeNo;
   
   }//end getPlaneNo method
   
   
   /**
   * This method simply returns the request the plane is making to the runway.
   */
   public char getOperation()
   {
   
      return operation;
   
   }//end getOperation method
   
   
   /**
   * This method simply returns the amount of planes that have made a request
   * to the runway.
   */
   private static int getPlaneCount()
   {
   
      return planeCount;
   
   }//end getPlaneCount;

}//end Plane class
