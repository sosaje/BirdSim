package gla.joose.birdsim.boards;

/**
 * Created by jonathansaje on 07/03/2017.
 */
public class FlockStockDisplay implements StockDisplayBehaviour {
    @Override
    public void updateStockDisplay() {
        updateStock();
        noOfBirdsLabel.setText("#birds: "+noofbirds);
    }
}
