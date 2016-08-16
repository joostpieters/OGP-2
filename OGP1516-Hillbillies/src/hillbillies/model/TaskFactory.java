package hillbillies.model;

import java.util.ArrayList;
import java.util.List;
//import java.util.*;
//import java.*;
//import java.beans.Expression;
//import java.beans.Statement;

import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.model.statement.*;
import hillbillies.model.expression.*;


public class TaskFactory implements ITaskFactory<Expression<?>, Statement, Task> {
	
	public TaskFactory() {
		
	}
	
	@Override
	public List<Task> createTasks(String name, int priority, Statement activity, List<int[]> selectedCubes) {
		List<Task> listOfTasks = new ArrayList<Task>();
		//System.out.println("create task in TF");
		if (selectedCubes != null && selectedCubes.size() > 0) {
			for (int[] selectedCube : selectedCubes) {
				Task task = new Task(name, priority, activity, selectedCube);
				listOfTasks.add(task);
				task.getActions().setTask(task);
				System.out.println(task.toString());
			} 
		} else {
			Task task = new Task(name, priority, activity);
			listOfTasks.add(task);
			task.getActions().setTask(task);
			System.out.println(task.toString());
		}
		
		return listOfTasks;
	}
	

	@Override
	public Statement createAssignment(String variableName, Expression<?> value, SourceLocation sourceLocation) {
		return new Assignment(variableName, value);
	}

	@Override
	public Statement createWhile(Expression<?> condition, Statement body, SourceLocation sourceLocation) {
		return new While((Expression<Boolean>) condition, body);
	}

	@Override
	public Statement createIf(Expression<?> condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		return new If((Expression<Boolean>) condition, ifBody, elseBody);
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createPrint(Expression<?> value, SourceLocation sourceLocation) {
		return new Print(value);
	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		return new Sequence(statements);
	}

	@Override
	public Statement createMoveTo(Expression<?> position, SourceLocation sourceLocation) {
		
		return new MoveTo((Expression<Coordinate>) position);
	}
	

	@Override
	public Statement createWork(Expression<?> position, SourceLocation sourceLocation) {
		return new Work((Expression<Coordinate>) position);
	}

	@Override
	public Statement createFollow(Expression<?> unit, SourceLocation sourceLocation) {
		return new Follow((Expression<Unit>) unit);
	}

	@Override
	public Statement createAttack(Expression<?> unit, SourceLocation sourceLocation) {
		return new Attack((Expression<Unit>) unit);
	}

	@Override
	public Expression<?> createReadVariable(String variableName, SourceLocation sourceLocation) {
		return new ReadVariable(variableName);
	}

	@Override
	public Expression<Boolean> createIsSolid(Expression<?> position, SourceLocation sourceLocation) {
		return new IsSolid((Expression<Coordinate>) position);
	}

	@Override
	public Expression<Boolean> createIsPassable(Expression<?> position, SourceLocation sourceLocation) {
		return new IsPassable((Expression<Coordinate>) position);
	}

	@Override
	public Expression<Boolean> createIsFriend(Expression<?> unit, SourceLocation sourceLocation) {
		return new IsFriend((Expression<Unit>) unit);
	}

	@Override
	public Expression<Boolean> createIsEnemy(Expression<?> unit, SourceLocation sourceLocation) {
		return new IsEnemy((Expression<Unit>) unit);
	}

	@Override
	public Expression<Boolean> createIsAlive(Expression<?> unit, SourceLocation sourceLocation) {
		return new IsAlive((Expression<Unit>) unit);
	}

	@Override
	public Expression<Boolean> createCarriesItem(Expression<?> unit, SourceLocation sourceLocation) {
		return new CarriesItem((Expression<Unit>) unit);
	}

	@Override
	public Expression<Boolean> createNot(Expression<?> expression, SourceLocation sourceLocation) {
		return new Not((Expression<Boolean>) expression);
	}

	@Override
	public Expression<Boolean> createAnd(Expression<?> left, Expression<?> right, SourceLocation sourceLocation) {
		return new And((Expression<Boolean>)left, (Expression<Boolean>) right);
	}

	@Override
	public Expression<Boolean> createOr(Expression<?> left, Expression<?> right, SourceLocation sourceLocation) {
		return new Or((Expression<Boolean>) left, (Expression<Boolean>) right);
	}

	@Override
	public Expression<Coordinate> createHerePosition(SourceLocation sourceLocation) {
		return new HerePosition();
	}

	@Override
	public Expression<Coordinate> createLogPosition(SourceLocation sourceLocation) {
		return new LogPosition();
	}

	@Override
	public Expression<Coordinate> createBoulderPosition(SourceLocation sourceLocation) {
		return new BoulderPosition();
	}

	@Override
	public Expression<Coordinate> createWorkshopPosition(SourceLocation sourceLocation) {
		return new WorkshopPosition();
	}

	@Override
	public Expression<Coordinate> createSelectedPosition(SourceLocation sourceLocation) {
		return new SelectedPosition();
	}

	@Override
	public Expression<Coordinate> createNextToPosition(Expression<?> position, SourceLocation sourceLocation) {
		return new NextToPosition((Expression<Coordinate>) position);
	}

	@Override
	public Expression<Coordinate> createPositionOf(Expression<?> unit, SourceLocation sourceLocation) {
		return new PositionOf((Expression<Unit>) unit);
	}

	@Override
	public Expression<Coordinate> createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		return new LiteralPosition(x, y, z);
	}

	@Override
	public Expression<Unit> createThis(SourceLocation sourceLocation) {
		return new This();
	}

	@Override
	public Expression<Unit> createFriend(SourceLocation sourceLocation) {
		return new Friend();
	}

	@Override
	public Expression<Unit> createEnemy(SourceLocation sourceLocation) {
		return new Enemy();
	}

	@Override
	public Expression<Unit> createAny(SourceLocation sourceLocation) {
		return new Any();
	}

	@Override
	public Expression<Boolean> createTrue(SourceLocation sourceLocation) {
		return new True();
	}

	@Override
	public Expression<Boolean> createFalse(SourceLocation sourceLocation) {
		return new False();
	}

}
