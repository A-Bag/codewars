import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CheckAndMate {
    public static Set<PieceConfig> isCheck(final PieceConfig[] arrPieces, int player) {
        return Stream.of(arrPieces)
                .filter(piece -> piece.getOwner() != player)
                .filter(piece -> kingCanBeBeaten(piece, arrPieces))
                .collect(Collectors.toSet());
    }

    public static boolean isMate(final PieceConfig[] arrPieces, int player) {

        if (isCheck(arrPieces, player).isEmpty()) {
            return false;
        }
        if (kingHasPossibilityToMove(arrPieces, player)) {
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

    private static boolean kingHasPossibilityToMove(PieceConfig[] arrPieces, int player) {
        PieceConfig king = Stream.of(arrPieces)
                .filter(piece -> piece.getOwner() == player)
                .filter(piece -> piece.getPiece().equals("king"))
                .findFirst().get();
        List<PieceConfig> potentialMoves = findPotentialKingMoves(king);
        List<PieceConfig> possibleMoves = potentialMoves.stream()
                .filter(position -> !isPositionOccupied(position.getX(), position.getY(), arrPieces))
                .collect(Collectors.toList());
        List<PieceConfig> possibleMovesWithoutThreat = possibleMoves.stream()
                .filter(kingPosition ->
                    Stream.of(arrPieces)
                            .filter(piece -> piece.getOwner() != player)
                            .filter(piece -> pieceCanBeBeaten(piece, kingPosition, arrPieces))
                            .collect(Collectors.toList())
                            .isEmpty())
                .collect(Collectors.toList());
        return !possibleMovesWithoutThreat.isEmpty();
    }

    private static boolean threateningPieceCanBeBeaten() {
        return false;
    }

    private static boolean otherPieceCanBlockThreateningPiece() {
        return false;
    }

    private static boolean kingCanBeBeaten(PieceConfig beatingPiece, PieceConfig[] arrPieces) {
        PieceConfig king = Stream.of(arrPieces)
                .filter(piece -> piece.getOwner() != beatingPiece.getOwner())
                .filter(piece -> piece.getPiece().equals("king"))
                .findFirst().get();

        return pieceCanBeBeaten(beatingPiece, king, arrPieces);
    }

    private static boolean pieceCanBeBeaten(PieceConfig beatingPiece, PieceConfig pieceToBeat, PieceConfig[] arrPieces) {
        switch(beatingPiece.getPiece()) {
            case "pawn": return checkIfPieceCanBeBeatenByPawn(beatingPiece, pieceToBeat);
            case "rook": return checkIfPieceCanBeBeatenByRook(beatingPiece, pieceToBeat, arrPieces);
            case "knight": return checkIfPieceCanBeBeatenByKnight(beatingPiece, pieceToBeat);
            case "bishop": return checkIfPieceCanBeBeatenByBishop(beatingPiece, pieceToBeat, arrPieces);
            case "queen": return checkIfPieceCanBeBeatenByQueen(beatingPiece, pieceToBeat, arrPieces);
            case "king": return checkIfPieceCanBeBeatenByKing(beatingPiece, pieceToBeat);
            default: return false;
        }
    }

    private static boolean checkIfPieceCanBeBeatenByPawn(PieceConfig pawn, PieceConfig pieceToBeat) {
        List<PieceConfig> potentialBeatMoves = findPotentialPawnBeatMoves(pawn);
        List<PieceConfig> beatMoves = potentialBeatMoves.stream()
                .filter(beatMove -> pieceToBeat.getX() == beatMove.getX() && pieceToBeat.getY() == beatMove.getY())
                .collect(Collectors.toList());
        return !beatMoves.isEmpty();
    }

    private static boolean checkIfPieceCanBeBeatenByRook(PieceConfig rook, PieceConfig pieceToBeat, PieceConfig[] arrPieces) {
        List<PieceConfig> potentialMoves = findPotentialRookMoves(rook);
        boolean isKingInPotentialMoves = isKingInPotentialMoves(pieceToBeat, potentialMoves);
        boolean isWayToKingFree = false;
        if (isKingInPotentialMoves) {
            if (pieceToBeat.getX() == rook.getX()) {
                isWayToKingFree = IntStream.range(pieceToBeat.getY(), rook.getY())
                        .noneMatch(y -> {
                            if (y != pieceToBeat.getY()) {
                                return isPositionOccupied(rook.getX(), y, arrPieces);
                            }
                            return false;
                        });
            } else if (pieceToBeat.getY() == rook.getY()) {
                isWayToKingFree = IntStream.range(pieceToBeat.getX(), rook.getX())
                        .noneMatch(x -> {
                            if (x != pieceToBeat.getX()) {
                                return isPositionOccupied(x, rook.getY(), arrPieces);
                            }
                            return false;
                        });
            }
        }
        return isWayToKingFree;
    }

    private static boolean checkIfPieceCanBeBeatenByKnight(PieceConfig knight, PieceConfig pieceToBeat) {
        List<PieceConfig> potentialMoves = findPotentialKnightMoves(knight);
        return isKingInPotentialMoves(pieceToBeat, potentialMoves);
    }

    private static boolean checkIfPieceCanBeBeatenByBishop(PieceConfig bishop, PieceConfig pieceToBeat, PieceConfig[] arrPieces) {
        List<PieceConfig> potentialMoves = findPotentialBishopMoves(bishop);
        boolean isKingInPotentialMoves = isKingInPotentialMoves(pieceToBeat, potentialMoves);
        boolean isWayToKingFree = false;
        if (isKingInPotentialMoves) {
            List<PieceConfig> positionsBetweenBishopAndKing = findPositionsBetweenBishopAndKing(bishop, pieceToBeat);
            isWayToKingFree = positionsBetweenBishopAndKing.stream()
                    .noneMatch(position -> isPositionOccupied(position.getX(), position.getY(), arrPieces));
        }
        return isWayToKingFree;
    }

    private static boolean checkIfPieceCanBeBeatenByQueen(PieceConfig queen, PieceConfig pieceToBeat, PieceConfig[] arrPieces) {
        return checkIfPieceCanBeBeatenByBishop(queen, pieceToBeat, arrPieces) || checkIfPieceCanBeBeatenByRook(queen, pieceToBeat, arrPieces);
    }

    private static boolean checkIfPieceCanBeBeatenByKing(PieceConfig king, PieceConfig pieceToBeat) {
        List<PieceConfig> potentialMoves = findPotentialKingMoves(king);
        return isKingInPotentialMoves(pieceToBeat, potentialMoves);
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
