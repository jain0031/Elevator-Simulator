package elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import elevatorsystem.ElevatorPanel;

public class ElevatorImp extends Observable implements Elevator {
	private static int power_start_stop=2;
	private static int power_continuous=1;
	private static long sleep_start_stop=50;
	private static long sleep_continuous=25;
	private final double  max_capacity_persons;
	private int capacity;
	private int powerUsed = 0;
	private int currentFloor = 0;
	private ArrayList<Integer> dataSet;
	private ElevatorPanel panel;
	private MovingState state = MovingState.Idle;
	int powerConsumed;

	public ElevatorImp(double max_capacity_persons, ElevatorPanel system) {
		this.max_capacity_persons = max_capacity_persons;
		this.panel = system;}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public MovingState getState() {
		return state;
	}

	@Override
	public double getPowerConsumed() {
		return powerUsed;
	}

	@Override
	public void moveTo(int floor) {
		while (currentFloor != floor) {
			if (state == MovingState.SlowUp) {
				powerUsed += power_start_stop;
				currentFloor++;
			} else if (state == MovingState.SlowDown) {
				powerUsed += power_start_stop;
				currentFloor--;
			} else if (state == MovingState.Up) {
				powerUsed += power_continuous;
				currentFloor++;
			} else if (state == MovingState.Down) {
				powerUsed += power_continuous;
				currentFloor--;
			}
			processMovingState(floor);
			dataSet = new ArrayList<>();
			dataSet.add(currentFloor);
			dataSet.add(floor);
			dataSet.add(powerUsed);
			setChanged();
			notifyObservers(dataSet);
			System.out.println("Floor: " + currentFloor + "\n"
					+ "Power: " + powerUsed);
		}
	}



	@Override
	public int getFloor() {
		return currentFloor;
	}

	public void processMovingState(int floor) {
		if (currentFloor > floor) {
			if (state == MovingState.Idle || currentFloor - floor == 1) {
				state = MovingState.SlowDown;
			} else {
				state = MovingState.Down;
			}
		} else if (currentFloor < floor) {
			if (state == MovingState.Idle || floor - currentFloor == 1) {
				state = MovingState.SlowUp;
			} else {
				state = MovingState.Up;
			}
		} 
	}
	@Override
	public void addPersons(int persons) {
		capacity=persons;
	}

	@Override
	public void requestStop(int floor) {
		panel.requestStop(floor, this);	
	}
}
