import java.util.Objects;

public class PieceConfig {
    /*{
        'piece':string,     // pawn, rook, knight, bishop, queen or king
            'owner':int,        // 0 for white or 1 for black
        'x':int,            // 0-7 where 0 is the leftmost column (or "A")
        'y':int,            // 0-7 where 0 is the top row (or "8" in the board below)
        'prevX':
        int,        // 0-7, presents this piece's previous x, only given if this is the piece that was just moved
        'prevY':int
    }        // 0-7, presents this piece's previous y, only given if this is the piece that was just moved }*/

    private String piece;
    private int owner;
    private int x;
    private int y;
    private int prevX = -1;
    private int prevY = -1;

    public PieceConfig(String piece, int owner, int x, int y) {
        this.piece = piece;
        this.owner = owner;
        this.x = x;
        this.y = y;
    }

    public PieceConfig(String piece, int owner, int x, int y, int prevX, int prevY) {
        this.piece = piece;
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.prevX = prevX;
        this.prevY = prevY;
    }

    public String getPiece() {
        return piece;
    }

    public int getOwner() {
        return owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }
//    public int getPrevX()        // will throw a RuntimeException if invoked for an object that do not have informations about its previous move.
//    public int getPrevY()        // will throw a RuntimeException if invoked for an object that do not have informations about its previous move.


    @Override
    public String toString() {
        return "PieceConfig{" +
                "piece='" + piece + '\'' +
                ", owner=" + owner +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieceConfig that = (PieceConfig) o;
        return owner == that.owner &&
                x == that.x &&
                y == that.y &&
                Objects.equals(piece, that.piece);
    }

    @Override
    public int hashCode() {

        return Objects.hash(piece, owner, x, y);
    }
}
