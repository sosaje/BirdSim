package gla.joose.birdsim.boards;

import gla.joose.birdsim.pieces.Bird;
import gla.joose.birdsim.pieces.Grain;
import gla.joose.birdsim.pieces.Piece;
import gla.joose.birdsim.util.Distance;
import gla.joose.birdsim.util.DistanceMgr;

/**
 * Created by jonathansaje on 07/03/2017.
 */
public class MovingFly implements FlyBehaviour {

    public void fly(Board board) {

        Bird bird = new Bird();
        int moves = board.rand.nextInt(21);

        int randRow = board.rand.nextInt((board.getRows() - 3) + 1) + 0;
        int randCol = board.rand.nextInt((board.getColumns() - 3) + 1) + 0;
        board.place(bird,randRow, randCol);
        bird.setDraggable(false);
        bird.setSpeed(20);
        board.updateStockDisplay();

        while(!board.scareBirds && moves != 0){

            DistanceMgr dmgr = new DistanceMgr();
            int current_row = bird.getRow();
            int current_col = bird.getColumn();

            synchronized(board.allPieces){
                for (int i=0;i< board.getAllPieces().size(); i++) {
                    Piece piece = board.getAllPieces().get(i);
                    if(piece instanceof Grain){

                        int dist_from_food_row = current_row - piece.getRow();
                        int dist_from_food_col = piece.getColumn() - current_col;
                        Distance d = null;
                        if(dist_from_food_row <= dist_from_food_col){
                            d = new Distance(bird, (Grain)piece, dist_from_food_row, dist_from_food_col);
                        }
                        else{
                            d = new Distance(bird, (Grain)piece, dist_from_food_row, dist_from_food_col);
                        }
                        dmgr.addDistance(d);


                    }
                }
            }
            ////

            Distance distances[] = dmgr.getDistances();
            boolean movedone = false;

            for(int i =0; i< distances.length;i++){
                Distance d = distances[i];

                if(d.getRowDist() <= d.getColDist()){

                    if(d.getRowDist() >0){
                        boolean can_move_down= bird.canMoveTo(current_row-1, current_col);
                        if(can_move_down){
                            bird.moveTo(current_row-1, current_col);
                            movedone = true;
                            break;
                        }
                    }
                    else if(d.getRowDist() < 0){
                        boolean can_move_down= bird.canMoveTo(current_row+1, current_col);
                        if(can_move_down){
                            bird.moveTo(current_row+1, current_col);
                            movedone = true;
                            break;
                        }
                    }
                    else if(d.getRowDist() ==0){
                        if(d.getColDist() >0){
                            boolean can_move_right = bird.canMoveTo(current_row, current_col+1);
                            if(can_move_right){
                                bird.moveTo(current_row, current_col+1);
                                movedone = true;
                                break;
                            }
                        }
                        else if(d.getColDist()< 0){
                            boolean can_move_left = bird.canMoveTo(current_row, current_col-1);
                            if(can_move_left){
                                bird.moveTo(current_row, current_col-1);
                                movedone = true;
                                break;
                            }
                        }
                        else if(d.getColDist() ==0){
                            //bingo -food found (eat and move away)
                            Grain grain = (Grain)d.getTargetpiece();
                            grain.deplete();

                            int randRowf = board.rand.nextInt((board.getRows() - 3) + 1) + 0;
                            int randColf= board.rand.nextInt((board.getColumns() - 3) + 1) + 0;
                            grain.moveTo(randRowf, randColf);
                            grain.setSpeed(10);
                            if(board.starveBirds){
                                grain.remove();
                                board.updateStockDisplay();
                            }
                            else if(grain.getRemaining() <=0){
                                grain.remove();
                                board.updateStockDisplay();
                            }
                            int randRow1 = board.rand.nextInt((board.getRows() - 3) + 1) + 0;
                            int randCol2 = board.rand.nextInt((board.getColumns() - 3) + 1) + 0;
                            bird.moveTo(randRow1, randCol2);
                            bird.setSpeed(20);
                            movedone = true;
                            moves = moves + 5;
                            break;


                        }

                    }
                }
                ///////
                else if(d.getRowDist() > d.getColDist()){

                    if(d.getColDist() >0){
                        boolean can_move_right = bird.canMoveTo(current_row, current_col+1);
                        if(can_move_right){
                            bird.moveTo(current_row, current_col+1);
                            movedone = true;
                            break;
                        }
                    }
                    else if(d.getColDist()<0){
                        boolean can_move_left = bird.canMoveTo(current_row, current_col-1);
                        if(can_move_left){
                            bird.moveTo(current_row, current_col-1);
                            movedone = true;
                            break;
                        }
                    }
                    else if(d.getColDist() == 0){
                        if(d.getRowDist() >0){
                            boolean can_move_up= bird.canMoveTo(current_row-1, current_col);
                            if(can_move_up){
                                bird.moveTo(current_row-1, current_col);
                                movedone = true;
                                break;
                            }

                        }
                        else if(d.getRowDist() < 0){
                            boolean can_move_down = bird.canMoveTo(current_row+1, current_col);///kkkk
                            if(can_move_down){
                                bird.moveTo(current_row+1, current_col);
                                movedone = true;
                                break;
                            }
                        }
                        else if(d.getRowDist() ==0){
                            //bingo -food found (eat and move away)
                            Grain grain = (Grain)d.getTargetpiece();
                            grain.deplete();

                            int randRowf = board.rand.nextInt((board.getRows() - 3) + 1) + 0;
                            int randColf= board.rand.nextInt((board.getColumns() - 3) + 1) + 0;
                            grain.moveTo(randRowf, randColf);
                            grain.setSpeed(10);

                            if(board.starveBirds){
                                grain.remove();
                                board.updateStockDisplay();
                            }
                            else if(grain.getRemaining() <=0){
                                grain.remove();
                                board.updateStockDisplay();
                            }
                            int randRow1 = board.rand.nextInt((board.getRows() - 3) + 1) + 0;
                            int randCol2 = board.rand.nextInt((board.getColumns() - 3) + 1) + 0;
                            bird.moveTo(randRow1, randCol2);
                            bird.setSpeed(20);
                            movedone = true;
                            moves = moves + 5;
                            break;

                        }
                    }
                }
            }
            if(!movedone){
                int randRow1 = board.rand.nextInt((board.getRows() - 3) + 1) + 0;
                int randCol2 = board.rand.nextInt((board.getColumns() - 3) + 1) + 0;
                bird.moveTo(randRow1, randCol2);
                moves--;
            }

        }
        bird.remove();
        board.updateStockDisplay();

    }

}
