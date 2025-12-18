package com.chess.engine.logic;

import com.chess.engine.enums.BOARD;
import java.util.Objects;

/**
 * Класс для представления координат на шахматной доске
 */
public class Coordinate {

    /** Файл (вертикаль) координаты */
    public char file;
    /** Ранг (горизонталь) координаты */
    public int rank;
    /** Пустая координата */
    public static Coordinate emptyCoordinate = new Coordinate((char) 0, 0);

    /**
     * Конструктор координаты
     * @param file файл (вертикаль) - символ от 'a' до 'j'
     * @param rank ранг (горизонталь) - число от 1 до 10
     */
    public Coordinate(char file, int rank) {
        this.file = Character.toLowerCase(file);
        this.rank = rank;
    }

    /**
     * Конструктор копирования координаты
     * @param original оригинальная координата для копирования
     */
    public Coordinate(Coordinate original) {
        file = original.file;
        rank = original.rank;
    }

    /**
     * Конструктор координаты из строки (например, "a1", "j10")
     * @param coordinate строковое представление координаты
     */
    public Coordinate(String coordinate) {
        if (coordinate == null || coordinate.trim().isEmpty()) {
            System.out.println("Неверный формат координат. Указана пустая координата.");
            file = 0;
            rank = 0;
            return;
        }

        String normalized = coordinate.trim().toLowerCase();

        if (normalized.length() < 2 || normalized.length() > 3) {
            System.out.println("Неверный формат координат: " + coordinate +
                    ". Формат должен быть: буква + число (например, a1 или e10)");
            file = 0;
            rank = 0;
            return;
        }

        char firstChar = normalized.charAt(0);
        if (!Character.isLetter(firstChar)) {
            System.out.println("Неверный формат координат: " + coordinate +
                    ". Первый символ должен быть буквой.");
            file = 0;
            rank = 0;
            return;
        }

        try {
            String numberPart = normalized.substring(1);
            int parsedRank = Integer.parseInt(numberPart);

            if (firstChar < BOARD.FIRST_FILE.getFileVal() ||
                    firstChar > BOARD.LAST_FILE.getFileVal() ||
                    parsedRank < BOARD.FIRST_RANK.getRankVal() ||
                    parsedRank > BOARD.LAST_RANK.getRankVal()) {
                System.out.println("Координата вне доски: " + coordinate +
                        ". Диапазон: файлы от " + BOARD.FIRST_FILE.getFileVal() +
                        " до " + BOARD.LAST_FILE.getFileVal() +
                        ", ранги от " + BOARD.FIRST_RANK.getRankVal() +
                        " до " + BOARD.LAST_RANK.getRankVal());
                file = 0;
                rank = 0;
                return;
            }

            file = firstChar;
            rank = parsedRank;

        } catch (NumberFormatException e) {
            System.out.println("Неверный формат координат: " + coordinate +
                    ". После буквы должны быть цифры.");
            file = 0;
            rank = 0;
        }
    }

    /**
     * Конструктор пустой координаты
     */
    public Coordinate() {
        file = 0;
        rank = 0;
    }

    /**
     * Получает файл (вертикаль) координаты
     * @return символ файла
     */
    public char getFile() {
        return file;
    }

    /**
     * Получает ранг (горизонталь) координаты
     * @return номер ранга
     */
    public int getRank() {
        return rank;
    }

    /**
     * Получает индекс файла для использования в массивах
     * @return индекс файла (0-based)
     */
    public int getFileIndex() {
        if (file == 0) return -1;
        return file - BOARD.FIRST_FILE.getFileVal();
    }

    /**
     * Получает индекс ранга для использования в массивах
     * @return индекс ранга (0-based)
     */
    public int getRankIndex() {
        if (rank == 0) return -1;
        return rank - BOARD.FIRST_RANK.getRankVal();
    }

    /**
     * Проверяет, является ли координата пустой
     * @return true если координата пустая
     */
    public boolean isEmpty() {
        return file == 0 || rank == 0;
    }

    /**
     * Проверяет, находится ли координата в пределах доски
     * @param coord координата для проверки
     * @return true если координата находится на доске
     */
    public static boolean inBoard(Coordinate coord) {
        if (coord == null || coord.isEmpty()) {
            return false;
        }

        char coordFile = coord.getFile();
        int coordRank = coord.getRank();
        return (coordFile >= BOARD.FIRST_FILE.getFileVal()
                && coordFile <= BOARD.LAST_FILE.getFileVal()
                && coordRank >= BOARD.FIRST_RANK.getRankVal()
                && coordRank <= BOARD.LAST_RANK.getRankVal());
    }

    /**
     * Проверяет, находится ли координата в пределах доски
     * @return true если координата находится на доске
     */
    public boolean isInBoard() {
        return inBoard(this);
    }

    @Override
    public String toString() {
        if (file == 0 || rank == 0) {
            return "INVALID";
        }
        return file + "" + rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return file == that.file &&
                rank == that.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }
}