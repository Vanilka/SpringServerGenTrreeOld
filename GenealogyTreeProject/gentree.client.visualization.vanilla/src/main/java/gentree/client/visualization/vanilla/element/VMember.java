package gentree.client.visualization.vanilla.element;

import gentree.client.desktop.domain.Member;
import gentree.client.visualization.elements.FamilyMember;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class VMember extends FamilyMember {

    DoubleProperty maxRight, maxLeft;

    {
        maxRight = new SimpleDoubleProperty();
        maxLeft = new SimpleDoubleProperty();
    }

    public VMember(Member member) {
        super(member);
    }

    public VMember() {
        this(null);
    }


    /**
     * Getters and setters
     *
     * @return
     */
    public double getMaxRight() {
        return maxRight.get();
    }

    public void setMaxRight(double maxRight) {
        this.maxRight.set(maxRight);
    }

    public ReadOnlyDoubleProperty maxRightProperty() {
        return maxRight;
    }

    public double getMaxLeft() {
        return maxLeft.get();
    }

    public void setMaxLeft(double maxLeft) {
        this.maxLeft.set(maxLeft);
    }

    public ReadOnlyDoubleProperty maxLeftProperty() {
        return maxLeft;
    }


}


