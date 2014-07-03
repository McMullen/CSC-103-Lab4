//----------------------------------------------------------------------------------
//Author:      Jason McMullen and Adam Reese
//Due Date:    11/20/13
//
//Program:     RunwaySimulation
//Description: The will act as the driver and run the simulation of the runway. This
//             class will contain the main method. The simulator works by comparing
//             the probability of a plane making a request to land or takeoff
//             a random number generator. If a number generator is less than the 
//             probability of making either request, a plane is created and makes a
//             demand to the tower. At this point the plane is placed immediately
//             into its respected queue where it will wait until the runway is free
//             before proceding. A tally is kept track for all planes that make a 
//             request to the tower and another tally for each specific type of
//             request. This data is used in computing the average amount of times
//             between each request type. The averages are calculated by using
//             methods from the Averager class.
//----------------------------------------------------------------------------------

import java.io.*;

public class RunwaySimulation
{
   
   //the amount of time needed for a plane to takeoff (in minutes)
   private final int TAKEOFF_TIME = 2;
   
   //the amount of time needed for a plane to land (in minutes)
   private final int LANDING_TIME = 3;
   
   //the amount of time an airplane can stay in the air before running out of fuel
   private final int MAX_FLIGHT_TIME = 9;
   
   //the total amount of time the simulation will run (in minutes)
   private final int TOTAL_RUN_TIME = 30;
   
   //the average amount of time between takeoff
   private final int TAKEOFF_AVERAGE = 7;
   
   //the average amount of time between landing 
   private final int LANDING_AVERAGE = 5;
   
   //the probability that an airplane will request a landing
   private double landingProb;
   
   //the probability that an airplane will request a takeoff
   private double takeoffProb;
   
   //number of planes that requested takeoff
   private int totalPlanesTakeoff;
   
   //number of planes that requested landing
   private int totalPlanesLanding;
   
   //number of planes that crashed
   private int planesCrashed;
   
   //the average amount of time a plane was in takeoff queue
   private int averageTakeoffQueue;
   
   //the average amount of time a plane was in landing queue
   private int averageLandingQueue;
   
   //tracks current minute the simulation is on
   private int currentTime;
   
   //holds the id number for each airplane
   private int planeNumber;
   
   //object of LinkedQueue, this will let us put landing planes in queue
   private LinkedQueue<Plane> queueLanding = new LinkedQueue<Plane>();
   
   //object of LinkedQueue, this will let us put takeoff planes in queue
   private LinkedQueue<Plane> queueTakeoff = new LinkedQueue<Plane>();
   
   //object of LinkedQueue, this will hold all the stats for each minute
   //the runway is in use
   private LinkedQueue<String> queueStats = new LinkedQueue<String>();
   
   //object of LinkedStack, this will let us put crashed planes in stack
   private LinkedStack<Plane> stack = new LinkedStack<Plane>();
   
