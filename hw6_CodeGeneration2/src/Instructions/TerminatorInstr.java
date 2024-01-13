package Instructions;

import STable.TableEntryType;
import Type.Type;
import Values.BasicBlock;
import Values.Value;

import java.util.ArrayList;

public class TerminatorInstr extends Instruction{
    private TerminatorType terminatorType;
    private boolean isVoid; //  是否是void类型的返回值

    public TerminatorInstr(String name, Type type, BasicBlock parent,
                           TerminatorType terminatorType, Value... ops) {
        super(name, type, parent, ops);
        this.terminatorType = terminatorType;
        if (terminatorType == TerminatorType.CallWithVal) {
            this.name = "%" + name;
        }
    }


    public TerminatorType getTerminatorType() {
        return terminatorType;
    }

    private String retToString() {
        if (terminatorType == TerminatorType.RetNoVal) {
            return "ret void";
        } else {
            return "ret " + getUsedValue(0).getType().toString() + " " + getUsedValue(0).getName();
        }
    }

    private String callToString() {
        StringBuilder ans = new StringBuilder();
        if (terminatorType == TerminatorType.CallNoVal) {
            ans.append("call void ");
        } else {
            ans.append(getName()).append(" = call ").append(getType().toString()).append(" ");
        }

        ans.append(getUsedValue(0).getName()); // function name
        ans.append("(");
        for (int i = 1; i < getUsedValuesNum(); i++) {
            ans.append(getUsedValue(i).getType().toString())
                    .append(" ").append(getUsedValue(i).getName());
            if (i != getUsedValuesNum() - 1) {
                ans.append(", ");
            }
        }
        ans.append(")");
        return ans.toString();
    }

    public String toString() {
        if (terminatorType == TerminatorType.RetNoVal || terminatorType == TerminatorType.RetWithVal) {
            return retToString();
        } else if (terminatorType == TerminatorType.CallNoVal || terminatorType == TerminatorType.CallWithVal) {
            return callToString();
        } else if (terminatorType == TerminatorType.BrNoCondition || terminatorType == TerminatorType.BrWithCondition) {
            return branchToString();
        } else {
            return terminatorType.toString();
        }
    }

    public boolean isBrOrRet() {
        return this.terminatorType == TerminatorType.BrNoCondition ||
                this.terminatorType == TerminatorType.BrWithCondition ||
                this.terminatorType == TerminatorType.RetWithVal ||
                this.terminatorType == TerminatorType.RetNoVal;
    }

    public boolean isRet() {
        return this.terminatorType == TerminatorType.RetWithVal ||
                this.terminatorType == TerminatorType.RetNoVal;
    }

    public String branchToString() {
        if (terminatorType == TerminatorType.BrNoCondition) {
            return "br " + getUsedValue(0).getType() + " " + getUsedValue(0).getName();
        } else if (terminatorType == TerminatorType.BrWithCondition) {
            return "br " + getUsedValue(0).getType() + " " + getUsedValue(0).getName() +
                    ", " + getUsedValue(1).getType() + " " + getUsedValue(1).getName() +
                    ", " + getUsedValue(2).getType() + " " + getUsedValue(2).getName();
        } else {
            return "";
        }
    }
}
