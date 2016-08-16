package hillbillies.model.statement;

import java.util.Iterator;
import java.util.List;

public abstract class ComposedStatement extends Statement /*implements Iterable<Statement> */{

	
	
	private Statement nextStatement = this;
	
	//private List<Statement> nextStatements = null;
	
	
	/*public void setNextStatements(List<Statement> nextStatements) {
		this.nextStatements = nextStatements;
	}
	
	public List<Statement> getNextStatements() {
		return this.nextStatements;
	}*/
	
	
	public void setNextStatement(Statement nextStatement) {
		this.nextStatement = nextStatement;
	}
	
	
	@Override
	public Statement getNextStatement() {
		return this.nextStatement;
	}
	

	/*
	@Override
    public Iterator<Statement> iterator() {
        Iterator<Statement> it = new Iterator<Statement>() {

            /*@Override
            public boolean hasNext() {
                return getNextStatement() != null;
            }

            @Override
            public Statement next() {
                return getNextStatement();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < nextStatements.size() && nextStatements.get(currentIndex) != null;
            }

            @Override
            public Statement next() {
                return nextStatements.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }*/


}
