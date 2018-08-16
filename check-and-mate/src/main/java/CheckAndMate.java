import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CheckAndMate {
    public static Set<PieceConfig> isCheck(final PieceConfig[] arrPieces, int player) {
        return Stream.of(arrPieces)
                .filter(piece -> piece.getOwner() != player)
                .filter(piece -> canBeatKing(piece, arrPieces))
                .collect(Collectors.toSet());
    }

    public static boolean isMate(final PieceConfig[] arrPieces, int player) {
        if (isCheck(arrPieces, player).isEmpty()) {
            return false;
        }
        if (kingHasPossibilityToMove()) {
            return false;
        }
        if (threateningPieceCanBeBeaten()) {
            return false;
        }
        if (otherPieceCanBlockThreateningPiece()) {
            return false;
        }
        return true;
    }

    private static boolean kingHasPossibilityToMove() {
        return false;
    }

    private static boolean threateningPieceCanBeBeaten() {
        return false;
    }

    private static boolean otherPieceCanBlockThreateningPiece() {
        return false;
    }

    private static boolean canBeatKing(PieceConfig beatingPiece, PieceConfig[] arrPieces) {
        PieceConfig king = Stream.of(arrPieces)
                .filter(piece -> piece.getOwner() != beatingPiece.getOwner())
                .filter(piece -> piece.getPiece().equals("king"))
                .findFirst().get();

        return checkIfPieceCanBeatKing(beatingPiece, king, arrPieces);
    }

    private static boolean checkIfPieceCanBeatKing(PieceConfig beatingPiece, PieceConfig king, PieceConfig[] arrPieces) {
        switch(beatingPiece.getPiece()) {
            case "pawn": return checkIfPawnCanBeatKing(beatingPiece, king, arrPieces);
            case "rook": return checkIfRookCanBeatKing(beatingPiece, king, arrPieces);
            case "knight": return checkIfKnightCanBeatKing(beatingPiece, king, arrPieces);
            case "bishop": return checkIfBishopCanBeatKing(beatingPiece, king, arrPieces);
            case "queen": return checkIfQueenCanBeatKing(beatingPiece, king, arrPieces);
            case "king": return checkIfKingCanBeatKing(beatingPiece, king, arrPieces);
            default: return false;
        }
    }

    private static boolean checkIfPawnCanBeatKing(PieceConfig beatingPiece, PieceConfig king, PieceConfig[] arrPieces) {
        List<PieceConfig> potentialBeatMoves = findPotentialPawnBeatMoves(beatingPiece);
        List<PieceConfig> beatMoves = potentialBeatMoves.stream()
                .filter(beatMove -> king.getX() == beatMove.getX() && king.getY() == beatMove.getY())
                .collect(Collectors.toList());
        return !beatMoves.isEmpty();
    }

    private static boolean checkIfRookCanBeatKing(PieceConfig beatingPiece, PieceConfig king, PieceConfig[] arrPieces) {
        List<PieceConfig> potentialMoves = findPotentialRookMoves(beatingPiece);
        boolean isKingInPotentialMoves = isKingInPotentialMoves(king, potentialMoves);
        boolean isWayToKingFree = false;
        if (isKingInPotentialMoves) {
            if (king.getX() == beatingPiece.getX()) {
                isWayToKingFree = IntStream.range(king.getY(), beatingPiece.getY())
                        .noneMatch(y -> {
                            if (y != king.getY()) {
                                return isPositionOccupied(beatingPiece.getX(), y, arrPieces);
                            }
                            return false;
                        });
            } else if (king.getY() == beatingPiece.getY()) {
                isWayToKingFree = IntStream.range(king.getX(), beatingPiece.getX())
                        .noneMatch(x -> {
                            if (x != king.getX()) {
                                return isPositionOccupied(x, beatingPiece.getY(), arrPieces);
                            }
                            return false;
                        });
            }
        }
        return isWayToKingFree;
    }

    private static boolean checkIfKnightCanBeatKing(PieceConfig beatingPiece, PieceConfig king, PieceConfig[] arrPieces) {
        List<PieceConfig> potentialMoves = findPotentialKnightMoves(beatingPiece);
        return isKingInPotentialMoves(king, potentialMoves);
    }

    private static boolean checkIfBishopCanBeatKing(PieceConfig beatingPiece, PieceConfig king, PieceConfig[] arrPieces) {
        List<PieceConfig> potentialMoves = findPotentialBishopMoves(beatingPiece);
        boolean isKingInPotentialMoves = isKingInPotentialMoves(king, potentialMoves);
        boolean isWayToKingFree = false;
        if (isKingInPotentialMoves) {
            List<PieceConfig> positionsBetweenBishopAndKing = findPositionsBetweenBishopAndKing(beatingPiece, king);
            isWayToKingFree = positionsBetweenBishopAndKing.stream()
                    .noneMatch(position -> isPositionOccupied(position.getX(), position.getY(), arrPieces));
        }
        return isWayToKingFree;
    }

    private static boolean checkIfQueenCanBeatKing(PieceConfig beatingPiece, PieceConfig king, PieceConfig[] arrPieces) {
        return checkIfBishopCanBeatKing(beatingPiece, king, arrPieces) || checkIfRookCanBeatKing(beatingPiece, king, arrPieces);
    }

    private static boolean checkIfKingCanBeatKing(PieceConfig beatingPiece, PieceConfig king, PieceConfig[] arrPieces) {
        List<PieceConfig> potentialMoves = findPotentialKingMoves(beatingPiece);
        return isKingInPotentialMoves(king, potentialMoves);
    }

    private static List<PieceConfig> findPositionsBetweenBishopAndKing(PieceConfig beatingPiece, PieceConfig king) {
        List<PieceConfig> positions = new ArrayList<>();
        int xLength = king.getX() - beatingPiece.getX();
        int yLength = king.getY() - beatingPiece.getY();
        if (xLength > 0 && yLength > 0) {
            for (int i=1; i<xLength; i++) {
                positions.add(new PieceConfig(beatingPiece.getPiece(), beatingPiece.getOwner(), beatingPiece.getX()+i, beatingPiece.getY()+i));
            }
        } else if (xLength > 0 && yLength < 0) {
            for (int i=1; i<xLength; i++) {
                positions.add(new PieceConfig(beatingPiece.getPiece(), beatingPiece.getOwner(), beatingPiece.getX()+i, beatingPiece.getY()-i));
            }
        } else if (xLength < 0 && yLength > 0) {
            xLength = -xLength;
            for (int i=1; i<xLength; i++) {
                positions.add(new PieceConfig(beatingPiece.getPiece(), beatingPiece.getOwner(), beatingPiece.getX()-i, beatingPiece.getY()+i));
            }
        } else {
            xLength = -xLength;
            for (int i=1; i<xLength; i++) {
                positions.add(new PieceConfig(beatingPiece.getPiece(), beatingPiece.getOwner(), beatingPiece.getX()-i, beatingPiece.getY()-i));
            }
        }
        return positions;
    }

    private static List<PieceConfig> findPotentialBishopMoves(PieceConfig bishop) {
        List<PieceConfig> potentialMoves = new ArrayList<>();
        for (int i=0; i<8; i++) {
            if (bishop.getX()+i < 8 && bishop.getY()+i < 8) {
                potentialMoves.add(new PieceConfig(bishop.getPiece(), bishop.getOwner(), bishop.getX()+i, bishop.getY()+i));
            }
            if (bishop.getX()+i < 8 && bishop.getY()-i > -1) {
                potentialMoves.add(new PieceConfig(bishop.getPiece(), bishop.getOwner(), bishop.getX()+i, bishop.getY()-i));
            }
            if (bishop.getX()-i > -1 && bishop.getY()+i < 8) {
                potentialMoves.add(new PieceConfig(bishop.getPiece(), bishop.getOwner(), bishop.getX()-i, bishop.getY()+i));
            }
            if (bishop.getX()-i > -1 && bishop.getY()-i > -1) {
                potentialMoves.add(new PieceConfig(bishop.getPiece(), bishop.getOwner(), bishop.getX()-i, bishop.getY()-i));
            }
        }
        return potentialMoves;
    }

    private static List<PieceConfig> findPotentialPawnBeatMoves(PieceConfig beatingPiece) {
        List<PieceConfig> potentialBeatMoves = new ArrayList<>();
        if (beatingPiece.getOwner() == 0) {
            potentialBeatMoves.add(new PieceConfig(beatingPiece.getPiece(), beatingPiece.getOwner(), beatingPiece.getX()-1, beatingPiece.getY()-1));
            potentialBeatMoves.add(new PieceConfig(beatingPiece.getPiece(), beatingPiece.getOwner(), beatingPiece.getX()+1, beatingPiece.getY()-1));
        } else if (beatingPiece.getOwner() == 1) {
            potentialBeatMoves.add(new PieceConfig(beatingPiece.getPiece(), beatingPiece.getOwner(), beatingPiece.getX()-1, beatingPiece.getY()+1));
            potentialBeatMoves.add(new PieceConfig(beatingPiece.getPiece(), beatingPiece.getOwner(), beatingPiece.getX()+1, beatingPiece.getY()+1));
        }
        return potentialBeatMoves;
    }

    private static List<PieceConfig> findPotentialRookMoves(PieceConfig rook) {
        List<PieceConfig> potentialMoves = new ArrayList<>();
        for (int i=0; i<8; i++) {
            if (i != rook.getY()) {
                potentialMoves.add(new PieceConfig(rook.getPiece(), rook.getOwner(), rook.getX(), i));
            }
            if (i != rook.getX()) {
                potentialMoves.add(new PieceConfig(rook.getPiece(), rook.getOwner(), i, rook.getY()));
            }
        }
        return potentialMoves;
    }

    private static List<PieceConfig> findPotentialKingMoves(PieceConfig king) {
        List<PieceConfig> potentialMoves = new ArrayList<>();
        potentialMoves.add(new PieceConfig(king.getPiece(), king.getOwner(), king.getX(), king.getY()+1));
        potentialMoves.add(new PieceConfig(king.getPiece(), king.getOwner(), king.getX()-1, king.getY()+1));
        potentialMoves.add(new PieceConfig(king.getPiece(), king.getOwner(), king.getX()+1, king.getY()+1));
        potentialMoves.add(new PieceConfig(king.getPiece(), king.getOwner(), king.getX(), king.getY()-1));
        potentialMoves.add(new PieceConfig(king.getPiece(), king.getOwner(), king.getX()-1, king.getY()-1));
        potentialMoves.add(new PieceConfig(king.getPiece(), king.getOwner(), king.getX()+1, king.getY()-1));
        potentialMoves.add(new PieceConfig(king.getPiece(), king.getOwner(), king.getX()-1, king.getY()));
        potentialMoves.add(new PieceConfig(king.getPiece(), king.getOwner(), king.getX()+1, king.getY()));
        return potentialMoves;
    }

    private static List<PieceConfig> findPotentialKnightMoves(PieceConfig knight) {
        List<PieceConfig> potentialMoves = new ArrayList<>();
        potentialMoves.add(new PieceConfig(knight.getPiece(), knight.getOwner(), knight.getX()+2, knight.getY()+1));
        potentialMoves.add(new PieceConfig(knight.getPiece(), knight.getOwner(), knight.getX()-2, knight.getY()+1));
        potentialMoves.add(new PieceConfig(knight.getPiece(), knight.getOwner(), knight.getX()+1, knight.getY()+2));
        potentialMoves.add(new PieceConfig(knight.getPiece(), knight.getOwner(), knight.getX()-1, knight.getY()+2));
        potentialMoves.add(new PieceConfig(knight.getPiece(), knight.getOwner(), knight.getX()+2, knight.getY()-1));
        potentialMoves.add(new PieceConfig(knight.getPiece(), knight.getOwner(), knight.getX()-2, knight.getY()-1));
        potentialMoves.add(new PieceConfig(knight.getPiece(), knight.getOwner(), knight.getX()+1, knight.getY()-2));
        potentialMoves.add(new PieceConfig(knight.getPiece(), knight.getOwner(), knight.getX()-1, knight.getY()-2));
        return potentialMoves;
    }

    private static boolean isKingInPotentialMoves(PieceConfig king, List<PieceConfig> potentialMoves) {
        return potentialMoves.stream()
                .anyMatch(rook -> rook.getX() == king.getX() && rook.getY() == king.getY());
    }

    private static boolean isPositionOccupied(int x, int y, PieceConfig[] arrPieces) {
        return Stream.of(arrPieces)
                .anyMatch(pieceConfig -> pieceConfig.getX() == x && pieceConfig.getY() == y);
    }
}
