package com.chess.console;

import java.nio.file.Path;
import java.util.Scanner;
import com.chess.engine.logic.*;
import com.chess.engine.enums.*;
import com.chess.engine.pieces.*;
import com.chess.engine.notation.*;

public class ConsoleBoard {

    public static void gameLoop(Pieces pieces) {

        boolean exit = false;
        COLOUR turn = COLOUR.W;
        Scanner sc = new Scanner(System.in);
        StringBuilder str = new StringBuilder();
        int numberOfTurns = 0;

        while (!exit) {

            String[] move = Move.moveQuery(sc);

            if (!Coordinate.inBoard(new Coordinate(move[0])) || !Coordinate.inBoard(new Coordinate(move[1]))) {
                System.out.println("По крайней мере, одной из указанных координат нет на игровом поле. Пожалуйста, попробуйте еще раз!");
            }
            else {
                Coordinate origin = new Coordinate(move[0]);
                Coordinate destination = new Coordinate(move[1]);

                Piece piece = pieces.getPiece(origin);

                if (piece.equals(Piece.emptyPiece)) {
                    System.out.println("Исходная координата не содержит фигуры. Пожалуйста, попробуйте еще раз!");
                } else {
                    if (piece.isValidMove(destination, turn)) {
                        pieces.makeMove(destination, piece);
                        if (turn == COLOUR.W) {
                            numberOfTurns++;
                            str.append(numberOfTurns).append(". ").append(ChessIO.moveString(pieces, destination, piece)).append(" ");
                        }
                        else
                            str.append(ChessIO.moveString(pieces,destination,piece)).append(" ");
                        System.out.println(Boards.displayBoard(pieces));
                        if (pieces.isMate(COLOUR.not(turn))) {
                            System.out.println(turn.toString() + " выиграли.");
                            exit = true;
                        }
                        else if (pieces.isStalemate(turn)) {
                            System.out.println("Это ничья из-за безвыходного положения.");
                            exit = true;
                        }
                        else if (pieces.isDraw()){
                            System.out.println("Это ничья.");
                            exit = true;
                        }
                        else{
                            System.out.println("Введите \"exit\" чтобы завершить игру, или \"save\" ,чтобы сохранить текущее состояние игры.");
                            String input = sc.nextLine();
                            switch (input) {
                                case "exit":
                                    exit = true;
                                    break;
                                case "save":
                                    Path filePath = ChessIO.fileQuery(sc);
                                    if (ChessIO.saveGame(str.toString(), filePath))
                                        System.out.println("Игра успешно сохранена на пути " + filePath.toString());
                                    else
                                        System.out.println("Произошла ошибка при сохранении файла по указанному пути " + filePath.toString());
                                    break;
                                default:
                                    break;
                            }
                            if (!exit) {
                                turn = COLOUR.not(turn);
                                System.out.println(turn.toString() + " ходят.");
                            }
                        }
                    } else {
                        System.out.println("Недопустимый ход.");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Pieces pieces = new Pieces();
        pieces.setGUIGame(false);
        gameLoop(pieces);
    }

}
