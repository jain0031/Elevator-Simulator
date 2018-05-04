 package elevatorsystem;

import java.util.Observer;

import elevator.Elevator;
import elevator.ElevatorImp;
import elevator.MovingState;

public class ElevatorSystemImp implements ElevatorPanel, ElevatorSystem {

	private int max_floor;
	private int min_floor;
	private Elevator elevator;
	 ElevatorImp elev;
	
	public ElevatorSystemImp(int MIN_FLOOR,int MAX_FLOOR) {
		 
		 this.min_floor=MIN_FLOOR;
		 this.max_floor=MAX_FLOOR;
		 
		 
		 
	 }
	public void requestStop(int floor,Elevator elevator) {
		elevator.moveTo(floor);
		
	}
	public Elevator callUp(int floor) {
		elevator.moveTo(floor);
		return elevator;
		
	}
	
	public Elevator callDown(int floor) {
		elevator.moveTo(floor);
		return elevator;
	}
	
	
	private void floorCheck(int floor) {
		
		
	}
	
	private Elevator call(int floor,Elevator direction) {
		
		return direction;
		
	}

	private boolean checkForElevator() {
		
		
     return	true;	
     
     
	}
	public double getPowerConsumed() {
		return elevator.getPowerConsumed();
		
	}
	public int getCurrentFloor() {
		return this.max_floor;
		
	}
	public int getMaxFloor() {
		return this.max_floor;
		
	}
	
	public int getMinFloor() {
		return this.min_floor;
		
	}
	
//	public int getFloorCount() {
//		return max_floor;
//		
//	}
	
	public void addElevator(Elevator elevator) {
		
		
		this.elevator=elevator;
	}
	public void addObserver(Observer observer) {
		elev.addObserver(observer);
			}
	@Override
	public int getFloorCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}

