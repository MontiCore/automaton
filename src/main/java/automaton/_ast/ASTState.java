/*
 * ******************************************************************************
 * MontiCore Language Workbench, www.monticore.de
 * Copyright (c) 2017, MontiCore, All rights reserved.
 *
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 * ******************************************************************************
 */

package automaton._ast;

import java.util.ArrayList;
import java.util.List;

public class ASTState extends ASTStateTOP {
  
  protected List<ASTState> reachableStates = new ArrayList<ASTState>();
  
  protected ASTState() {
    super();
  }
  
  protected ASTState(
      String name,
      List<ASTState> states,
      List<ASTTransition> transitions,
      boolean r_final,
      boolean initial) {
    super(name, states, transitions, r_final, initial);
  }
  
  public boolean isReachable(ASTState s) {
    return reachableStates.contains(s);
  }
  
  public List<ASTState> getReachableStates() {
    return this.reachableStates;
  }
  
  public void setReachableStates(List<ASTState> reachableStates) {
    this.reachableStates = reachableStates;
  }
  
  public void addReachableState(ASTState s) {
    this.reachableStates.add(s);
  }
  
}