   //object of LinkedStack, will keep track of the time that a plane has crashed
   private LinkedStack<Integer> stackTime = new LinkedStack<Integer>();
      
   
   /**
   * The default constructor of RunwaySimulation class.
   */
   public RunwaySimulation()
   {
   
      //with landing average = 5 min and total time = 30 min, prob is 0.16666667
      landingProb = (double)LANDING_AVERAGE/TOTAL_RUN_TIME;
      
      //with takeoff = 7 min and total time = 30 min, prob is 0.233333333
      takeoffProb = (double)TAKEOFF_AVERAGE/TOTAL_RUN_TIME;
      
      totalPlanesTakeoff = 0;
      totalPlanesLanding = 0;
      planesCrashed = 0;
      
      averageTakeoffQueue = 0;
      averageLandingQueue = 0;
      
      currentTime = 0;
      planeNumber = 0;
      
      try
      {
      
         //objects needed to connect and allow us to writer to an external file
         FileWriter fileOut = new FileWriter("Output.txt", true);
         PrintWriter pw = new PrintWriter(fileOut);
         
         simulate(pw);
         
      }//end try block
      catch(IOException e)
      {
      
         System.out.println("Output.txt could not be found.");
         
         System.exit(0);
      
      }//end catch block
                    
   }//end default constructor
   
   
   /**
   * simulate is responsible for starting the simulation of the runway model. It
   * does this by calling two objects of BooleanSource to see if the random number
   * generator has created an incoming plane for takeoff or landing, or both at the
   * sametime. If two airplanes are requesting use of the tower at the sametime, the
   * plane needing to land will get priority. Each new plane that makes a request
   * then be placed into its proper queue. There are two different queues, one for
   * planes landing and another for planes taking off.
   */
   public void simulate(PrintWriter pw)
   {
         
      //objects of BooleanSource that will hold the probability of planes landing
      //and taking off and will generate a random number that will be interpretted
      //as a plane taking off or landing   
      BooleanSource takeoff = new BooleanSource(takeoffProb);
      BooleanSource landing = new BooleanSource(landingProb);
            
      //object of Runway that will keep track of the current use of the runway
      Runway runway = new Runway(TAKEOFF_TIME, LANDING_TIME);
      
      //object of Averager that will average the wait times for takeoff
      Averager avgTakeoff = new Averager();
      
      //object of Averager that will average the wait times for landing
      Averager avgLanding = new Averager();
            
      //check to see if the probabilities are within expected ranges      
      if(takeoffProb < 0 || takeoffProb > 1 || landingProb < 0 || 
         landingProb > 1 || TOTAL_RUN_TIME < 0)
      {
      
         throw new IllegalArgumentException("The values are not in range.");
      
      }//end if statement
      
      for(currentTime = 0; currentTime <= TOTAL_RUN_TIME; currentTime++)
      {
         
         //string objects that will store the plane number requesting a takeoff
         //or landing to be used for printing purposes
         String planeTakeoff = null;
         String planeLanding = null;
         
         //check to see if there is a plane waiting for takeoff. If there is
         //add to queue
         if(takeoff.query())
         {
         
            totalPlanesTakeoff++;
            Plane planeT = new Plane(currentTime, 'T');
            queueTakeoff.add(planeT);
            planeTakeoff = "Plane #" + planeT.getPlaneNo();
         
         }//end if statement
         
         //check to see if there is a plane waiting for landing. If there is
         //add to queue
         if(landing.query())
         {
         
            totalPlanesLanding++;
            Plane planeL = new Plane(currentTime, 'L');
            queueLanding.add(planeL);
            planeLanding = "Plane #" + planeL.getPlaneNo();
         
         }//end if statement
         
         //check to see if the runway is busy, if free allow a plane to use it
         if(!runway.isBusy() && (!queueTakeoff.isEmpty() || !queueLanding.isEmpty()))
         {
         
            planeNumber = runwayBusy(runway, avgTakeoff, avgLanding, currentTime);
         
         }//end if statement
         
         //create a string to store in a queue all the specifics of this one min
         //of the simulation run
         printTimeUnit(currentTime, runway, planeNumber, planeTakeoff,
                        planeLanding);
         
         //reduce current time on any ongoing operations at the airport
         runway.reduceRemainingTime();
      
      }//end for loop
      
      //print out the summary of the runway
      printSimulationSummary(pw, avgTakeoff, avgLanding);

      //print out the header to the description of the runway use   
      pw.print("\n");
      pw.print("Description of use of the runway: \n");
      pw.print("\n");
      
      //print the stats for each minute
      printAllStats(pw);
      
      //print the list of crashed planes
      printCrashedPlanes(pw);
      
      //closes the output file being printed to
      pw.close();
   
   }//end simulate method
   
   
   /**
   * runwayBusy will check to see if the runway is available for new planes
   * to takeoff or land. If free it will add a new plane to the runway giving
   * priority to those landing over taking off.
   */
   private int runwayBusy(Runway runway, Averager avgTakeoff, Averager avgLanding,
                           int currentTime)
   {
   
      //will use object to store planes as they come off the queue lists
      Plane plane;
      
      int answer = -1;
      
      //if the landing queue is empty allow a plane from takeoff queue to use runway                
      if(queueLanding.isEmpty())
      {
   
         plane = (Plane)queueTakeoff.remove();
         runway.startUsingRunway(plane.getOperation());
         avgTakeoff.addNumber(currentTime - plane.getTime());
         System.out.println(currentTime - plane.getTime());
            
         answer = plane.getPlaneNo();
      
      }//end if statement
      //if there are planes in landing queue, let them have a higher priority of
      //runway use and let them land
      else
      {
                  
         plane = (Plane)queueLanding.remove();
         
         //check to make sure the plane has enough fuel to land
         if((currentTime - plane.getTime()) <= MAX_FLIGHT_TIME)
         {
         
            runway.startUsingRunway(plane.getOperation());
            answer = plane.getPlaneNo();
            avgLanding.addNumber(currentTime - plane.getTime());
                    
         }//end if statement
         //if a plane has crashed add them to the crashed stack
         else
         {
         
            planesCrashed++;
            stack.push(plane);
            stackTime.push(currentTime);
            
            //if a plane has crashed check the queue to see if another plane
            //is available to land
            if(!queueLanding.isEmpty())
            {
            
               plane = (Plane)queueLanding.remove();
               runway.startUsingRunway(plane.getOperation());
               answer = plane.getPlaneNo();
               avgLanding.addNumber(currentTime - plane.getTime());
            
            }//end if statement
         
         }//end else statement   
                    
      }//end else statement
      
      return answer;
   
   }//end runwayBusy method
   
   
   /**
   * This method stores a string inside of a queue. This string will contain all the
   * information of the current use of the runway minute by minute.
   */
   private void printCrashedPlanes(PrintWriter pw)
   {
   
      if(!stack.isEmpty())
      {
      
         pw.print("Crashed Planes: \n");
         for(int i = stack.size(); i > 0; i--)
         {
            
            Plane plane = (Plane)stack.pop();
            pw.print("Plane #" + plane.getPlaneNo() + " crashed at minute " +
                        stackTime.pop() + "\n");
         
         }//end for loop
      
      }//end if statement
      else
      {
      
         pw.print("No planes have crashed during this simulation.");
      
      }//end else statement
   
   }//end printCrashedPlanes method
   
   
   /**
   * This method prints out the current time of the simulation and all the
   * details of the uses of the runway and the activites of the plane currently
   * using the runway.
   */
   public void printTimeUnit(int time, Runway runway, int planeNumber, 
                              String planeTakeoff, String planeLanding)
   {
   
      String string;
      
      string = "\t min " + time + ":\n";
      string = string + "\t Arrived for takeoff: ";
      
      if(planeTakeoff == null)
      {
      
         string = string + "\n";
      
      }//end if statement
      else
      {
      
         string = string + planeTakeoff + "\n";
      
      }//end else statement
      
      string = string + "\t Arrived for Landing: ";
      
      if(planeLanding == null)
      {
      
         string = string + "\n";
      
      }//end if statement
      else
      {
      
         string = string + planeLanding + "\n";      
      }//end else statement
      
      string = string + "\t Runway: ";
      
      if(runway.kindOfOperation() == 'T')
      {
      
         string = string + "Plane #" + planeNumber + " Takeoff \n";
      
      }//end if statement
      else if(runway.kindOfOperation() == 'I')
      {
      
         string = string + "Idle \n";
      
      }//end else if statement
      else if(runway.kindOfOperation() == 'L')
      {
      
         string = string + "Plane #" + planeNumber + " Landing \n";
      
      }//end else statement
      string = string + "\n";
      
      queueStats.add(string);
        
   }//end printTimeUnit method
   
   
   /**
   * printAllStats removes one string object from the queue at a time and prints
   * its value to Output.txt. The value stored in the string should be all of the
   * details of the runway's use per minute ran by the simulator.
   */
   public void printAllStats(PrintWriter pw)
   {
   
      for(int i = 0; i <= TOTAL_RUN_TIME; i++)
      {
      
         pw.print(queueStats.remove());
      
      }//end for loop
   
   }//end printAllStats method
   

