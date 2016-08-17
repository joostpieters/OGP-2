package hillbillies.model.statement;

import java.util.Iterator;
import java.util.List;

public class Sequence extends ComposedStatement implements Iterable<Statement> {
	
	private final List<Statement> statements;
	private final Iterator<Statement> iterator;

	public Sequence(List<Statement> statements) {
		this.statements = statements;
		this.iterator = iterator();
		
	
		for (int i=0; i+1<this.statements.size(); i++) {
			this.statements.get(i).setNextToExecStatement(this.statements.get(i+1));

			this.statements.get(i).setNextStatement(this.statements.get(i+1));
		}
		
	}
	
	
	@Override
	public void execute() {
		for (Statement statement: this.statements) {
			statement.setTask(getTask());
			
		}
		
	}
	
	
	@Override
	public void setNextToExecStatement(Statement nextToExecStatement) {
		super.setNextToExecStatement(this.statements.get(0));
		
		
		this.statements.get(this.statements.size()-1).setNextToExecStatement(nextToExecStatement);
	}
	
	
	@Override
	public Statement getNextStatement() {
		if (this.statements != null && this.statements.size() > 0)
			return this.statements.iterator().next();
		return getNextStatement();
	}
	
	
	@Override
	public void setNextStatement(Statement nextStatement) {
		super.setNextStatement(nextStatement);
		this.statements.get(this.statements.size() - 1).setNextStatement(nextStatement);
	}
	
	
	@Override
    public Iterator<Statement> iterator() {
        Iterator<Statement> it = new Iterator<Statement>() {

            
            
            private int currentIndex = 0;
            
            public int getCurrentIndex() {
            	return this.currentIndex;
            }

            @Override
            public boolean hasNext() {
                return currentIndex < statements.size() && statements.get(currentIndex) != null;
            }

            @Override
            public Statement next() {
            	System.out.println(this.getCurrentIndex());
                return statements.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

}
