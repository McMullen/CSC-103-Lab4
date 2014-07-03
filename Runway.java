//----------------------------------------------------------------------------------
//Author:      Jason McMullen and Adam Reese
//Due Date:    11/20/13
//
//Program:     Runway
//Description: The Runway class keeps track of all of the planes requests for taking
//             off and landing at the airport. Priority is giving to airplanes 
//             requesting a landing over those that request clearance for takeoff.
//----------------------------------------------------------------------------------

public class Runway
{

   //this variable holds the amount of time (in minutes) for a plane to land
   private int timeForLanding;
   
   //this variable holds the amount of time (in minutes) for a plane to takeoff
   private int timeForTakeoff;
   
   //this variable holds the amount of time (in minutes) left until the runway 
   //is available to be used again
   private int runwayTimeLeft;
   
   //operation can be: 'I' - Idle, 'L' - Landing, 'T' - Takeoff
   private char operation;
  
   //The default constructor sets the default time for a plane to land and takeoff
   //from our runway. It also sets runwayTimeLeft and operation to its default
   //values.   
   public Runway(int time_takeoff, int time_landing)
   {
   
      timeForLanding = time_landing;
      timeForTakeoff = time_takeoff;
      
      runwayTimeLeft = 0;
      
      operation = 'I';
   
   }//end default constructor


   //This method checks to see if the runway is currently available for use. It 
   //does this by comparing the amount of time left on the runway's current 
   //operation and if it is being used it will return true, if the runway is idle 
   //it will return false.
   public boolean isBusy()
   {
   
      return (runwayTimeLeft > 0);
   
   }//end isBusy method


   //This method decrements the amount of time left on the runway's current
   //operation by 1 minute.   
   public void reduceRemainingTime()
   {
   
      if(runwayTimeLeft > 0)
      {
      
         runwayTimeLeft--;
      
      }//end if statement
      
   }//end reduceRemainingTime method
   

   //This method will determine if what state the runway currently is in by
   //interpreting the char coming in. The char can be: 'I' - Idle, 'L' - Landing,
   //'T' - Takeoff. The method will then set the runwayTimeLeft accordingly.
   public void startUsingRunway(char typeOfUse)
   {
   
      //make sure the runway is available for use first
      if(isBusy() == true)
      {
      
         throw new IllegalStateException("The runway is currently in use.");
      
      }//end if statement
      else
      {
      
         operation = typeOfUse;
      
         switch(typeOfUse)
         {
         
            //----------------------------------------------------
            case 'I':
               
               runwayTimeLeft = 0;
               
               break;
                           
            //----------------------------------------------------         
            case 'L':
            
               runwayTimeLeft = timeForLanding;
               
               break;
               
            //----------------------------------------------------
            case 'T':
            
               runwayTimeLeft = timeForTakeoff;
               
               break;
      
         }//end switch statement
      
      }//end else statment
        
   }//end startUsingRunway method
   
   
   /**
   * This method will return the char that is designates what the runway is
   * currently being used for. The options are: 'L' - Landing, 'T' - Takeoff,
   * and 'I' - Idle.
   */
   public char kindOfOperation()
   {
   
      char answer = 'Z';
      
      //if isBusy returns false then no one is currently using the runway
      //therefore must be in the Idle state. If isBusy returns true, then
      //the runway is either being used for Landing or Takeoff by a plane.
      if(isBusy() == false)
      {
      
         answer = 'I';
               
      }//end if statement
      else
      {
      
         answer = operation;
      
      }//end else statement
      
      return answer; 
       
   }//end kindOfOperation method

}//end Runway class
