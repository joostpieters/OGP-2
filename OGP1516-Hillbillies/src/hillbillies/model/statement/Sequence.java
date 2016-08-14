package hillbillies.model.statement;

import java.util.List;

public class Sequence extends Statement {
	
	private final List<Statement> statements;

	public Sequence(List<Statement> statements) {
		this.statements = statements;
	}
	
	
	@Override
	public void execute() {
		for (Statement statement: this.statements) {
			statement.execute();
		}
	}

}
