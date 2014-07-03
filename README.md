CSC-103-Lab4
============

Runway Simulator

CSC103  Fall    2013
Sara Wexler

Assignment #4


This assignment is to be done by a group of two persons 

•	Do programming project 9 from chapter 7 on page 407 
•	Create a RunwaySimulation class, where the Car Wash Simulation described on pages 353 - 365 provides a model upon which to base your program. 
•	In addition to the output specified in the book, your program should also: 
1.	Show for each minute simulated how the runway is being used if the runway is (i.e. take-off, landing, crash landing or idle.)- look at output example
2.	After the simulated time period is over show a list of times at which planes crashed. This list is to be in reverse order of the crash occurrences (Hint: keep track of crash times using a stack.) . 
     
    You are to make use of the following components in constructing your program: 
•	the BooleanSource and Averager classes used in the Car Wash Simulation- with no changes! 
     http://www.cs.colorado.edu/~main/edu/colorado/simulations/

•	a Runway class modeled on the Washer class used in the Car Wash. The same Runway class should be used for takeoff and landing.  Complete the class given below. 
               Landing has a higher priority then take off. There is no need to set a priority 
               queue, just pop from the landing queue before you pop from the take off queue.

•	a Queue Class from the book : LinkedQueue.java 
a Stack Class from the book: LinkedStack.java 
a Node class from the book: Node.java
All classes are on the M drive and in: 
      http://www.cs.colorado.edu/~main/edu/colorado/collections

•	create a printTimeUnit method in the RunwaySimulation class to print each TimeUnit of the simulation 

•	create another print method, to print all other statistics about the simulation

MAKE SURE TO HAVE :
•	CLEAR AND GOOD DOCUMENTATION
•	CREATE A CLEAR OUTPUT
 
The stack and queue store objects of the following class Plane.

Class Runway{
	private  int timeForLanding;
private  int timeForTakeoff;
private int runwayTimeLeft; 
private char operation;             // operation can be: I – Idle, L-Landing, T-takeoff

public Runnway ( int time_takeofff, int time_landing){….}
//set the time for landing, time for takeoff, and the operation to idle. 

public boolean isBusy() {…}

public reduceRemainingTime(){…}

public void startUsingRunway(char typeOfUse){…..}
// if typeOfUse is 'T' - then the operation is take off  and set the runway time left 
// to the time it takes for take off.
// if typeOfUse is 'L' - then the operation is landing and set the runway time left 
// to the time it takes for landing
            // if typrofUse is ‘I’ – then the runway is idle, set the runway time left to zero

public char kindOf Operation() {…} 
// returns the type of operation the runway is used for. 
// returns  'L' if the runway is used for is landing. 
// returns  'T' if  the runway is used for taking off. 
// returns ‘I, if the runway is idle 


class Plane {
static private int  planeCount = 0;       // the plane number arrived to the queue 
                        // should be in incrementing order
private int time;	                        // the time the plane arrived to the queue
private char operation;                        // the kind of operation the plane is doing 'L" 
					// is  for landing 'T' is for taking off 
           private int planeNo;	                         // plane number

public Plane( int aTime, char landingOrTakeOff)
// operation  is the type of operation the plane is doing. If landingOrTakeOff is 'L' // it means the plane is landing, if landingOrTakeOff is 'T' it means the plane is 
// Takingoff. 
{
	time = aTime;
	 operation =  landingOrTakeOff;
            planeNo = ++planeCount;
}

public int getTime()  
{return time;}
static public int getPlaneNo () 
{ return planeNo;}
public char getOperation ()
{ return operation;}
           
          private static int getPlaneCount()
           {
                            return planeCount;
            }


      }

Example of output:

The time of simulation is:    30 minutes 
The amount of time that is needed for one plane to take off is:    2 minutes
The amount of time that is needed for one plane to land is :         3 minutes
The average amount of time between arrival of planes to the takeoff queue is    7 minutes
The average amount of time between arrival of panes to the landing queue is    5 minutes 
The maximum time a plane can stay in the landing queue before crashing is     9 minutes 

No of  planes that came to the runway  for takeoff		: 	 6
No. of  plans that came to the runway for landing   		:  	 8
No of  planes that crashed 		: 	 3
The average time a plane spent on the takeoff queue is	:	15 min 
The average time a plane spent on the landing queue is	:	8 min 

Description of use of the runway:
min 1 :  
             Arrived for Takeoff  :  Plane  #1 
             Arrived for Landing :      
              Runway : Plane #1  is taking off      
   
min 2 : 
             Arrived for Takeoff  :  
             Arrived for Landing :    
              Runway : Plane #1  is taking off  ( takeoff finished)




min 3 : 
             Arrived for Takeoff  :  
             Arrived for Landing :   
              Runway :  Idle



min 4 : 
             Arrived for Takeoff  :  
             Arrived for Landing :      Plane #2     
              Runway :  Plane #2  is landing 

 min 5 : 
             Arrived for Takeoff  :     Plane #3
             Arrived for Landing :      Plane #4
              Runway :  Plane #2  is landing
  
min 6 : 
             Arrived for Takeoff  :     
             Arrived for Landing :      Plane #5
              Runway :  Plane #2  is landing ( landing finished)

:
  
crashing planes:
plane 10 crashed at minute 25
plane 5 crashed at minute 20
plane 3 crashed at minute 15 


Turned In : 
•	Use closeable envelope / folder marked clearly outside with names, class section and assignment # 
•	All source code written for the assignment and the javadoc document; 
•	Highlight all new code in the DoubleLinkedSeq class
•	Write your names at the top of the source code and the date 
•	Hard copy of test cases run 
•	Disk with all files relevant to the Assignment, including executable. 

Grading criteria
5	- if the project  is working good, get correct output, and there are sufficient comments
4	- if the  project is working good, but there are some problems with the   
       code, or some of the output is incorrect
       or the comments are not sufficient
2 - if the code compile but does not run
1 - if the does not compile
Due date:    ( Nov 20 week 12)
