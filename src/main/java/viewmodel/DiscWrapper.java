package viewmodel;

import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import model.DiscObj;

public class DiscWrapper {

    private DiscObj discObj = null;


    // JAVAFX PROPERTIES
    private final ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();
    private final ReadOnlyStringWrapper serial = new ReadOnlyStringWrapper();
    private final ReadOnlyLongWrapper size = new ReadOnlyLongWrapper();
    private final ReadOnlyLongWrapper reads = new ReadOnlyLongWrapper();
    private final ReadOnlyLongWrapper writes = new ReadOnlyLongWrapper();
    private final ReadOnlyLongWrapper freeSpace = new ReadOnlyLongWrapper();


    // CONSTRUCTOR
    public DiscWrapper(DiscObj discObj) {
        this.discObj = discObj;
        update();
    }


    // BIND PROPERTIES
    public void bind() {
        name.bind(discObj.nameProperty());
        serial.bind(discObj.serialProperty());
        size.bind(discObj.sizeProperty());
        reads.bind(discObj.readsProperty());
        writes.bind(discObj.writesProperty());
        freeSpace.bind(discObj.freeSpaceProperty());
    }


    // UPDATE JAVAFX PROPERTIES
    public void update() {
        discObj.update();
    }


    // JAVAFX PROPERTY GETTERS
    public ReadOnlyStringProperty nameProperty() {
        return name.getReadOnlyProperty();
    }

    public ReadOnlyStringProperty serialProperty() {
        return serial.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty sizeProperty() {
        return size.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty readsProperty() {
        return reads.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty writesProperty() {
        return writes.getReadOnlyProperty();
    }

    public ReadOnlyLongProperty freeSpaceProperty() {
        return freeSpace.getReadOnlyProperty();
    }


}