   /**
   * printSimulationSummary prints out the summary of the simulator. It will 
   * output the total time the sim has ran, the times needed for a plane to land 
   * and takeoff, the average amount of time between requests for both landing and 
   * takeoff, and the total amount of time an airplane can stay in the air before 
   * crashing due to lack of fuel. This method prints all this information to an 
   * outside .txt file.
   */
   public void printSimulationSummary(PrintWriter pw, Averager avgTakeoff,
                                       Averager avgLanding)
   {
      
      averageTakeoffQueue = (int)avgTakeoff.average();
      averageLandingQueue = (int)avgLanding.average();
      
      //total run time along with takeoff and landing times
      pw.print("The time of simulation is: \t" + TOTAL_RUN_TIME
                   + " minutes \n");
      pw.print("The amount of time that is needed for one plane to take off is: \t"
                   + TAKEOFF_TIME + " minutes \n");
      pw.print("The amount of time that is needed for one plane to land is: \t \t"
                   + LANDING_TIME + " minutes \n");
      
      //the averages for each type of request the tower can receive
      pw.print("The average amount of time between arrival of planes to the takeoff"
                   + " queue is: " + TAKEOFF_AVERAGE + " minutes \n");
      pw.print("The average amount of time between arrival of planes to the landing"
                   + " queue is: " + LANDING_AVERAGE + " minutes \n");
      pw.print("The maximum time a plane can stay in the landing queue before crashing"
                   + " is: \t" + MAX_FLIGHT_TIME + " minutes \n");
      pw.print("\n");
      
      //the information gained from most recent run of simulation
      pw.print("No. of planes that came to the runway for takeoff: \t \t" + 
                   totalPlanesTakeoff + "\n");
      pw.print("No. of planes that came to the runway for landing: \t \t" +
                   totalPlanesLanding + "\n");
      pw.print("No. of planes that crashed: \t \t \t \t \t \t \t \t \t \t" 
                   + planesCrashed + "\n");
      pw.print("The average time a plane spent on the takeoff queue is: \t"
                   + averageTakeoffQueue + " min \n");
      pw.print("The average time a plane spent on the landing queue is: \t"
                   + averageLandingQueue + " min \n");
   
   }//end printSimulationSummary method
   
   
   /**
   * The main method of RunwaySimulation class.
   */
   public static void main(String[] args)
   {
           
      //create an object of the class to call the constructor
      RunwaySimulation sim = new RunwaySimulation();
                  
      //closes down the program
      System.exit(0);
   
   }//end main method

}//end RunwaySimulation class 
