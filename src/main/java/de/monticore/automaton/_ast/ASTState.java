package de.monticore.automaton._ast;

public class ASTState extends ASTStateTOP {
  
  protected ASTStateList reachableStates = AutomatonNodeFactory.createASTStateList();
  
  protected ASTState() {
    super();
  }
  
  protected ASTState(
      String name,
      boolean r_final,
      boolean initial) {
    super(name, r_final, initial);
  }
  
  public boolean isReachable(ASTState s) {
    return reachableStates.contains(s);
  }
  
  public ASTStateList getReachableStates() {
    return this.reachableStates;
  }
  
  public void setReachableStates(ASTStateList reachableStates) {
    this.reachableStates = reachableStates;
  }
  
  public void addReachableState(ASTState s) {
    this.reachableStates.add(s);
  }
  
}
